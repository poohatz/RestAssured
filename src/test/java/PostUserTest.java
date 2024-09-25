import io.qameta.allure.restassured.AllureRestAssured;
import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostUserTest  extends BaseTest{

    @Test
    public void postUser(){

        User user = new User("118", "Janek", "54", "male");

        given()
                .spec(reqSpec)
                .filter(new AllureRestAssured())
                .body(user).
        when()
                .post().
        then()
                .assertThat().statusCode(201)
                .assertThat().body("id", Matchers.equalTo("118"))
                .assertThat().body("attributes.name", Matchers.equalTo("Janek"));

    }


}
