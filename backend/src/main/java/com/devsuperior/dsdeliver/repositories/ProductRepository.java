package com.devsuperior.dsdeliver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsdeliver.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	//metodo auxiliar que vai buscar os produtos ja ordenado por nome (usando recurso do spring data jpa)
	List<Product> findAllByOrderByNameAsc();
}
