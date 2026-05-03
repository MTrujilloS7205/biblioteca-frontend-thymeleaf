package com.michael.biblioteca_thymeleaf.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorDTO {

  private Long id;
  private String nombre;
  private String nacionalidad;
  private Boolean estado;
}
