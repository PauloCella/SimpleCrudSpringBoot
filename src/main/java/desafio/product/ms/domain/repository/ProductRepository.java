package desafio.product.ms.domain.repository;

import desafio.product.ms.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("Select p from Product p where" +
            " ( :name is not null and p.name like CONCAT('%',:name,'%') ) or " +
            " ( :description is not null and p.description like CONCAT('%',:description,'%') ) or " +
            " ( :minPrice is not null and :maxPrice is not null and :minPrice <= p.price  and :maxPrice >= p.price) or " +
            " ( :maxPrice is not null and :minPrice is null and :maxPrice >= p.price ) or " +
            " ( :minPrice is not null and :maxPrice is null and :minPrice <= p.price ) ")
    List<Product> searchByParams(String name,
                                 String description,
                                 BigDecimal minPrice,
                                 BigDecimal maxPrice);

}
