package com.example.comissaoapi.tarefas.facade;

import com.example.comissaoapi.tarefas.Tarefa;
import com.example.comissaoapi.tarefas.TarefaRepository;
import com.example.comissaoapi.tarefas.dto.TarefaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaFacade {
    @Autowired
    private TarefaRepository repository;

    public TarefaDTO criar(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setTitulo(tarefaDTO.getTitulo());
        repository.save(tarefa);
        tarefaDTO.setId(tarefa.getId());
        return tarefaDTO;
    }

    public TarefaDTO atualizar(TarefaDTO tarefaDTO, Long tarefaId) {
        Tarefa tarefaDatabase = repository.getOne(tarefaId);
        tarefaDatabase.setTitulo(tarefaDTO.getTitulo());
        tarefaDatabase.setDescricao(tarefaDTO.getDescricao());
        repository.save(tarefaDatabase);
        return tarefaDTO;
    }

    private TarefaDTO converter(Tarefa tarefa) {
        TarefaDTO result = new TarefaDTO();
        result.setId(tarefa.getId());
        result.setTitulo(tarefa.getTitulo());
        result.setDescricao(tarefa.getDescricao());
        return result;
    }

    public List<TarefaDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public String delete(Long tarefaId) {
        repository.deleteById(tarefaId);
        return "DELETED";
    }
}
