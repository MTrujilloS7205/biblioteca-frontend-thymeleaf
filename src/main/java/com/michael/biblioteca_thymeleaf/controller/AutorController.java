package com.michael.biblioteca_thymeleaf.controller;

import com.michael.biblioteca_thymeleaf.dto.AutorDTO;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AutorController {

  private final RestTemplate restTemplate;

  @Value("${backend.url}")
  private String backendUrl;

  @GetMapping("/autores")
  public String autores(Model model) {

    AutorDTO[] response = restTemplate.getForObject(
            backendUrl + "/autores",
            AutorDTO[].class
    );
    
    System.out.println("URL: " + backendUrl + "/autores");

    List<AutorDTO> listaAutores = Arrays.asList(response);

    model.addAttribute("listaAutores", listaAutores);
    return "autores/list";
  }

  @GetMapping("/autores/eliminar/{id}")
  public String eliminar(@PathVariable Long id) {
    restTemplate.delete(backendUrl + "/autores/" + id);
    return "redirect:/autores";
  }

  // 🔹 MOSTRAR FORM
  @GetMapping("/autores/nuevo")
  public String nuevo(Model model) {
    model.addAttribute("autor", new AutorDTO());
    return "autores/form";
  }

  // 🔹 GUARDAR AUTOR
  @PostMapping("/autores")
  public String guardarAutor(@ModelAttribute AutorDTO autor,
          RedirectAttributes redirectAttributes) {

    try {

      restTemplate.postForObject(
              backendUrl + "/autores",
              autor,
              Void.class
      );
      redirectAttributes.addFlashAttribute("success",
              "Autor Guardado correctamente");

      return "redirect:/autores";

    } catch (HttpClientErrorException e) {
      // Capturamos el mensaje que viene del backend
      String mensajeError = e.getResponseBodyAsString();
      redirectAttributes.addFlashAttribute("error", mensajeError);

    } catch (RestClientException e) {
      redirectAttributes.addFlashAttribute("error",
              "Error al guardar autor");
    }

    return "autores/form";
  }

  // 🔹 CARGAR AUTOR EN EL FORMULARIO PARA EDITAR
  @GetMapping("/autores/editar/{id}")
  public String editarAutor(@PathVariable Long id, Model model) {
    try {
      AutorDTO autor = restTemplate.getForObject(
              backendUrl + "/autores/" + id,
              AutorDTO.class
      );
      model.addAttribute("autor", autor);
    } catch (RestClientException e) {
      model.addAttribute("error", "Error al cargar el autor");
      model.addAttribute("autor", new AutorDTO());
    }
    return "autores/form"; // Mismo formulario que para crear
  }

  // 🔹 ENVIAR EL AUTOR AL BACKEND PARA ACTUALIZAR
  @PostMapping("/autores/{id}")
  public String actualizarAutor(@PathVariable Long id,
          @ModelAttribute AutorDTO autor,
          RedirectAttributes redirectAttributes) {
    try {
      restTemplate.put(
              backendUrl + "/autores/" + id,
              autor
      );
      redirectAttributes.addFlashAttribute("success", "Autor actualizado correctamente");
      return "redirect:/autores";
    } catch (RestClientException e) {
      redirectAttributes.addFlashAttribute("error", "Error al actualizar el autor");
      return "autores/form";
    }
  }
}
