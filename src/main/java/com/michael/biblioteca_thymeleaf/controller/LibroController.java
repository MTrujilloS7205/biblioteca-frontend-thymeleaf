package com.michael.biblioteca_thymeleaf.controller;

import com.michael.biblioteca_thymeleaf.dto.AutorDTO;
import com.michael.biblioteca_thymeleaf.dto.LibroDTO;
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
public class LibroController {

  private final RestTemplate restTemplate;

  @Value("${backend.url}")
  private String backendUrl;

  @GetMapping("/libros")
  public String libros(Model model) {

    try {
      LibroDTO[] response = restTemplate.getForObject(
              backendUrl + "/libros",
              LibroDTO[].class
      );
      System.out.println("URL: " + backendUrl + "/libros");

      List<LibroDTO> listaLibrosVar = (response != null)
              ? Arrays.asList(response)
              : List.of();

      model.addAttribute("listaLibros", listaLibrosVar);

      // 🔥 TRAER AUTORES DEL BACKEND
      AutorDTO[] autores = restTemplate.getForObject(
              backendUrl + "/autores",
              AutorDTO[].class
      );

      List<AutorDTO> listaAutores = (autores != null)
              ? Arrays.asList(autores)
              : List.of();

      model.addAttribute("listaAutores", listaAutores);
    } catch (RestClientException e) {
      e.printStackTrace(); // 🔥 CLAVE
      model.addAttribute("listaLibros", List.of());
    }
    return "libros/list";
  }

  @GetMapping("/libros/eliminar/{id}")
  public String eliminar(@PathVariable Long id) {
    try {
      restTemplate.delete(backendUrl + "/libros/" + id);

    } catch (HttpClientErrorException.NotFound e) {
      System.out.println("El libro ya fue eliminado");

    } catch (RestClientException e) {
      System.out.println("Error al intentar eliminar el libro: " + e.getMessage());
    }

    return "redirect:/libros";
  }

  @GetMapping("/libros/nuevo")
  public String nuevo(Model model) {

    model.addAttribute("libro", new LibroDTO());

    // 🔥 TRAER AUTORES DEL BACKEND
    AutorDTO[] autores = restTemplate.getForObject(
            backendUrl + "/autores",
            AutorDTO[].class
    );

    List<AutorDTO> listaAutores = (autores != null)
            ? Arrays.asList(autores)
            : List.of();

    model.addAttribute("listaAutores", listaAutores);
    return "libros/form";
  }

  @PostMapping("/libros")
  public String guardarLibro(@ModelAttribute LibroDTO libro,
          RedirectAttributes redirectAttributes) {

    try {

      restTemplate.postForObject(
              backendUrl + "/libros",
              libro,
              Void.class
      );

      redirectAttributes.addFlashAttribute("success",
              "Libro guardado correctamente");

      return "redirect:/libros";

    } catch (HttpClientErrorException e) {

      String mensajeError = e.getResponseBodyAsString();
      redirectAttributes.addFlashAttribute("error", mensajeError);

    } catch (RestClientException e) {

      redirectAttributes.addFlashAttribute("error",
              "Error al guardar libro");
    }

    return "libros/form";
  }

  @GetMapping("/libros/editar/{id}")
  public String editarLibro(@PathVariable Long id, Model model) {

    try {
      LibroDTO libro = restTemplate.getForObject(
              backendUrl + "/libros/" + id,
              LibroDTO.class
      );

      model.addAttribute("libro", libro);

      // 🔥 MISMO BLOQUE
      AutorDTO[] autores = restTemplate.getForObject(
              backendUrl + "/autores",
              AutorDTO[].class
      );

      List<AutorDTO> listaAutores = (autores != null)
              ? Arrays.asList(autores)
              : List.of();

      model.addAttribute("listaAutores", listaAutores);

    } catch (RestClientException e) {
      model.addAttribute("error", "Error al cargar el libro");
      model.addAttribute("libro", new LibroDTO());
    }

    return "libros/form";
  }

  @PostMapping("/libros/{id}")
  public String actualizarLibro(@PathVariable Long id,
          @ModelAttribute LibroDTO libro,
          RedirectAttributes redirectAttributes) {

    try {

      restTemplate.put(
              backendUrl + "/libros/" + id,
              libro
      );

      redirectAttributes.addFlashAttribute("success",
              "Libro actualizado correctamente");

      return "redirect:/libros";

    } catch (RestClientException e) {

      redirectAttributes.addFlashAttribute("error",
              "Error al actualizar el libro");

      return "libros/form";
    }
  }

  @GetMapping("/libros/autor/{id}")
  public String librosPorAutor(@PathVariable Long id, Model model) {

    List<LibroDTO> lista = List.of();
    List<AutorDTO> listaAutores = List.of();

    try {

      LibroDTO[] response = restTemplate.getForObject(
              backendUrl + "/libros/autor/" + id,
              LibroDTO[].class
      );

      lista = (response != null) ? Arrays.asList(response) : List.of();

      AutorDTO[] autores = restTemplate.getForObject(
              backendUrl + "/autores",
              AutorDTO[].class
      );

      listaAutores = Arrays.asList(autores);

    } catch (RestClientException e) {
      // opcional: log
    }

    // 🔥 SIEMPRE se envía a la vista
    model.addAttribute("listaLibros", lista);
    model.addAttribute("listaAutores", listaAutores);
    model.addAttribute("autorSeleccionado", id);

    return "libros/list";
  }

}
