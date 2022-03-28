import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class SingleResource {

    @Test
    public void validateSingleResource (){

        Set<Integer> ids = new LinkedHashSet<>();
        List<Integer> years = new ArrayList<>();
        Set<String> colors = new LinkedHashSet<>();
        List<String> supportTexts = new ArrayList<>();

        for (int i=1; i<=12;i++) {
            Response response = given().header("Accept", "application/json")
                    .when().get("https://reqres.in/api/unknown/"+i)
                    .then().statusCode(200)
                    .extract().response();

            Assert.assertNotEquals(response.statusCode(),201);


            Map<String, Map<String, Object>> parseResponse = response.as(new TypeRef<Map<String, Map<String, Object>>>() {
            });

            int id = (int) parseResponse.get("data").get("id");
            int year = (int) parseResponse.get("data").get("year");
            String color = (String) parseResponse.get("data").get("color");
            String supportText = (String) parseResponse.get("support").get("text");

            ids.add(id);
            years.add(year);
            colors.add(color);
            supportTexts.add(supportText);

        }

        Set<Integer> expectedIds = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        List<Integer> expectedYears = Arrays.asList(2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011);

        Set<String> expectedColors = new LinkedHashSet<>(Arrays.asList("#98B2D1", "#C74375", "#BF1932", "#7BC4C4", "#E2583E",
                                                "#53B0AE", "#DECDBE", "#9B1B30", "#5A5B9F", "#F0C05A", "#45B5AA", "#D94F70"));

        List<String> expectedSupportTexts = new ArrayList<>();
        for (int i=0; i<12;i++){
            expectedSupportTexts.add("To keep ReqRes free, contributions towards server costs are appreciated!");
        }


        Assert.assertEquals(expectedIds,ids);
        Assert.assertEquals(expectedYears,years);
        Assert.assertEquals(expectedColors,colors);
        Assert.assertEquals(expectedSupportTexts,supportTexts);


//        System.out.println(expectedIds);
//        System.out.println(expectedYears);
//        System.out.println(expectedColors);
//        System.out.println(expectedSupportTexts);


    }
}
