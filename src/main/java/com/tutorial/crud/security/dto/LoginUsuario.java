package com.tutorial.crud.security.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class LoginUsuario {

    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String password;
}
