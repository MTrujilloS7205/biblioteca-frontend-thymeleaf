package com.michael.biblioteca_thymeleaf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LibroDTO {

  private Long id;
  private String titulo;
  private String isbn;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaPublicacion;
  private Boolean estado;

  private Long autorId;
  private String autorNombre;
}
