import io.qameta.allure.restassured.AllureRestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static javax.management.Query.and;

public class GetAllUsersTest extends BaseTest{

    @Test
    public void getAllUsers(){

        given()
                .spec(reqSpec)
                .filter(new AllureRestAssured()).
        when()
                .get().
        then()
                .spec(respSpec).
        and()
                .assertThat().body("id[0]", Matchers.equalTo("42"))
                .assertThat().body("attributes[1].name", Matchers.equalTo("Krzychu"));
    }

}
