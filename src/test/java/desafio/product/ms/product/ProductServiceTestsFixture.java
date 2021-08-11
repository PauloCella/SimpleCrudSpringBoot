package desafio.product.ms.product;

import desafio.product.ms.domain.entity.Product;

import java.math.BigDecimal;

public class ProductServiceTestsFixture {

    public static Product newMockedProduct() {

        Product mockedProduct = new Product();
        mockedProduct.setName("Name");
        mockedProduct.setDescription("Description");
        mockedProduct.setPrice(BigDecimal.TEN);

        return mockedProduct;
    }
}
