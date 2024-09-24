import model.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserTest  extends BaseTest{

    @Test
    public void updateUser(){

        User user = new User("116", "Updated", "111", "female");

        User userUpdated =  given()
                                .spec(reqSpec)
                                .pathParam("userId", "116")
                                .body(user).
                            when()
                                .put("{userId}").
                            then()
                                .spec(respSpec)
                                .extract().body().as(User.class);

        Assert.assertEquals(user, userUpdated);

    }


}
