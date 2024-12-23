package com.lucas.tarefas.controllers;

import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> list(){
        return categoriaService.list();
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Categoria>> listarCategorias(@PathVariable Long usuarioId, @AuthenticationPrincipal Usuario usuarioLogado){
        List<Categoria> categorias = categoriaService.listarCategoriaPorUsuario(usuarioId, usuarioLogado);
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Categoria criarCategorias(@RequestBody Categoria categoria, @RequestParam Long usuarioId, @AuthenticationPrincipal Usuario usuarioLogado){
        return categoriaService.criarCategoria(categoria, usuarioId, usuarioLogado);
    }

    @PutMapping
    public Categoria atualizarCategorias(@RequestBody Categoria categoria, @RequestParam Long usuarioId, @RequestParam Long categoriaId, @AuthenticationPrincipal Usuario usuarioLogado){
        return categoriaService.utualizarCategorias(categoria, usuarioId, categoriaId, usuarioLogado);
    }

    @DeleteMapping("/{id}")
    public void deletarCategorias(@PathVariable Long id){
        categoriaService.deletarCategorias(id);
    }
}
