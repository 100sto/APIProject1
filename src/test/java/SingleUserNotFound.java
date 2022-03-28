import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
public class SingleUserNotFound {

    @Test
    public void validateSingleUserNotFound(){
        Response response = given().header("Accept", "application/json")
                .when().get("https://reqres.in/api/user/23")
                .then().extract().response();

        Assert.assertEquals(404, response.getStatusCode());
        Assert.assertNotEquals(200,response.getStatusCode());

        Map<String,Object> parseResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        Assert.assertEquals(0,parseResponse.size());
        Assert.assertFalse(parseResponse.size()>0);

    }

}
