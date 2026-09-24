package com.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.helpdesk.entity.Ticket;
import com.helpdesk.service.CategoriaService;
import com.helpdesk.service.EstadoTicketService;
import com.helpdesk.service.PrioridadService;
import com.helpdesk.service.TicketService;
import com.helpdesk.service.UsuarioService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PrioridadService prioridadService;

    @Autowired
    private EstadoTicketService estadoTicketService;

    @GetMapping
    public String listarTickets(Model model) {
        model.addAttribute("tickets", ticketService.listar());
        return "tickets/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("prioridades", prioridadService.listar());
        model.addAttribute("estados", estadoTicketService.listar());

        return "tickets/form";
    }

    @PostMapping("/guardar")
    public String guardarTicket(@ModelAttribute Ticket ticket) {
        ticketService.guardar(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/editar/{id}")
    public String editarTicket(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.buscarPorId(id);

        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("prioridades", prioridadService.listar());
        model.addAttribute("estados", estadoTicketService.listar());

        return "tickets/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTicket(@PathVariable Integer id) {
        ticketService.eliminar(id);
        return "redirect:/tickets";
    }

    @GetMapping("/buscar")
    public String buscarTicket(@RequestParam String titulo, Model model) {
        model.addAttribute("tickets", ticketService.buscarPorTitulo(titulo));
        return "tickets/lista";
    }
}