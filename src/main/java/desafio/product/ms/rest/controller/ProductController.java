package desafio.product.ms.rest.controller;


import desafio.product.ms.domain.entity.Product;
import desafio.product.ms.domain.service.ProductService;
import desafio.product.ms.rest.converter.ProductMapper;
import desafio.product.ms.rest.dto.ProductDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productMapper.createProduct(productDto));
        return new ResponseEntity<>(product.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id,
                                           @Valid @RequestBody ProductDto productDto) {
        Optional<Product> product = productService.findProduct(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDto.setId(id);
        Product updatedProduct = productService.updateProduct(productMapper.createProduct(productDto));
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable UUID id) {
        Optional<Product> product = productService.findProduct(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productMapper.createProductDto(product.get()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> products = productService.findAllProducts()
                .stream()
                .map(productMapper::createProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam(value = "q", required = false) String search,
                                            @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
                                            @RequestParam(value = "max_price", required = false) BigDecimal maxPrice) {

        List<Product> products = productService.searchProducts(search, minPrice, maxPrice);

        return new ResponseEntity<>(products.stream().map(productMapper::createProductDto), HttpStatus.OK);
    }

    @GetMapping("/list_product_methods")
    public ResponseEntity<?> listProductMethods() {

        List<String> products = productService.listProductMethods();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        Optional<Product> product = productService.findProduct(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
