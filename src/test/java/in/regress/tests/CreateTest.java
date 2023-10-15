package in.regress.tests;

import in.regress.models.CreateBodyModel;
import in.regress.models.CreateResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.regress.specs.CreateSpec.createRequestSpec;
import static in.regress.specs.CreateSpec.createResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CreateTest {

    @Test
    @DisplayName("Проверка успешного создания сотрудника с Name и Job")
    void successfulCreatePersonTest() {

        CreateBodyModel createPersonData = new CreateBodyModel();
        createPersonData.setName("morpheus");
        createPersonData.setJob("leader");

        CreateResponseModel response = step("Создание сотрудника с Именем и Должностью", () ->
                given(createRequestSpec)
                        .body(createPersonData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateResponseModel.class));

        step("Проверка ответа на запрос об успешном создании сотрудника", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Проверка успешного создания сотрудника без Name")
    void createPersonWithOutNameTest() {

        CreateBodyModel noneNameData = new CreateBodyModel();
        noneNameData.setJob("leader");

        CreateResponseModel response = step("Создание сотрудника без Имени", () ->
                given(createRequestSpec)
                        .body(noneNameData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateResponseModel.class));

        step("Проверка ответа на запрос об успешном создании сотрудника без Имени", () -> {
            assertNull(response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Проверка успешного создания сотрудника без Job")
    void createPersonWithOutJobTest() {

        CreateBodyModel noneJobData = new CreateBodyModel();
        noneJobData.setName("morpheus");

        CreateResponseModel response = step("Создание сотрудника без Работы", () ->
                given(createRequestSpec)
                        .body(noneJobData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateResponseModel.class));

        step("Проверка ответа на запрос об успешном создании сотрудника без Работы", () -> {
            assertEquals("morpheus", response.getName());
            assertNull(response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }
}
