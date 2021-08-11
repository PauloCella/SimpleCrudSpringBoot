package desafio.product.ms.product;

import desafio.product.ms.base.BaseTestController;
import desafio.product.ms.domain.entity.Product;
import desafio.product.ms.domain.service.ProductService;
import desafio.product.ms.rest.controller.ProductController;
import desafio.product.ms.rest.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static desafio.product.ms.product.ProductControllerTestsFixture.newMockedProductDto;
import static desafio.product.ms.product.ProductServiceTestsFixture.newMockedProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ProductControllerTests extends BaseTestController {

    private ProductController subject;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = Mockito.mock(ProductService.class);
        subject = new ProductController(productService);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));

    }

    @Test
    public void shouldCreateOneProduct() {
        Product product = newMockedProduct();

        given(productService.createProduct(any(Product.class))).willReturn(product);

        ResponseEntity<?> createProductDto = subject.createProduct(newMockedProductDto());
        Assertions.assertNotNull(createProductDto);
        Assertions.assertNull(createProductDto.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, createProductDto.getStatusCode());

        verify(productService).createProduct(any(Product.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldUpdateOneProduct() {
        Optional<Product> product = Optional.of(newMockedProduct());

        given(productService.findProduct(any(UUID.class))).willReturn(product);
        given(productService.updateProduct(any(Product.class))).willReturn(product.get());

        ResponseEntity<?> updateProductDto = subject.updateProduct(UUID.randomUUID(), newMockedProductDto());
        Assertions.assertNotNull(updateProductDto.getBody());
        Assertions.assertEquals(HttpStatus.OK, updateProductDto.getStatusCode());

        verify(productService).findProduct(any(UUID.class));
        verify(productService).updateProduct(any(Product.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldDeleteOneProduct() {
        Optional<Product> product = Optional.of(newMockedProduct());
        product.get().setId(UUID.randomUUID());

        given(productService.findProduct(any(UUID.class))).willReturn(product);
        doNothing().when(productService).deleteProduct(product.get().getId());

        ResponseEntity<?> deleteProductDto = subject.deleteProduct(UUID.randomUUID());
        Assertions.assertNotNull(deleteProductDto);
        Assertions.assertNull(deleteProductDto.getBody());
        Assertions.assertEquals(HttpStatus.OK, deleteProductDto.getStatusCode());

        verify(productService).findProduct(any(UUID.class));
        verify(productService).deleteProduct(any(UUID.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldReturnOneProduct() {
        Optional<Product> product = Optional.of(newMockedProduct());

        given(productService.findProduct(any(UUID.class))).willReturn(product);

        ResponseEntity<?> productDto = subject.findProduct(UUID.randomUUID());
        Assertions.assertNotNull(productDto);
        Assertions.assertNotNull(productDto.getBody());
        Assertions.assertEquals(HttpStatus.OK, productDto.getStatusCode());
        assertEqualsProperties(product.get(), (ProductDto) productDto.getBody());

        verify(productService).findProduct(any(UUID.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldReturnAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(newMockedProduct());
        products.add(newMockedProduct());

        given(productService.findAllProducts()).willReturn(products);

        ResponseEntity<List<ProductDto>> findAllProductDto = subject.findAllProducts();
        Assertions.assertNotNull(findAllProductDto);
        Assertions.assertNotNull(findAllProductDto.getBody());
        Assertions.assertEquals(HttpStatus.OK, findAllProductDto.getStatusCode());
        Assertions.assertEquals(2, findAllProductDto.getBody().size());

        assertEqualsProperties(products.get(0), findAllProductDto.getBody().get(0));
        assertEqualsProperties(products.get(1), findAllProductDto.getBody().get(1));

        verify(productService).findAllProducts();
        verifyNoMoreInteractions(productService);

    }

    private void assertEqualsProperties(Product expected, ProductDto actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());

    }

}
