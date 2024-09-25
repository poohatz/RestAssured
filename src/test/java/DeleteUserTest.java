import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class DeleteUserTest extends BaseTest{

    @Test
    public void deleteUser(){

        given()
                .pathParam("userId", "116")
                .filter(new AllureRestAssured())
                .spec(reqSpec).
        when()
                .delete("{userId}").
        then()
                .spec(respSpec);

    }

}
