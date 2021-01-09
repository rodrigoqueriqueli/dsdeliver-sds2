package com.devsuperior.dsdeliver.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.services.OrderService;

//controlador Rest pra trabalhar com o produto
@RestController
@RequestMapping(value = "/orders")  //definindo o caminho do recurso
public class OrderController {

	@Autowired
	private OrderService service;
	
	//fazendo o endpoint pra metodo get do http (endpoint pra trazer todos os produtos ordenados por nome
	//ResponseEntity tipo especifico do springboot que encapsula uma resposta a uma requisicao http
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll(){
		List<OrderDTO> list = service.findAll();
		
		//retornar objeto do tipo ResponseEntity
		return ResponseEntity.ok().body(list); //ok metodo que cria resposta que deu sucesso (code 200) 
	}
	
	//endpoint para salvar - metodo http post para salvar no banco
	//O OrderDTO dto recebido eh um JSON passado no corpo da requisicao..o framework converte o JSON num objeto java do tipo OrderDTO
	@PostMapping
	public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO dto){
		dto = service.insert(dto);
		
		//chamada pra trazer a URI do recurso criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
}
