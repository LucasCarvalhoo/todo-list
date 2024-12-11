package com.lucas.tarefas.exception;

import java.io.Serial;

public class CategoriaNaoCriadaException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public CategoriaNaoCriadaException(String message){
        super(message);
    }
}
