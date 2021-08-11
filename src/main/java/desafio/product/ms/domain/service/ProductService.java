package desafio.product.ms.domain.service;

import desafio.product.ms.domain.entity.Product;
import desafio.product.ms.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String search, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.searchByParams(search,search, minPrice, maxPrice);
    }

    public List<String> listProductMethods() {
        Method[] methods = Product.class.getDeclaredMethods();
        return getNameMethodsGet(methods);

    }

    private static List<String> getNameMethodsGet(Method[] methods) {
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods)
            if(method.getName().startsWith("get"))
                methodNames.add(method.getName());
        return methodNames;
    }

    public Optional<Product> findProduct(UUID id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

}
