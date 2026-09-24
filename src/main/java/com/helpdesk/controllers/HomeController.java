package com.helpdesk.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.helpdesk.entity.Usuario;
import com.helpdesk.service.UsuarioService;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio(
            Principal principal,
            Model model) {

        if (principal != null) {

            Usuario usuario =
                    usuarioService.buscarPorCorreo(
                            principal.getName()
                    );

            model.addAttribute("usuario", usuario);
            model.addAttribute(
                    "rol",
                    usuario.getRol().getNombreRol()
            );
        }

        return "index";
    }
}