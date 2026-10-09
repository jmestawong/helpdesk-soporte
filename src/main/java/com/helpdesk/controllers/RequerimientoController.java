package com.helpdesk.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.helpdesk.entity.Requerimiento;
import com.helpdesk.entity.Usuario;
import com.helpdesk.service.CategoriaService;
import com.helpdesk.service.EstadoTicketService;
import com.helpdesk.service.PrioridadService;
import com.helpdesk.service.RequerimientoService;
import com.helpdesk.service.UsuarioService;

@Controller
@RequestMapping("/requerimientos")
public class RequerimientoController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PrioridadService prioridadService;

    @Autowired
    private EstadoTicketService estadoTicketService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("requerimientos", requerimientoService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("activo", "requerimientos");
        return "requerimientos/lista";
    }

    @GetMapping("/mis-requerimientos")
    public String misRequerimientos(Principal principal, Model model) {

        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        model.addAttribute("requerimientos", requerimientoService.listarPorUsuario(usuario));
        model.addAttribute("activo", "mis-requerimientos");

        return "requerimientos/mis-requerimientos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("requerimiento", new Requerimiento());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("prioridades", prioridadService.listar());
        model.addAttribute("estados", estadoTicketService.listar());
        model.addAttribute("activo", "nuevo-requerimiento");

        return "requerimientos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Requerimiento requerimiento) {
        requerimientoService.guardar(requerimiento);
        return "redirect:/requerimientos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Requerimiento requerimiento = requerimientoService.buscarPorId(id);

        model.addAttribute("requerimiento", requerimiento);
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("prioridades", prioridadService.listar());
        model.addAttribute("estados", estadoTicketService.listar());
        model.addAttribute("activo", "requerimientos");

        return "requerimientos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        requerimientoService.eliminar(id);
        return "redirect:/requerimientos";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String titulo, Model model) {

        if (titulo != null && titulo.matches("\\d+")) {

            Requerimiento requerimiento = requerimientoService.buscarPorId(Integer.valueOf(titulo));
            model.addAttribute("requerimientos", requerimiento != null ? List.of(requerimiento) : List.of());

        } else {
            model.addAttribute("requerimientos", requerimientoService.buscarPorTitulo(titulo));
        }

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("activo", "requerimientos");
        return "requerimientos/lista";
    }

    @GetMapping("/filtrar")
    public String filtrar(
            @RequestParam(required = false) List<Integer> categoriasSeleccionadas,
            Model model) {

        if (categoriasSeleccionadas != null && !categoriasSeleccionadas.isEmpty()) {
            model.addAttribute("requerimientos", requerimientoService.listarPorCategorias(categoriasSeleccionadas));
        } else {
            model.addAttribute("requerimientos", requerimientoService.listar());
        }

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("categoriasSeleccionadas", categoriasSeleccionadas);
        model.addAttribute("activo", "requerimientos");
        return "requerimientos/lista";
    }
}
