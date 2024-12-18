package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.CategoriaRepository;
import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.exception.CategoriaNaoCriadaException;
import com.lucas.tarefas.exception.CategoriaNaoEncontradaException;
import com.lucas.tarefas.exception.UsuarioNaoEncontradoException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Categoria> list(){
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarCategoriaPorUsuario(Long usuarioId){
        return categoriaRepository.findByUsuarioId(usuarioId);
    }

    public Categoria criarCategoria(Categoria categoria, Long usuarioId, Usuario usuarioLogado){
        if (categoria.getNome() == null) {
            throw new CategoriaNaoCriadaException("Nome da categoria está nulo.");
        }

        if (!usuarioId.equals(usuarioLogado.getId())) {
            throw new CategoriaNaoCriadaException("Usuário não tem permissão para criar categoria para outro usuário.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        categoria.setUsuario(usuario);
        usuario.getCategorias().add(categoria);
        return categoriaRepository.save(categoria);

    }

    public Categoria utualizarCategorias(Categoria categoria, Long categoriaId){
        return categoriaRepository.findById(categoriaId)
                .map(recordFound -> {
                    recordFound.setNome(categoria.getNome());
                    return categoriaRepository.save(recordFound);
                }).orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada"));
    }

    public void deletarCategorias(Long categoriaId){
        categoriaRepository.delete(categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada: " + categoriaId)));
    }
}
