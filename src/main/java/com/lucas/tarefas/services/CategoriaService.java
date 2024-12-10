package com.lucas.tarefas.services;

import com.lucas.tarefas.Repository.CategoriaRepository;
import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

//    public Categoria criarCategoria(@Valid Categoria categoria){
//        return categoriaRepository.save(categoria);
//    }
}
