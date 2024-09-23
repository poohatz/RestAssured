import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

@Test
public class RestAssuredTests {


    @Test
    public void getAllData(){

        when().
                get("http://localhost:3000/data").
        then().
                log().all().
        and().
                assertThat().statusCode(200);

    }

    @Test
    public void getAllIncludedThatMatchToSpecifiedQueryParams(){


        Map<String, Object> params = new HashMap<>();
        params.put("type", "people");
        params.put("attributes.name", "John");
        params.put("id", 42);

       given().
               queryParams(params).
        when().
                get("http://localhost:3000/included/").
        then().
               log().all().
               assertThat().body("attributes.name[0]", Matchers.equalTo("John")).
        and().
               body("id[0]", Matchers.equalTo("42"));


    }

    @Test
    public void addNewManFromJsonAndDeleteIt(){

        String manJson = "      {\n" +
                "         \"type\":\"people\",\n" +
                "           \"id\":\"55\",\n" +
                "         \"attributes\":{\n" +
                "            \"name\":\"Kacper\",\n" +
                "            \"age\":41,\n" +
                "            \"gender\":\"male\"\n" +
                "         }\n" +
                "      }";

        given().
                contentType(ContentType.JSON).body(manJson).
        when().
                post("http://localhost:3000/included").
        then().
                log().all().assertThat().body("attributes.name", Matchers.equalTo("Kacper"));

        //cleaning data
        RestAssured.delete("http://localhost:3000/included/55");

    }

    @Test
    public void addNewManFromMapAndDeleteIt(){

        Map<String, Object> newMan = new HashMap<>();
        newMan.put("type", "people");
        newMan.put("id", 123);
            Map<String, String> manData = new HashMap<>();
            manData.put("name", "Mariusz");
            manData.put("age", "41");
            manData.put("gender", "male");
        newMan.put("attributes", manData);


        given().
                contentType(ContentType.JSON).body(newMan).
        when().
                post("http://localhost:3000/included").
        then().
                log().all().and().assertThat().body("id", Matchers.equalTo(123));

        //cleaning data
        RestAssured.delete("http://localhost:3000/included/123");

    }

    @Test
    public void addNewManFromObjectAndDeleteIt(){

        User newUser = new User(2222, "Pablo", "212", "male");


        given().
                contentType(ContentType.JSON).body(newUser).
        when().
                post("http://localhost:3000/included").
        then().
                log().all();

        User newUserCreated = RestAssured.get("http://localhost:3000/included/2222").body().as(User.class);

        Assert.assertEquals(newUserCreated.getId(), 2222);
        Assert.assertEquals(newUser, newUserCreated);

        //cleaning data
        RestAssured.delete("http://localhost:3000/included/2222");


    }

    @Test
    public void updateExistingManAndReverseOperation() throws ClassNotFoundException {

        User newUser = RestAssured.get("http://localhost:3000/included/42").body().as(User.class);

        given().
                contentType(ContentType.JSON).body(newUser).
        when().
                put("http://localhost:3000/included/49").
        then().
                log().all();

        User newUserUpdated = RestAssured.get("http://localhost:3000/included/49").body().as(User.class);
        HashMap attributesUpdated = newUserUpdated.getAttributes();
        String nameUpdated = attributesUpdated.get("name").toString();

        Assert.assertEquals(nameUpdated, "John");

        //reversing update
        User reverseUpdateUser = new User(49, "Krzychu", "122", "male");

        given().
                contentType(ContentType.JSON).body(reverseUpdateUser).
        when().
                put("http://localhost:3000/included/49").
        then().
                log().all().
        and().
                assertThat().body("attributes.name", Matchers.equalTo("Krzychu"));

    }

    @Test
    public void updateOnlyNameInGivenPost(){

        String updatingOnlyNameJson = "{\"attributes\": {\"name\": \"Jacek\"}}";

        given().
                contentType(ContentType.JSON).body(updatingOnlyNameJson).
        when().
                patch("http://localhost:3000/included/49").
        then().
                log().all().
        and().
                assertThat().body("attributes.name", Matchers.equalTo("Jacek"));

        //reversing update
        User reverseUpdateUser = new User(49, "Krzychu", "122", "male");

        given().
                contentType(ContentType.JSON).body(reverseUpdateUser).
                when().
                put("http://localhost:3000/included/49").
                then().
                log().all().
                and().
                assertThat().body("attributes.name", Matchers.equalTo("Krzychu"));

    }

    @Test
    public void findSpecificData(){

        Response response = get("http://localhost:3000/included");
        JsonPath jsonPath = new JsonPath(response.asString());

        //verifying if there is name 'Krzychu' in response
        List<String> imiona = new ArrayList<>();
        imiona.addAll(response.path("attributes.name"));
        String imie = "";
        for(int i = 0; i < imiona.size(); i++){
            if(Objects.equals(imiona.get(i), "Krzychu")){
                imie = "Krzychu";
            }
        }

        Assert.assertEquals(imie, "Krzychu");

        //veryfying if for id 42 name is 'John'
        List<Map<String, Object>> people = new ArrayList<>();
        people = response.path("attributes");
        String john = response.path("find {it.id=='42'}.attributes.name");

        Assert.assertEquals(john, "John");


        //jsonPath - finding name where id is 49
        String nameToCompare = jsonPath.get("find {it.id=='49'}.attributes.name").toString();
        Assert.assertEquals(nameToCompare, "Krzychu");

        //jsonPath - finding name where id is bigger than 43
        List<String> names = jsonPath.getList("findAll {it.id>'43'}.attributes.name");
        List<String> namesTested = new ArrayList<>();
        namesTested.add("Krzychu");
        namesTested.add("Anna");
        Assert.assertEquals(names, namesTested);

        //jsonPath - finding attributes for man with id 42
        Map<String, String> attributes = jsonPath.getMap("find {it.id=='42'}.attributes");
        List<String> attributesToTest = new ArrayList<>();
        attributesToTest.add(attributes.get("name"));
        attributesToTest.add(attributes.get("age"));
        attributesToTest.add(attributes.get("gender"));
        Assert.assertEquals(attributesToTest.get(0), "John");

    }

}
