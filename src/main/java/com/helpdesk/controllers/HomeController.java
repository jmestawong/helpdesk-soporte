package com.helpdesk.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio(Authentication authentication) {

        boolean puedeVerDashboard = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMINISTRADOR") || a.equals("ROLE_TECNICO"));

        return puedeVerDashboard ? "redirect:/dashboard" : "redirect:/tickets/mis-tickets";
    }
}
