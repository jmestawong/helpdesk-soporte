package com.helpdesk.config;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.helpdesk.service.UsuarioService;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute
    public void agregarUsuarioSesion(Principal principal, Model model) {

        if (principal != null) {
            model.addAttribute(
                    "usuarioSesion",
                    usuarioService.buscarPorCorreo(principal.getName())
            );
        }
    }
}
