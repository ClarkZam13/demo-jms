package com.example.demo.dao;

import com.example.demo.dto.Usuario;

import java.util.List;

public interface Dao<T> {

    List<T> listar(Usuario usuario);
}
