package com.example.demo.dao.venta;

import com.example.demo.dao.Dao;
import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.demo.fixture.DatosBasicos.ejecuciones;

@Service("ventaDao")
public class VentaDao implements Dao<Venta> {

    public static List<Venta> ventasHechas = new ArrayList<>();


    @Override
    public List<Venta> listar(Usuario usuario) {
        try {
            Thread.sleep(ejecuciones*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Objects.nonNull(usuario))
            return VentaDao.ventasHechas.stream().filter(
                     v -> v.getIdUsuario().compareTo(usuario.getId()) == 0)
                    .collect(Collectors.toList());
        else
            return Collections.emptyList();
    }
}
