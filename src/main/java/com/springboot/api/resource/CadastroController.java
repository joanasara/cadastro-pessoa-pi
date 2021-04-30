package com.springboot.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.api.model.Cadastro;
import com.springboot.api.repository.CadastroRespository;

@RestController
@RequestMapping("/cadastros")
public class CadastroController {

	@Autowired
	private CadastroRespository repository;

	@GetMapping
	public List<Cadastro> listar() {

		return repository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Cadastro> criar(@Valid @RequestBody  Cadastro cadastro, HttpServletResponse response) {
		Cadastro cadastroSalvo = repository.save(cadastro);
		
	 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
		.path("/{codigo}").buildAndExpand(cadastroSalvo.getCodigo()).toUri();
	    response.setHeader("Location", uri.toASCIIString());
	    
	    
	    return ResponseEntity.created(uri).body(cadastroSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cadastro> buscarPeloCodigo(@PathVariable Long codigo) {
		Cadastro cadastro = repository.findById(codigo).orElse(null);
		return cadastro != null ? ResponseEntity.ok(cadastro) : ResponseEntity.notFound().build();
	}
	
	

}
