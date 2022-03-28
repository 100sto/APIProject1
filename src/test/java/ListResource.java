import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class ListResource {

    @Test
    public void validateListResource(){

        Response response1 = given().header("Accept", "application/json")
                .param("page",1)
                .when().get("https://reqres.in/api/unknown")
                .then().statusCode(200)
                .extract().response();
        Assert.assertNotEquals(response1.statusCode(),404);

        Response response2 = given().header("Accept", "application/json")
                .param("page",2)
                .when().get("https://reqres.in/api/unknown")
                .then().extract().response();

        Assert.assertNotEquals(response2.statusCode(),404);

        Map<String,Object> parseResponse2 = response2.as(new TypeRef<Map<String, Object>>() {
        });

        Map<String,Object> parseResponse1 = response1.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String,Object>> listData1 = (List<Map<String, Object>>) parseResponse1.get("data");

        int idSumPage1=0;
        int yearSumPage1=0;
        for (Map<String,Object> map : listData1){
            idSumPage1 += (int) map.get("id");
            yearSumPage1 += (int) map.get("year");
        }


        List<Map<String,Object>> listData2 = (List<Map<String, Object>>) parseResponse2.get("data");

        int idSumPage2=0;
        int yearSumPage2=0;
        for (Map<String,Object> map : listData2){
            idSumPage2 += (int) map.get("id");
            yearSumPage2 += (int) map.get("year");
        }

        int idSum = idSumPage1+idSumPage2;
        Assert.assertEquals(78,idSum);
        Assert.assertFalse(idSum!=78);

        int totalColors = (int) parseResponse1.get("total");

        double avgOfYears = (double)(yearSumPage1+yearSumPage2)/totalColors;

        Assert.assertEquals(2005,avgOfYears,5);

        Assert.assertFalse(avgOfYears<2005);

    }
}
