package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nombre;
    private String user;

    public Usuario(Long id, String nombre, String user){
        this.id = id;
        this.nombre = nombre;
        this.user = user;
    }
}
