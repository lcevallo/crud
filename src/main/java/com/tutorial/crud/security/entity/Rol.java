package com.tutorial.crud.security.entity;

import com.tutorial.crud.security.enums.RolNombre;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;
}
