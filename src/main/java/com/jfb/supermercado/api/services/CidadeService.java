package com.jfb.supermercado.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfb.supermercado.api.domain.Cidade;
import com.jfb.supermercado.api.repositories.CidadeRepository;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findByEstado(Integer estadoId) {
        return repo.findCidades(estadoId);
    }
}