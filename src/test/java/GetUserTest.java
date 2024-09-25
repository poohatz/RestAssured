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

public class GetUserTest extends BaseTest{

    @Test
    public void getUser(){

        given()
                .pathParam("userId", "4772")
                .filter(new AllureRestAssured())
                .spec(reqSpec).
        when()
                .get("{userId}").
        then()
                .spec(respSpec)
                .assertThat().body("attributes.name", Matchers.equalTo("John"));

    }

}
