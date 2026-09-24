package com.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.helpdesk.entity.Rol;
import com.helpdesk.entity.Usuario;
import com.helpdesk.service.RolService;
import com.helpdesk.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("usuarios",
                usuarioService.listar());

        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("usuario",
                new Usuario());

        model.addAttribute("roles",
                rolService.listar());

        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {

        if (usuario.getPassword() != null &&
                !usuario.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(
                            usuario.getPassword()
                    )
            );
        }

        usuarioService.guardar(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute(
                "usuario",
                usuarioService.buscarPorId(id));

        model.addAttribute(
                "roles",
                rolService.listar());

        return "usuarios/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Integer id) {

        usuarioService.eliminar(id);

        return "redirect:/usuarios";
    }
}