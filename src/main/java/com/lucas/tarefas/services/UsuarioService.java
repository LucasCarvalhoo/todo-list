package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Usuario;
import jakarta.validation.constraints.Positive;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    Optional<Usuario> findById(@Positive Long id){
        return usuarioRepository.findById(id);
    }

    List<Usuario> findByName(@Positive String nome){
        return usuarioRepository.findByName(nome);
    }

    public Usuario criarUsuario(@NotNull @Valid Usuario usuario){
        return usuarioRepository.save(usuario);
    }

}
