package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.exception.RecordNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findById(@Positive Long id){
        return Optional.ofNullable(usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }

    public Optional<Usuario> findByName(@NotBlank String name){
        return usuarioRepository.findByName(name);
    }

    public Usuario criarUsuario(@Valid Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario utualizarUsuario(@Positive Long id, Usuario usuario){
        return usuarioRepository.findById(id)
                .map(recordFound -> {
                    if(usuario.getName() != null) {
                        recordFound.setName(usuario.getName());
                    }
                    if(usuario.getEmail() != null){
                        recordFound.setEmail(usuario.getEmail());
                    }
                    if(usuario.getSenha() != null){
                        recordFound.setSenha(usuario.getSenha());
                    }

                    // Limpa as antigas e associa as novas categorias ao usuário atual
                    if(usuario.getCategorias() != null){
                        recordFound.getCategorias().forEach(categoria -> categoria.setUsuario(null));
                        usuario.getCategorias().forEach(categoria -> categoria.setUsuario(recordFound));
                        recordFound.setCategorias(usuario.getCategorias());
                    }
                    return usuarioRepository.save(recordFound);
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
