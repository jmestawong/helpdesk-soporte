package com.helpdesk.controllers;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.helpdesk.entity.Ticket;
import com.helpdesk.entity.Usuario;
import com.helpdesk.service.TicketService;
import com.helpdesk.service.UsuarioService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String dashboard(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            Model model) {

        LocalDate inicio;
        LocalDate fin;

        if (fechaInicio == null || fechaInicio.isBlank()) {
            inicio = LocalDate.now().minusMonths(1);
        } else {
            inicio = LocalDate.parse(fechaInicio);
        }

        if (fechaFin == null || fechaFin.isBlank()) {
            fin = LocalDate.now();
        } else {
            fin = LocalDate.parse(fechaFin);
        }

        LocalDateTime fechaInicioDateTime =
                inicio.atStartOfDay();

        LocalDateTime fechaFinDateTime =
                fin.atTime(LocalTime.MAX);

        List<Ticket> tickets =
                ticketService.listar()
                        .stream()
                        .filter(t ->
                                t.getFechaRegistro() != null
                                        && !t.getFechaRegistro().isBefore(fechaInicioDateTime)
                                        && !t.getFechaRegistro().isAfter(fechaFinDateTime)
                        )
                        .toList();

        long pendientes =
                tickets.stream()
                        .filter(t ->
                                t.getEstado().getNombreEstado()
                                        .equalsIgnoreCase("Pendiente"))
                        .count();

        long proceso =
                tickets.stream()
                        .filter(t ->
                                t.getEstado().getNombreEstado()
                                        .equalsIgnoreCase("En Proceso"))
                        .count();

        long atendidos =
                tickets.stream()
                        .filter(t ->
                                t.getEstado().getNombreEstado()
                                        .equalsIgnoreCase("Atendido"))
                        .count();

        long cerrados =
                tickets.stream()
                        .filter(t ->
                                t.getEstado().getNombreEstado()
                                        .equalsIgnoreCase("Cerrado"))
                        .count();

        model.addAttribute("tickets", tickets);

        model.addAttribute("totalTickets", tickets.size());

        model.addAttribute("pendientes", pendientes);
        model.addAttribute("proceso", proceso);
        model.addAttribute("atendidos", atendidos);
        model.addAttribute("cerrados", cerrados);

        model.addAttribute("fechaInicio", inicio);
        model.addAttribute("fechaFin", fin);

        return "dashboard/index";
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportarPdf(
            Principal principal) {

        try {

            Usuario usuario =
                    usuarioService.buscarPorCorreo(
                            principal.getName()
                    );

            List<Ticket> tickets =
                    ticketService.listar();

            long pendientes =
                    tickets.stream()
                            .filter(t ->
                                    t.getEstado().getNombreEstado()
                                            .equalsIgnoreCase("Pendiente"))
                            .count();

            long proceso =
                    tickets.stream()
                            .filter(t ->
                                    t.getEstado().getNombreEstado()
                                            .equalsIgnoreCase("En Proceso"))
                            .count();

            long atendidos =
                    tickets.stream()
                            .filter(t ->
                                    t.getEstado().getNombreEstado()
                                            .equalsIgnoreCase("Atendido"))
                            .count();

            long cerrados =
                    tickets.stream()
                            .filter(t ->
                                    t.getEstado().getNombreEstado()
                                            .equalsIgnoreCase("Cerrado"))
                            .count();

            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(document, baos);

            document.open();

            Font titulo =
                    new Font(Font.HELVETICA, 18, Font.BOLD);

            Paragraph encabezado =
                    new Paragraph(
                            "REPORTE HELPDESK",
                            titulo
                    );

            encabezado.setAlignment(Element.ALIGN_CENTER);

            document.add(encabezado);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "Generado por: "
                            + usuario.getNombres()
                            + " "
                            + usuario.getApellidos()
            ));

            document.add(new Paragraph(
                    "Correo: "
                            + usuario.getCorreo()
            ));

            document.add(new Paragraph(
                    "Fecha: "
                            + LocalDate.now()
            ));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("RESUMEN"));
            document.add(new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "Total Tickets: "
                                    + tickets.size()
                    )
            );

            document.add(
                    new Paragraph(
                            "Pendientes: "
                                    + pendientes
                    )
            );

            document.add(
                    new Paragraph(
                            "En Proceso: "
                                    + proceso
                    )
            );

            document.add(
                    new Paragraph(
                            "Atendidos: "
                                    + atendidos
                    )
            );

            document.add(
                    new Paragraph(
                            "Cerrados: "
                                    + cerrados
                    )
            );

            document.add(new Paragraph(" "));
            document.add(new Paragraph("LISTADO DE TICKETS"));
            document.add(new Paragraph(" "));

            PdfPTable tabla =
                    new PdfPTable(7);

            tabla.setWidthPercentage(100);

            tabla.addCell("Código");
            tabla.addCell("Título");
            tabla.addCell("Usuario");
            tabla.addCell("Categoría");
            tabla.addCell("Prioridad");
            tabla.addCell("Estado");
            tabla.addCell("Fecha");

            for (Ticket ticket : tickets) {

                tabla.addCell(ticket.getCodigoTicket());

                tabla.addCell(ticket.getTitulo());

                tabla.addCell(
                        ticket.getUsuario()
                                .getNombres()
                );

                tabla.addCell(
                        ticket.getCategoria()
                                .getNombreCategoria()
                );

                tabla.addCell(
                        ticket.getPrioridad()
                                .getNombrePrioridad()
                );

                tabla.addCell(
                        ticket.getEstado()
                                .getNombreEstado()
                );

                tabla.addCell(
                        ticket.getFechaRegistro() != null
                                ? ticket.getFechaRegistro().toString()
                                : ""
                );
            }

            document.add(tabla);

            document.close();

            String nombreArchivo =
                    LocalDate.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "dd-MM-yyyy"
                                    )
                            )
                            + "-"
                            + usuario.getNombres()
                            + " "
                            + usuario.getApellidos()
                            + ".pdf";

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    nombreArchivo + "\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generando PDF",
                    e
            );

        }
    }
}