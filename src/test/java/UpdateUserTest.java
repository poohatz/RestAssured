import io.qameta.allure.restassured.AllureRestAssured;
import model.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserTest  extends BaseTest{

    @Test
    public void updateUser(){

        User user = new User("117", "Updated", "111", "female");

        User userUpdated =  given()
                                .spec(reqSpec)
                                .filter(new AllureRestAssured())
                                .pathParam("userId", "117")
                                .body(user).
                            when()
                                .put("{userId}").
                            then()
                                .spec(respSpec)
                                .extract().body().as(User.class);

        Assert.assertEquals(user, userUpdated);

    }


}
