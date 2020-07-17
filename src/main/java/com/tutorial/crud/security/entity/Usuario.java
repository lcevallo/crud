package com.tutorial.crud.security.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NonNull
    private String nombre;

    @NonNull
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;

    @NonNull
    @NotNull
    private String email;


    @NonNull
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id")
    , inverseJoinColumns = @JoinColumn( name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();
}
