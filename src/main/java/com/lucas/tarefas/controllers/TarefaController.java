package com.lucas.tarefas.controllers;

import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.Tarefa;
import com.lucas.tarefas.services.TarefaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

    private final TarefaService tarefaService;
    private final UsuarioRepository usuarioRepository;


    public TarefaController(TarefaService tarefaService, UsuarioRepository usuarioRepository) {
        this.tarefaService = tarefaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Tarefa> listarTarefas(){
        return tarefaService.list();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Tarefa> listarTarefasPorUsuario(@PathVariable Long usuarioId){
        return tarefaService.listarTarefasPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Optional<Tarefa> buscarTarefaPorId(@PathVariable Long tarefaId){
        return tarefaService.buscarTarefaPorId(tarefaId);
    }

    @PostMapping
    public Tarefa salvarTarefa(@RequestBody Tarefa tarefa, @RequestParam Long usuarioId, @RequestParam Long categoriaId, @AuthenticationPrincipal UserDetails userDetails){
        Long usuarioAutenticadoId = Long.parseLong(userDetails.getUsername());
        return tarefaService.salvarTarefa(tarefa, usuarioId, categoriaId, usuarioAutenticadoId);
    }

    @PutMapping("/{tarefaId}")
    public Tarefa atualizarTarefas(@RequestBody Tarefa tarefa, @PathVariable Long tarefaId){
        return tarefaService.atualizarTarefa(tarefa, tarefaId);
    }

    @DeleteMapping("/{tarefaId}")
    public void deletarTarefas(@PathVariable Long tarefaId){
        tarefaService.deletarTarefa(tarefaId);
    }

}