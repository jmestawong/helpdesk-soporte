package com.helpdesk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.helpdesk.entity.PreguntaFrecuente;
import com.helpdesk.service.PreguntaFrecuenteService;

@Controller
@RequestMapping("/preguntas-frecuentes")
public class PreguntaFrecuenteController {

    @Autowired
    private PreguntaFrecuenteService preguntaFrecuenteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("preguntas", preguntaFrecuenteService.listar());
        model.addAttribute("activo", "preguntas-frecuentes");
        return "preguntas_frecuentes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pregunta", new PreguntaFrecuente());
        model.addAttribute("activo", "preguntas-frecuentes");
        return "preguntas_frecuentes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PreguntaFrecuente pregunta) {
        preguntaFrecuenteService.guardar(pregunta);
        return "redirect:/preguntas-frecuentes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("pregunta", preguntaFrecuenteService.buscarPorId(id));
        model.addAttribute("activo", "preguntas-frecuentes");
        return "preguntas_frecuentes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        preguntaFrecuenteService.eliminar(id);
        return "redirect:/preguntas-frecuentes";
    }
}
