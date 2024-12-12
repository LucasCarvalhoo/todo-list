package com.lucas.tarefas.Repository;

import com.lucas.tarefas.entities.Categoria;
import com.lucas.tarefas.entities.Tarefa;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


    @Query("SELECT t FROM Tarefa t WHERE t.categoria.usuario.id = :usuarioId")
    List<Tarefa> listarTarefasPorUsuario(@Param("usuarioId") Long usuarioId);

}
