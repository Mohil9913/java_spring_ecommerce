package org.mohil.ecom.controller;

import org.mohil.ecom.model.Product;
import org.mohil.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {

    @Autowired
    public ProductService ps;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> product() {
        return new ResponseEntity<>( ps.getAllProducts(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
        Product product = ps.getProductById(productId);
        if(product.getId() < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( product, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable("productId") Long productId) {
        Product product = ps.getProductById(productId);
        if(product.getId() < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(product.getImageData());
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile
    ) {
        Product savedProduct = null;
        try {
            savedProduct = ps.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
