package desafio.product.ms.product;

import desafio.product.ms.rest.dto.ProductDto;

import java.math.BigDecimal;

public class ProductControllerTestsFixture {

    public static ProductDto newMockedProductDto() {

        ProductDto mockedProductDto = new ProductDto();
        mockedProductDto.setName("Name");
        mockedProductDto.setDescription("Description");
        mockedProductDto.setPrice(BigDecimal.TEN);

        return mockedProductDto;
    }
}
