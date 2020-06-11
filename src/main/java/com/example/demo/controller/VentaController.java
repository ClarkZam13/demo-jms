package com.example.demo.controller;

import com.example.demo.exception.TransactionVentaException;
import com.example.demo.dto.Usuario;
import com.example.demo.dto.Venta;
import com.example.demo.dto.VentaResponse;
import com.example.demo.service.usuario.UsuarioService;
import com.example.demo.service.venta.VentasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentasService ventasService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<Venta>> obtenerVentas(@RequestParam(name = "fecha") Optional<LocalDate> fecha, Principal principal){
        Usuario usuario = usuarioService.obtenerUsuarioPorUser(principal.getName());
        if(nonNull(usuario)){
            return new ResponseEntity<>(ventasService.obtenerVentasPorCliente(usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }

    }

    @PostMapping("/generar")
    public ResponseEntity crearVenta(@RequestBody Venta venta, Principal principal){

        if(isNull(venta)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioService.obtenerUsuarioPorUser(principal.getName());
        if(isNull(usuario)){
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        try {
            return new ResponseEntity<>(ventasService.generarVenta(venta, usuario), HttpStatus.OK);
        }catch (JMSException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TransactionVentaException e) {
            e.printStackTrace();
            return ResponseEntity.status(Integer.parseInt(e.getCodigoError()))
                    .body(e.getMensaje());
        }
    }
}
