package com.devsuperior.dscomercio.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscomercio.dto.ProductDTO;
import com.devsuperior.dscomercio.dto.ProductMinDTO;
import com.devsuperior.dscomercio.entities.Product;
import com.devsuperior.dscomercio.repositories.ProductRepository;
import com.devsuperior.dscomercio.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscomercio.tests.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingProductId, nonExistingProductId;
	private Product product;
	private String productName;
	private PageImpl<Product> page;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() throws Exception {

		existingProductId = 1L;
		nonExistingProductId = 1000L;

// inicialização do test searchByName
		productName = "PS5";
		product = ProductFactory.createProduct2(productName);
		page = new PageImpl<>(List.of(product));
	
		productDTO = new ProductDTO(product);

// simulando os comportamentos 

		Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());

// simulando o comportamento da consulta retorna um produto pelo nome paginada
		Mockito.when(repository.searchByName(any(), (Pageable) any())).thenReturn(page);

		Mockito.when(repository.save(any())).thenReturn(product);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = service.findById(existingProductId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingProductId);
		Assertions.assertEquals(result.getName(), product.getName());
	}

	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}

	@Test
	public void findAllShouldReturnPagedProductMinDTO() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<ProductMinDTO> result = service.findAll(productName, pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), productName);
	}

	
	@Test
	public void insertShouldReturnProductDTO() {
		
		ProductDTO result = service.insert(productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), product.getId());
		
	}
}
