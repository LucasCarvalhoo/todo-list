package com.lucas.tarefas.controllers;

import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
     }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Usuario criarUsuario(@RequestBody @Valid Usuario usuario){
        return usuarioService.criarUsuario(usuario);
    }
}
