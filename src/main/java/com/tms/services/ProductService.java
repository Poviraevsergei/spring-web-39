package com.tms.services;

import com.tms.model.Product;
import com.tms.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public boolean addProduct(Integer productId, Integer userId) {
        return productRepository.addProductByUser(productId, userId) > 0;
    }

    public Page<Product> getProductsWithPagination(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public List<Product> getProductsWithSorting(String sortField) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, sortField));
    }
}
