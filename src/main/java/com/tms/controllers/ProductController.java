package com.tms.controllers;

import com.tms.model.Product;
import com.tms.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<HttpStatus> addProduct(@PathVariable Integer productId, @PathVariable Integer userId) {
        if (productService.addProduct(productId, userId)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/sort/{sortField}")
    public ResponseEntity<List<Product>> getProductsWithSorting(@PathVariable String sortField) {
        List<Product> products = productService.getProductsWithSorting(sortField);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/pagination/{page}/{size}")
    public ResponseEntity<Page<Product>> getProductsWithSorting(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Product> products = productService.getProductsWithPagination(page, size);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
