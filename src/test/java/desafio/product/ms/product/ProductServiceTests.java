package desafio.product.ms.product;

import desafio.product.ms.base.BaseTestController;
import desafio.product.ms.domain.entity.Product;
import desafio.product.ms.domain.repository.ProductRepository;
import desafio.product.ms.domain.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static desafio.product.ms.product.ProductServiceTestsFixture.newMockedProduct;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ProductServiceTests extends BaseTestController {

    private ProductService subject;
    private ProductRepository productRepository;


    @BeforeEach
    public void setUp(){
        productRepository = Mockito.mock(ProductRepository.class);
        subject = new ProductService(productRepository);
    }


    @Test
    public void shouldCreateOneProduct(){
        Product product = newMockedProduct();

        given(productRepository.save(any(Product.class))).willReturn(product);

        Product createdProduct = subject.createProduct(product);
        Assertions.assertNotNull(createdProduct);
        assertEqualsProperties(product, createdProduct);

        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);

    }


    @Test
    public void shouldUpdateOneProduct() {

        Product product = newMockedProduct();

        given(productRepository.save(any(Product.class))).willReturn(product);

        Product updatedProduct = subject.updateProduct(product);

        Assertions.assertNotNull(updatedProduct);
        assertEqualsProperties(product, updatedProduct);

        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }


    @Test
    public void shouldDeleteOneProduct() {
        doNothing().when(productRepository).delete(any(Product.class));

        subject.deleteProduct(UUID.randomUUID());

        verify(productRepository).deleteById(any(UUID.class));
        verifyNoMoreInteractions(productRepository);
    }


    @Test
    public void shouldReturnOneProduct() {
        Optional<Product> address = Optional.of(newMockedProduct());

        given(productRepository.findById(any(UUID.class))).willReturn(address);

        Optional<Product> findProductById = subject.findProduct(UUID.randomUUID());
        Assertions.assertTrue(findProductById.isPresent());

        assertEqualsProperties(address.get(), findProductById.get());

        verify(productRepository).findById(any(UUID.class));
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void shouldReturnAllProduct(){
        List<Product> products = new ArrayList<>();
        products.add(newMockedProduct());
        products.add(newMockedProduct());

        given(productRepository.findAll()).willReturn(products);

        List<Product> findAllProducts = subject.findAllProducts();
        Assertions.assertNotNull(findAllProducts);
        Assertions.assertEquals(2, findAllProducts.size());

        assertEqualsProperties(products.get(0), findAllProducts.get(0));
        assertEqualsProperties(products.get(1), findAllProducts.get(1));

        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);

    }

    private void assertEqualsProperties(Product expected, Product actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());

    }

}
