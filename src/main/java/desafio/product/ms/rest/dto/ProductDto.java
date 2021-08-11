package desafio.product.ms.rest.dto;


import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {

    private UUID id;

    @NotNull(message = "name is mandatory")
    private String name;

    @NotNull(message = "description is mandatory")
    private String description;

    @NotNull(message = "price is mandatory")
    @DecimalMin(value = "0.00")
    private BigDecimal price;

}
