package com.generation.farmacia.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome")String Nome); 
    
   
	 
	public List<Produto>  findByPrecoBetween(BigDecimal min, BigDecimal max);

	 // MySQL: *SELECT * FROM tb_produtos WHERE preco BETWEEN min AND max;
	 	
	public List<Produto> findAllByNomeAndMarca(String nome, String marca);
	
	
	// MySQL: SELECT * FROM tb_produtos WHERE nome = nome AND preco = preco;
		 
	public List<Produto> findAllByNomeOrMarca(String nome, String marca);
	
	// MySQL: SELECT * FROM tb_produtos WHERE nome = nome OR preco = preco;
}
