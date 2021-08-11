package desafio.product.ms.rest.converter;

import desafio.product.ms.domain.entity.Product;
import desafio.product.ms.rest.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class ProductMapper {

    public abstract ProductDto createProductDto(Product entity);

    public abstract Product createProduct(ProductDto productDto);

    public abstract Product updateProduct(ProductDto productDto, @MappingTarget Product product);

    public abstract ProductDto updateDto(Product product, @MappingTarget ProductDto productDto);
}
