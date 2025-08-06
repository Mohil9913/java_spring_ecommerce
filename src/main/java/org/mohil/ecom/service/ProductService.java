package org.mohil.ecom.service;

import org.mohil.ecom.model.Product;
import org.mohil.ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepo pr;

    public List<Product> getAllProducts() {
        return pr.findAll();
    }

    public Product getProductById(Long productId) {
        return pr.findById(productId).orElse(new Product(-1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());

        return pr.save(product);
    }

    public void delete(Product product) {
        pr.delete(product);
    }

    public List<Product> searchProducts(String keyword) {
//        return pr.searchProducts(keyword);
        return pr.findByNameContainingOrDescriptionContainingOrCategoryContaining(keyword, keyword, keyword);
    }
}
