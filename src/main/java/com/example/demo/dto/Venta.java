package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Venta {

    private String id;
    private BigDecimal monto;
    private LocalDate fecha;
    @JsonIgnore
    private Long idUsuario;
}
