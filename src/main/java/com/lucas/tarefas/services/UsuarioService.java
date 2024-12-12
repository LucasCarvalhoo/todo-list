package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.exception.UsuarioNaoEncontradoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> list(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByName(@NotBlank String name){
        return usuarioRepository.findByName(name);
    }

    public Usuario criarUsuario(Usuario usuario){
        for (Categoria categoria : usuario.getCategorias()) {
            categoria.setUsuario(usuario);
        }
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
                }).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public void deletarUsuario(@Positive Long id){
        usuarioRepository.deleteById(id);
    }
}
