package com.example.demo.service;

import com.example.demo.component.transaction.ComponentProxy;
import com.example.demo.dao.Dao;
import com.example.demo.dto.Transaccion;
import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.example.demo.dto.VentaResponse;
import com.example.demo.exception.TransactionVentaException;
import com.example.demo.service.venta.VentasService;
import com.example.demo.service.venta.VentasServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VentasServiceImplTest {

    @Mock
    private Dao<Venta> ventaDao;

    @Mock
    private ComponentProxy<Transaccion> transactionComponentProxy;

    @InjectMocks
    private VentasService ventasService = new VentasServiceImpl();

    @Test
    public void generarVentaTestCorrecto() throws JMSException {

        Transaccion out = new Transaccion();
        out.setCodigo("200");
        out.setId("1");
        out.setMensaje("Correcto");
        out.setMonto(new BigDecimal("10000"));
        doReturn(out).when(transactionComponentProxy).ejecutar(any(Transaccion.class));

        VentaResponse response = ventasService.generarVenta(new Venta(),new Usuario());
        assertEquals(out.getCodigo(),response.getEstadoOperacion());
        assertEquals(out.getMensaje(),response.getMensaje());

    }

    @Test(expected = TransactionVentaException.class)
    public void generarVentaTestCorrecto_RespuestaError() throws JMSException {

        Transaccion out = new Transaccion();
        out.setCodigo("500");
        out.setId("1");
        out.setMensaje("Error");
        out.setMonto(new BigDecimal("10000"));
        doReturn(out).when(transactionComponentProxy).ejecutar(any(Transaccion.class));

        ventasService.generarVenta(new Venta(),new Usuario());
    }

    @Test
    public void generarVentaTestCorrecto_SinRespuestaError() throws JMSException {

        Transaccion out = new Transaccion();
        out.setCodigo("404");
        out.setId("1");
        out.setMensaje("Sin Respuesta");
        out.setMonto(new BigDecimal("10000"));
        doReturn(out).when(transactionComponentProxy).ejecutar(any(Transaccion.class));

        VentaResponse response = ventasService.generarVenta(new Venta(),new Usuario());
        assertNotNull(response);
    }

    @Test(expected = TransactionVentaException.class)
    public void generarVentaTestCorrecto_Null() throws JMSException {


        doReturn(null).when(transactionComponentProxy).ejecutar(any(Transaccion.class));

        ventasService.generarVenta(new Venta(),new Usuario());
    }

    @Test
    public void obtenerVentasPorClienteTest(){

        doReturn(Collections.emptyList()).when(ventaDao).listar(any(Usuario.class));
        List<Venta> response = ventasService.obtenerVentasPorCliente(new Usuario());
        assertTrue(response.isEmpty());
    }
}
