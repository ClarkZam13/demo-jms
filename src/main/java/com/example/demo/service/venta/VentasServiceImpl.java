package com.example.demo.service.venta;

import com.example.demo.component.transaction.ComponentProxy;
import com.example.demo.component.transaction.TransactionComponentProxy;
import com.example.demo.dao.Dao;
import com.example.demo.dao.venta.VentaDao;
import com.example.demo.dto.Transaccion;
import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.example.demo.dto.VentaResponse;
import com.example.demo.exception.TransactionVentaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.nonNull;

@Service("VentasService")
public class VentasServiceImpl implements VentasService {

    @Qualifier("ventaDao")
    @Autowired
    private Dao<Venta> ventaDao;

    @Autowired
    private ComponentProxy<Transaccion> transactionComponentProxy;


    private static final String CODIGO_SIN_RESPUESTA = "404";
    private static final String CODIGO_RESPUESTA_CORRECTA = "200";
    private static final String CODIGO_RESPUESTA_ERROR = "500";

    @Override
    public List<Venta> obtenerVentasPorCliente(Usuario usuario) {
        return ventaDao.listar(usuario);
    }

    @Override
    public VentaResponse generarVenta(Venta venta, Usuario usuario) {


        Transaccion respuesta = transactionComponentProxy.ejecutar(new Transaccion(venta.getMonto(),usuario.getId()));
        VentaResponse vr;
        if(nonNull(respuesta)){

            switch (respuesta.getCodigo()){
                case CODIGO_RESPUESTA_CORRECTA:
                    venta.setFecha(LocalDate.now());
                    venta.setIdUsuario(usuario.getId());
                    vr = new VentaResponse(venta);
                    VentaDao.ventasHechas.add(vr.getVenta());
                    convertTransaccionInVentaResponse(vr, respuesta);
                    break;
                case CODIGO_RESPUESTA_ERROR:
                    throw new TransactionVentaException(respuesta.getCodigo(),respuesta.getMensaje());
                case CODIGO_SIN_RESPUESTA:
                default:
                    vr = new VentaResponse();
                    break;
            }
        } else {
            throw  new TransactionVentaException("500","No se ha podido obtener información del resultado de la operación");
        }

        return vr;
    }

    private VentaResponse convertTransaccionInVentaResponse(VentaResponse vr, Transaccion t){
        vr.setEstadoOperacion(t.getCodigo());
        vr.setMensaje(t.getMensaje());
        vr.getVenta().setId(t.getId());
        return vr;
    }
}
