import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostUserTest  extends BaseTest{

    @Test
    public void postUser(){

        User user = new User("116", "Kowal", "54", "male");

        given()
                .spec(reqSpec)
                .body(user).
        when()
                .post().
        then()
                .assertThat().statusCode(201)
                .assertThat().body("id", Matchers.equalTo("116"))
                .assertThat().body("attributes.name", Matchers.equalTo("Kowal"));

    }


}
