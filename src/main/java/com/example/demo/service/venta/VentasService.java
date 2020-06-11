package com.example.demo.service.venta;

import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.example.demo.dto.VentaResponse;

import javax.jms.JMSException;
import java.util.List;

public interface VentasService {

    List<Venta> obtenerVentasPorCliente(Usuario usuario);

    VentaResponse generarVenta(Venta venta, Usuario usuario) throws JMSException;
}
