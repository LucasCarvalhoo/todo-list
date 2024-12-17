package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.CategoriaRepository;
import com.lucas.tarefas.Repository.TarefaRepository;
import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Tarefa;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.entities.enums.Status;
import com.lucas.tarefas.exception.CategoriaNaoEncontradaException;
import com.lucas.tarefas.exception.TarefaNaoCriadaException;
import com.lucas.tarefas.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Tarefa> list(){
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarTarefasPorUsuario(Long usuarioId){
        return tarefaRepository.listarTarefasPorUsuario(usuarioId);
    }

    public Optional<Tarefa> buscarTarefaPorId(Long id){
        return tarefaRepository.findById(id);
    }

    public Tarefa salvarTarefa(Tarefa tarefa, Long usuarioId, Long categoriaId, Long usuarioAutenticadoId){
        if(tarefa.getTitulo() == null){
            throw new TarefaNaoCriadaException("Tarefa sem titulo");
        }
        if(tarefa.getDescricao() == null){
            throw new TarefaNaoCriadaException("Tarefa sem descrição");
        }
        if(tarefa.getStatus() == null){
            throw new TarefaNaoCriadaException("Tarefa sem status");
        }
        if(tarefa.getData_vencimento() == null){
            throw new TarefaNaoCriadaException("Tarefa sem data fim");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + categoriaId));

        if (!categoria.getUsuario().getId().equals(usuarioId) && !usuarioAutenticadoId.equals(usuarioId)) {
            throw new IllegalArgumentException("O usuário não pode acessar a categoria ou tarefa.");
        }

        tarefa.setUsuario(usuario);
        tarefa.setCategoria(categoria);

        return tarefaRepository.save(tarefa);
    }


    public Tarefa atualizarTarefa(Tarefa tarefa, Long tarefaId){
        return tarefaRepository.findById(tarefaId)
                .map(recordFound -> {
                    recordFound.setTitulo(tarefa.getTitulo());
                    recordFound.setDescricao(tarefa.getDescricao());
                    recordFound.setStatus(tarefa.getStatus());
                    recordFound.setData_vencimento(tarefa.getData_vencimento());
                    if (tarefa.getCategoria() != null) {
                        Categoria categoria = categoriaRepository.findById(tarefa.getCategoria().getId())
                                .orElseThrow(() -> new CategoriaNaoEncontradaException("" + tarefa.getCategoria().getId()));
                        recordFound.setCategoria(categoria);
                    }
                    return tarefaRepository.save(recordFound);
                }).orElseThrow(() -> new TarefaNaoCriadaException("Tarefa não atualizada"));
    }

    public void deletarTarefa(Long tarefaId){
        tarefaRepository.deleteById(tarefaId);
    }

    public boolean isOwner(Long tarefaId, Long userId) {
        Tarefa tarefa = buscarTarefaPorId(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return tarefa.getUsuario().getId().equals(userId);
    }
}
