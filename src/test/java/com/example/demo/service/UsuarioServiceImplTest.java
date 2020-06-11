package com.example.demo.service;

import com.example.demo.dto.Usuario;
import com.example.demo.service.usuario.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceImplTest {

    private UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

    @Before
    public void before(){
        usuarioService.setUpUsuarios();
    }

    @Test
    public void obtenerUsuarioPorUser(){
        Usuario usuario = usuarioService.obtenerUsuarioPorUser("user");
        assertNotNull(usuario);
    }
}
