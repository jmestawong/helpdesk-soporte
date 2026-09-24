package com.helpdesk.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.helpdesk.entity.Usuario;
import com.helpdesk.service.UsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String verPerfil(Principal principal, Model model) {

        model.addAttribute(
                "usuario",
                usuarioService.buscarPorCorreo(principal.getName())
        );

        model.addAttribute("activo", "perfil");

        return "perfil/index";
    }

    @PostMapping("/actualizar")
    public String actualizarDatos(
            @RequestParam String nombres,
            @RequestParam String apellidos,
            Principal principal) {

        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);

        usuarioService.guardar(usuario);

        return "redirect:/perfil?actualizado";
    }

    @PostMapping("/password")
    public String cambiarPassword(
            @RequestParam String passwordNueva,
            Principal principal) {

        Usuario usuario = usuarioService.buscarPorCorreo(principal.getName());

        if (passwordNueva != null && !passwordNueva.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(passwordNueva));
            usuarioService.guardar(usuario);
        }

        return "redirect:/perfil?passwordActualizada";
    }
}
