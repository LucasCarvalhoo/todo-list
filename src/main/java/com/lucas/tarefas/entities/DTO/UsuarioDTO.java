package com.lucas.tarefas.entities.DTO;

import com.lucas.tarefas.entities.enums.Roles;

public record UsuarioDTO(String name, String email, String senha, Roles role) {
}
