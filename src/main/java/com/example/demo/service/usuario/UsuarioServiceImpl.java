package com.example.demo.service.usuario;

import com.example.demo.dto.Usuario;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    List<Usuario> usuarios = new ArrayList<>();

    @PostConstruct
    public void setUpUsuarios(){
        usuarios.add(new Usuario(1L,"nombre1","user"));
        usuarios.add(new Usuario(2L,"nombre2","otherUser"));
    }

    @Override
    public Usuario obtenerUsuarioPorUser(String user) {
        Optional<Usuario> usuario =  usuarios.stream().filter(u -> u.getUser().compareTo(user) == 0).findFirst();
        return usuario.orElse(null);
    }
}
