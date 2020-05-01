package com.jfb.supermercado.api.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jfb.supermercado.api.domain.Categoria;
import com.jfb.supermercado.api.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

			/* 
			 * O código abaixo é o mesmo do acima com a diferença que no de cima estou usando o
			 * padrão de nomes do spring data e o abaixo criando a consulta em JPQL.
			 *  
			 * @Transactional(readOnly = true)
			 * @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
			 * Page<Produto> search(@Param("nome") String nome,
			 *		@Param("categorias") List<Categoria> categorias, Pageable pageRequest); */
}