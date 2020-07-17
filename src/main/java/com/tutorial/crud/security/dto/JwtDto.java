package com.tutorial.crud.security.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class JwtDto {

    @NonNull
    private String token;
    @NonNull
    private String bearer = "Bearer";
    @NonNull
    private String nombreUsuario;
    @NonNull
    private Collection<? extends GrantedAuthority> authorities;

}
