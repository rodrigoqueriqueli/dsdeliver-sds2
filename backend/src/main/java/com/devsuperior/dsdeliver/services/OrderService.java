package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.repositories.OrderRepository;

@Service  //poderia ter usado tb @Component, mas usei o service pra ficar mais semantico
public class OrderService {
	
	@Autowired //notacao faz resolucao de dependencia de forma transparente
	private OrderRepository repository;
	
	/* Caso nao usar o Autowired (Injecao de dependencia desacoplado
	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}*/

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findOrdersWithProducts(); 
		
		//transformar cada elementa da lista em ProductDTO
		//stream() aceita operacoes de alta ordem, programacao adicional
		//o map aplica uma funcao em todos os elementos da lista, transformando em uma nova lista lista
		//transformando lista de Product pra ProductDTO por meio da expressao lambda
		//no final da linha abaixo reconverte de stream para lista
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}
}
