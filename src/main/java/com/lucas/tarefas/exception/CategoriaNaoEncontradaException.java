package com.lucas.tarefas.exception;

import java.io.Serial;

public class CategoriaNaoEncontradaException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public CategoriaNaoEncontradaException(String message){
        super(message);
    }

}
