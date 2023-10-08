package in.regress;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteTest {
    @Test
    void successfulDeleteUserTest() {

        given()
                .log().uri()
                .log().method()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

}
