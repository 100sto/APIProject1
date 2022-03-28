
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class ListUsers {

    @Test
    public void validateListUsers(){

        Response response1 = given().header("Accept", "application/json")
                .param("page",1)
                .when().get("https://reqres.in/api/users")
                .then().statusCode(200)
                .extract().response();

        Response response2 = given().header("Accept", "application/json")
                .param("page",2)
                .when().get("https://reqres.in/api/users")
                .then().statusCode(200)
                .extract().response();

        Assert.assertNotEquals(404,response1.statusCode());
        Assert.assertNotEquals(404,response2.statusCode());

        Map<String,Object> parseResponse1 = response1.as(new TypeRef<Map<String, Object>>() {
        });
        Map<String,Object> parseResponse2 = response2.as(new TypeRef<Map<String, Object>>() {
        });

        Assert.assertEquals(1,parseResponse1.get("page"));
        Assert.assertEquals(2,parseResponse2.get("page"));

        Assert.assertNotEquals(0,parseResponse1.get("page"));
        Assert.assertNotEquals(3,parseResponse1.get("page"));
        Assert.assertNotEquals(0,parseResponse2.get("page"));
        Assert.assertNotEquals(3,parseResponse1.get("page"));


        List<Integer> expectedAllIds = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
        List<String> expectedAllFirstNames = Arrays.asList("George","Janet","Emma","Eve","Charles","Tracey","Michael","Lindsay","Tobias","Byron","George","Rachel");
        List<String> expectedAllLastNames = Arrays.asList("Bluth","Weaver","Wong","Holt","Morris","Ramos","Lawson","Ferguson","Funke","Fields","Edwards","Howell");
        List<String> expectedAllAvatars = new ArrayList<>();

        for (int i=1; i<=12; i++){
            expectedAllAvatars.add("https://reqres.in/img/faces/"+i+"-image.jpg");
        }

        List<Integer> actualAllIds = new ArrayList<>();
        List<String> actualAllFirstNames = new ArrayList<>();
        List<String> actualAllLastNames = new ArrayList<>();
        List<String> actualAllAvatars = new ArrayList<>();

        List<Map<String,Object>> listOfData1 = (List<Map<String, Object>>) parseResponse1.get("data");
        List<Map<String,Object>> listOfData2 = (List<Map<String, Object>>) parseResponse2.get("data");

        int numberOfEmployees =listOfData1.size()+listOfData2.size();

        for (Map<String,Object> map1 : listOfData1){
            actualAllIds.add((Integer) map1.get("id"));
            actualAllFirstNames.add((String) map1.get("first_name"));
            actualAllLastNames.add((String) map1.get("last_name"));
            actualAllAvatars.add((String) map1.get("avatar"));
        }

        for (Map<String,Object> map2 : listOfData2){
            actualAllIds.add((Integer) map2.get("id"));
            actualAllFirstNames.add((String) map2.get("first_name"));
            actualAllLastNames.add((String) map2.get("last_name"));
            actualAllAvatars.add((String) map2.get("avatar"));
        }

        Assert.assertEquals(parseResponse1.get("total"),numberOfEmployees);

        Assert.assertFalse(numberOfEmployees<12);
        Assert.assertNotEquals(0, numberOfEmployees);


        Assert.assertEquals(expectedAllIds,actualAllIds);
        Assert.assertEquals(expectedAllFirstNames,actualAllFirstNames);
        Assert.assertEquals(expectedAllLastNames,actualAllLastNames);
        Assert.assertEquals(expectedAllAvatars,actualAllAvatars);

    }


}
