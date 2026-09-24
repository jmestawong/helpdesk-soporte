package com.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.helpdesk.entity.Rol;
import com.helpdesk.entity.Usuario;
import com.helpdesk.repository.RolRepository;
import com.helpdesk.service.UsuarioService;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(Usuario usuario, Model model) {

        if (usuarioService.buscarPorCorreo(usuario.getCorreo()) != null) {

            model.addAttribute("error",
                    "El correo ya se encuentra registrado");

            return "registro";
        }

        Rol rolUsuario = rolRepository
                .findByNombreRol("Usuario")
                .orElseThrow();

        usuario.setRol(rolUsuario);

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        usuarioService.guardar(usuario);

        return "redirect:/login?registro";
    }
}