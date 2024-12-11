package com.lucas.tarefas.exception;

import java.io.Serial;

public class UsuarioNaoEncontradoException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(Long id) {
        super("Registro não encontrado com o id: " + id);
    }
}
