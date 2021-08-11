package desafio.product.ms;


import desafio.product.ms.base.BaseTestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


class DemoApplicationTests extends BaseTestController {

    @Test
    void checkingUpServer() {

        ResponseEntity<?> responseEntity = createRestTemplate().exchange(PRODUCT_URL, HttpMethod.GET, new HttpEntity<>(defaultHeaders()), Object.class);

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
