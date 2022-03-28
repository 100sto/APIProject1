import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;

public class SingleUser {

    @Test
    public void validateSingleUser(){

        //all names start with upper case, email and id is unique, emails ends with .in


        Set<Integer> allIds = new HashSet<>();
        Set<String> allEmails = new HashSet<>();

        for (int i=1; i<=12; i++){
            Response response = given().header("Accept", "application/json")
                    .when().get("https://reqres.in/api/users/"+i)
                    .then().extract().response();

            Assert.assertEquals(200,response.getStatusCode());
            Assert.assertNotEquals(response.statusCode(),404);


            Map<String,Map<String,Object>> parseResponse = response.as(new TypeRef<Map<String, Map<String, Object>>>() {
            });

            boolean firstCharFirstName = Character.isUpperCase(parseResponse.get("data").get("first_name").toString().charAt(0));
            boolean firstCharLastName = Character.isUpperCase(parseResponse.get("data").get("last_name").toString().charAt(0));

            Assert.assertTrue(firstCharFirstName);
            Assert.assertTrue(firstCharLastName);

            Assert.assertFalse(Character.isLowerCase(parseResponse.get("data").get("first_name").toString().charAt(0)));

            boolean emailEndsWithIN = parseResponse.get("data").get("email").toString().endsWith(".in");
            Assert.assertTrue(emailEndsWithIN);

            allIds.add((Integer) parseResponse.get("data").get("id"));
            allEmails.add(parseResponse.get("data").get("email").toString());
        }


        Assert.assertEquals(12,allIds.size());
        Assert.assertEquals(12,allEmails.size());

        Assert.assertFalse(allEmails.size()<12);
        Assert.assertNotEquals(0, allIds.size());

        
    }

}
