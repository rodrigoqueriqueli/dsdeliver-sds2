package com.devsuperior.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;

@Service  //poderia ter usado tb @Component, mas usei o service pra ficar mais semantico
public class OrderService {
	
	@Autowired //notacao faz resolucao de dependencia de forma transparente
	private OrderRepository repository;
	
	@Autowired //dependencia para associar produtos ao pedido
	private ProductRepository productRepository;
	
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
	
	//retorna um OrderDTO correspondente ao pedido que inseri no banco...passando como argumento o objeto que quero salvar no banco
	//metodo para inserir um pedido no banco, ja associado com os produtos dele
	@Transactional
	public OrderDTO insert(OrderDTO dto){
		
		//o objeto(OrderDTO dto) passado sera salvo no banco
		
		//instanciando um objeto order com esses valores, o id eh nulo pois nao eh passado pelo usuario, assim como o instante e o status que sao passados automaticos
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(), OrderStatus.PEDING);
		
		//associar produtos que vieram dentro do objeto dto...percorrer todos ProductDTo dentro do OrderDTO passado
		for (ProductDTO p : dto.getProducts()) {
			//metodo getOne usado apenas pra salvar a associacao da entidade produto dentro de pedido, o produto nao eh salvo no banco, apenas a associacao
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		
		//orderRepository
		//save retorno uma referencia pro objeto salvo..variavel order guarda referencia pra objeto salvo
		order = repository.save(order);
		//na hora de retornar retorno o objeto convertido para DTO
		return new OrderDTO(order);
	}
	
}
