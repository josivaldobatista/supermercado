    package com.jfb.supermercado.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfb.supermercado.api.domain.Categoria;
import com.jfb.supermercado.api.dto.CategoriaDTO;
import com.jfb.supermercado.api.repositories.CategoriaRepository;
import com.jfb.supermercado.api.services.exceptions.DataIntegrityException;
import com.jfb.supermercado.api.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objecto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    @Transactional
    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId()); // Aqui estou verificando se o objeto existe.
		updateData(newObj, obj);
		return repo.save(newObj);
	}

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                "Não é possivel excluir uma categoria que possui produtos.");
        }
    }

    public List<Categoria> findAll () {
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    // Metódo para instanciar uma Categoria a parti de um DTO.
    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}