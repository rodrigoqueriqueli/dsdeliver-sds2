package com.devsuperior.dsdeliver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
