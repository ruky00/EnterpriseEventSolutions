package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserRestTest extends ControllerRestTest{


    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = { "Customer" })
    @DisplayName("Check that admin can delete a user")
    public void deleteUserTest(String type) throws Exception {

        // CREATE NEW USER

        ObjectNode user = objectMapper.createObjectNode()
                .put("username", "ToDeleteUser_" + type)
                .put("email", type + "_delete@urjc.es")
                .put("encodedPassword", "pass")
                .put("isEnabled",true);
        given()
                .request()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("username", equalTo(user.get("username").asText()))
                .extract().as(User.class);

        given()
            .auth()
                .basic(User.class.getName(),"pass")
                .delete("/api/users/me")
                .then()
                    .assertThat()
                          .statusCode(HttpStatus.SC_OK);

    }
}