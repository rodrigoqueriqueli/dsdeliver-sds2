package com.devsuperior.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsdeliver.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	//metodo pra mostrar apenas pedidos pendentes e ordenados do mais antigo pro mais novo
	//liguaguem do JPQL e nao do SQL...o fetch faz o inner join e toca o banco 1x só
	//obj.products(atributo que representa a associacao) vem da classe Order linha 36 (nome da colecao set de produtos)
	@Query("SELECT DISTINCT obj FROM Order obj JOIN FETCH obj.products"
			+ " WHERE obj.status = 0 ORDER BY obj.moment ASC") 
	List<Order> findOrdersWithProducts();
}
