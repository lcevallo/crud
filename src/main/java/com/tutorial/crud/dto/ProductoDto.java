package com.tutorial.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {

    @NotBlank
    private String nombre;

    @Min(0)
    private Float precio;
}
