import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class GetUserTest extends BaseTest{

    @Test
    public void getUser(){

        given()
                .pathParam("userId", "42")
                .spec(reqSpec).
        when()
                .get("{userId}").
        then()
                .spec(respSpec)
                .assertThat().body("attributes.name", Matchers.equalTo("John"));



    }

}
