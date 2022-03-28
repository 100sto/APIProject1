import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
public class SingleResourceNotFound {
    @Test
    public void validateSingleResourceNotFound(){
        Response response = given().header("Accept", "application/json")
                .when().get("https://reqres.in/api/unknown/23")
                .then().statusCode(404)
                .extract().response();
        Map<String,Object> parseResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        Assert.assertNotEquals(200,response.getStatusCode());

        Assert.assertEquals(0,parseResponse.size());
        Assert.assertFalse(parseResponse.size()>0);

    }


}
