package com.generation.farmacia.repository;

//import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome")String Nome); 
    
    
	 
	/**public List  findAllByPrecoGreaterThanOrderByPreco(BigDecimal preco);
	
	 *  Método Personalizado - Buscar todos os Produtos cujo o preço seja maior 
	 *  do que um valor digitado ordenado pelo preço em ordem crescente
	 *  
	 *  MySQL: select * from tb_produtos where preco > preco order by preco;
	 */
	
	 
	/**public ListfindAllByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);
	
		 *  Método Personalizado - Buscar todos os Produtos cujo o preço seja menor 
		 *  do que um valor digitado ordenado pelo preço em ordem decrescente
		 *  
		 *  MySQL: select * from tb_produtos where preco < preco order by preco desc;
		 */
}
