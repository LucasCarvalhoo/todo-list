package com.lucas.tarefas.exception;

import java.io.Serial;

public class TarefaNaoCriadaException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public TarefaNaoCriadaException(String message){
        super("Tarefa não criada: " + message);
    }
}
