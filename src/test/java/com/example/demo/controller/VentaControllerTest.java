package com.example.demo.controller;

import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.example.demo.dto.VentaResponse;
import com.example.demo.exception.TransactionVentaException;
import com.example.demo.service.usuario.UsuarioService;
import com.example.demo.service.venta.VentasService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VentaControllerTest {

    @Mock
    VentasService ventasService;

    @Mock
    Principal principal;

    @Mock
    UsuarioService usuarioService;

    @InjectMocks
    VentaController ventaController = new VentaController();

    @Test
    public void crearVentaTest() throws JMSException {
        Venta venta = new Venta();
        venta.setMonto(new BigDecimal("10000"));
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(usuario).when(usuarioService).obtenerUsuarioPorUser(anyString());

        VentaResponse vr = new VentaResponse(venta);
        vr.setEstadoOperacion("200");
        vr.setMensaje("Mensaje");
        doReturn(vr).when(ventasService).generarVenta(any(Venta.class),any(Usuario.class));

        ResponseEntity<VentaResponse> response  = ventaController.crearVenta(venta,principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void crearVentaTest_NullVenta() throws JMSException {
        Venta venta = new Venta();
        venta.setMonto(new BigDecimal("10000"));
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(usuario).when(usuarioService).obtenerUsuarioPorUser(anyString());

        VentaResponse vr = new VentaResponse(venta);
        vr.setEstadoOperacion("200");
        vr.setMensaje("Mensaje");
        doReturn(vr).when(ventasService).generarVenta(any(Venta.class),any(Usuario.class));

        ResponseEntity<VentaResponse> response  = ventaController.crearVenta(null,principal);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void crearVentaTest_UsuarioVenta() throws JMSException {
        Venta venta = new Venta();
        venta.setMonto(new BigDecimal("10000"));
        doReturn("user").when(principal).getName();

        doReturn(null).when(usuarioService).obtenerUsuarioPorUser(anyString());

        VentaResponse vr = new VentaResponse(venta);
        vr.setEstadoOperacion("200");
        vr.setMensaje("Mensaje");
        doReturn(vr).when(ventasService).generarVenta(any(Venta.class),any(Usuario.class));

        ResponseEntity<VentaResponse> response  = ventaController.crearVenta(venta,principal);
        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    public void crearVentaTest_JMSException() throws JMSException {
        Venta venta = new Venta();
        venta.setMonto(new BigDecimal("10000"));
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(usuario).when(usuarioService).obtenerUsuarioPorUser(anyString());

        VentaResponse vr = new VentaResponse(venta);
        vr.setEstadoOperacion("200");
        vr.setMensaje("Mensaje");
        doThrow(new JMSException("Exception")).when(ventasService).generarVenta(any(Venta.class),any(Usuario.class));

        ResponseEntity<VentaResponse> response  = ventaController.crearVenta(venta,principal);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void crearVentaTest_TransactionVentaException() throws JMSException {
        Venta venta = new Venta();
        venta.setMonto(new BigDecimal("10000"));
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(usuario).when(usuarioService).obtenerUsuarioPorUser(anyString());

        VentaResponse vr = new VentaResponse(venta);
        vr.setEstadoOperacion("200");
        vr.setMensaje("Mensaje");
        doThrow(new TransactionVentaException("404","Mensaje")).when(ventasService).generarVenta(any(Venta.class),any(Usuario.class));

        ResponseEntity<VentaResponse> response  = ventaController.crearVenta(venta,principal);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void obtenerVentas(){
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(usuario).when(usuarioService).obtenerUsuarioPorUser(anyString());

        Venta venta = new Venta();
        List<Venta> ventas = Collections.singletonList(venta);
        doReturn(ventas).when(ventasService).obtenerVentasPorCliente(any(Usuario.class));

        ResponseEntity<List<Venta>> response = ventaController.obtenerVentas(null,principal);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void obtenerVentas_usuarionull(){
        doReturn("user").when(principal).getName();

        Usuario usuario = new Usuario();
        usuario.setUser("user");
        doReturn(null).when(usuarioService).obtenerUsuarioPorUser(anyString());

        Venta venta = new Venta();
        List<Venta> ventas = Collections.singletonList(venta);
        doReturn(ventas).when(ventasService).obtenerVentasPorCliente(any(Usuario.class));

        ResponseEntity<List<Venta>> response = ventaController.obtenerVentas(null,principal);
        assertEquals(HttpStatus.PRECONDITION_FAILED,response.getStatusCode());
    }
}
