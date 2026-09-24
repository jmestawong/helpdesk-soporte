package com.helpdesk.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.helpdesk.entity.ArticuloConocimiento;
import com.helpdesk.entity.Usuario;
import com.helpdesk.service.ArticuloConocimientoService;
import com.helpdesk.service.CategoriaService;
import com.helpdesk.service.UsuarioService;

@Controller
@RequestMapping("/base-conocimiento")
public class BaseConocimientoController {

    @Autowired
    private ArticuloConocimientoService articuloService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("articulos", articuloService.listar());
        return "base_conocimiento/lista";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Integer id, Model model) {
        model.addAttribute("articulo", articuloService.buscarPorId(id));
        return "base_conocimiento/ver";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("articulo", new ArticuloConocimiento());
        model.addAttribute("categorias", categoriaService.listar());
        return "base_conocimiento/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ArticuloConocimiento articulo, Principal principal) {

        Usuario autor = usuarioService.buscarPorCorreo(principal.getName());
        articulo.setAutor(autor);

        articuloService.guardar(articulo);

        return "redirect:/base-conocimiento";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        model.addAttribute("articulo", articuloService.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listar());

        return "base_conocimiento/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        articuloService.eliminar(id);
        return "redirect:/base-conocimiento";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String texto, Model model) {
        model.addAttribute("articulos", articuloService.buscarPorTexto(texto));
        return "base_conocimiento/lista";
    }
}
