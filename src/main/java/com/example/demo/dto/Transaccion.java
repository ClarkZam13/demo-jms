package com.example.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class Transaccion implements Serializable {

    public Transaccion(BigDecimal monto, Long idusuario){
        this.monto = monto;
        this.idUsuario = idusuario;
    }

    private String codigo;
    private String mensaje;
    private String id;
    private BigDecimal monto;
    private Long idUsuario;
}
