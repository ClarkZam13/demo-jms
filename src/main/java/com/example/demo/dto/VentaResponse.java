package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VentaResponse {

    private String estadoOperacion;
    private String mensaje;
    private Venta venta;

    public VentaResponse(Venta venta){
        this.venta = venta;
    }
}
