package com.generation.farmacia.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository; // variavel que contem todos os atributos da model

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll()); // SELECT * FROM tb_produtos
	}

	@GetMapping("/{id}") // entre chaves: parametro é uma variavel
	public ResponseEntity<Produto> getAll(@PathVariable Long id) {
		return produtoRepository.findById(id) // Selecionar atributos pelo id: SELECT * FROM tb_produtos WHERE id = id;

				.map(resposta -> ResponseEntity.ok(resposta))

				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nome/{nome}") // entre chaves: parametro é uma variavel
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));

	}

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {// Popular tabela
		if (categoriaRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)// Resposta para um uso especifico do CORPO da requisição
					.body(produtoRepository.save(produto));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);
		// INSERT INTO tb_produtos (nome, descricao, console, foto, quantidade, preco,
		// categoria);
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {// Atualizar tabela
		// UPTADE tb_produtos SET nome = ?, descricao = ?, console = ?, foto = ?,
		// quantidade = ?, preco = ?, categoria = ? WHERE id = id;

		if (produtoRepository.existsById(produto.getId())) {
			if (categoriaRepository.existsById(produto.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não existe", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT) // Resposta para sem conteudo
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		produtoRepository.deleteById(id);
		// DELETE * FROM tb_produtos WHERE id = id;
		
	}
		
	@GetMapping("preco/min/{min}/max/{max}")
	public ResponseEntity<List<Produto>> getPrecoEntre(@PathVariable BigDecimal min, BigDecimal max){ 
	return ResponseEntity.ok(produtoRepository.findByPrecoBetween(min, max));//falta uma comparação de preço com os parametros
	// Consulta pelo preço entre parametros
	}
		
		
	@GetMapping("AND/nome/{nome}/marca/{marca}")
	public ResponseEntity<List<Produto>> getProdutoAnd(@PathVariable String nome, String marca){ 
	return ResponseEntity.ok(produtoRepository.findAllByNomeAndMarca(nome, marca));
	// Consulta pelo produto com parametros AND
		
	}
	
	@GetMapping("OR/nome/{nome}/marca/{marca}")
	public ResponseEntity<List<Produto>> getProdutoOr(@PathVariable String nome, String marca){ 
	return ResponseEntity.ok(produtoRepository.findAllByNomeOrMarca(nome, marca));
	// Consulta pelo produto com parametros OR
		
	}


}
