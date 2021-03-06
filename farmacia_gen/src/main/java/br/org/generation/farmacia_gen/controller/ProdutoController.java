package br.org.generation.farmacia_gen.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.farmacia_gen.model.Produto;
import br.org.generation.farmacia_gen.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable long id) {
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	
	
	  @GetMapping("/if_else/{id}") public ResponseEntity<Optional<Produto>> getByIdIfElse(@PathVariable long id){
	  
	  Optional <Produto> resposta = produtoRepository.findById(id);
	  
	  if (resposta.isPresent()) 
	  { 
		  return ResponseEntity.ok(resposta); 
	  } 
	  else 
	  
	  { return
	  ResponseEntity.notFound().build(); 
	  }
	  
	  }
	 
	 
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
		return produtoRepository.findById(produto.getId())
				.map(resposta -> ResponseEntity.ok(produtoRepository.save(produto)))
				.orElse(ResponseEntity.notFound().build());
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable long id){
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	// Consulta por nome ou laborat??rio
	
		@GetMapping("/nome/{nome}/oulaboratorio/{laboratorio}")
		public ResponseEntity<List<Produto>> getByNomeOuLaboratorio(@PathVariable String nome, @PathVariable String laboratorio){
			return ResponseEntity.ok(produtoRepository.findByNomeOrLaboratorio(nome, laboratorio));
		}
		
		// Consulta por nome e laborat??rio
		
		@GetMapping("/nome/{nome}/elaboratorio/{laboratorio}")
		public ResponseEntity<List<Produto>> getByNomeELaboratorio(@PathVariable String nome, @PathVariable String laboratorio){
			return ResponseEntity.ok(produtoRepository.findByNomeAndLaboratorio(nome, laboratorio));
		}
		
		// Consulta por pre??o entre dois valores (Between)
		
		@GetMapping("/preco_inicial/{inicio}/preco_final/{fim}")
		public ResponseEntity<List<Produto>> getByPrecoEntre(@PathVariable BigDecimal inicio, @PathVariable BigDecimal fim){
			return ResponseEntity.ok(produtoRepository.buscarProdutosEntre(inicio, fim));
		}

}
