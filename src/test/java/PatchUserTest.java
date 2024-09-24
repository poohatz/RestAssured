import model.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PatchUserTest  extends BaseTest{

    @Test
    public void patchUser(){

        User user = new User("Michael");

        User userUpdated =  given()
                                .spec(reqSpec)
                                .pathParam("userId", "117")
                                 .body(user).
                             when()
                                 .patch("{userId}").
                             then()
                                 .spec(respSpec)
                                  .extract().body().as(User.class);

        Assert.assertEquals(userUpdated.getAttributes().get("name"), "Michael");
        Assert.assertEquals(userUpdated.getId(), "117");

    }


}
