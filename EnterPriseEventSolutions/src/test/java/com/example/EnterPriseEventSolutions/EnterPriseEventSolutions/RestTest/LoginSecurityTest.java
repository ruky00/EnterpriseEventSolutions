package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;


import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginSecurityTest extends ControllerRestTest {

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"Client" })
    @DisplayName("Check that a non-register user can create an Organizer/Client User")
    public void createUserTest(String type) throws Exception {
        // CREATE NEW USER



        ObjectNode user = objectMapper.createObjectNode()
                .put("username", "NewUser_"+ type)
                .put("email", type+"@urjc.es")
                .put("encodedPassword", "pass")
                .put("role",type.toUpperCase(Locale.ROOT));
        given()
                .request()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/")
                .then()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .body("username", equalTo(user.get("username").asText()));

        // CHECK THAT NEW USER EXIST (AS ME)

       /* given()
                .auth()
                .basic(user.get("name").asText(), user.get("password").asText())
                .when()
                .get("/api/users/"+type.toLowerCase()+"s/me")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(user.get("name").asText()));*/

    }
}
