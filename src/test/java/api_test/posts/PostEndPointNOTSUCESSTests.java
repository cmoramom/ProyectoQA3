package api_test.posts;

import api_test.BaseTest;
import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;
import specifications.ResponseSpecsHTML;

import static io.restassured.RestAssured.given;


public class PostEndPointNOTSUCESSTests extends BaseTest {
    private static String resourcePath = "https://api-coffee-testing.herokuapp.com/v1/post";
    private static Integer postID = 0;

    @BeforeGroups("create_post")
    public void CreatePosts(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());


        Response response = given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testPost)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        postID = jsonPathEvaluator.get("id");




    }
    @Test
    public void Test_Creat_Post_NOTSuccessToken(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test()
    public void Test_Search_Post_NOID(){
        String postID = "";

        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + postID.toString())
                .then()
                .statusCode(404)
                .spec(ResponseSpecsHTML.defaultSpec());

    }


}
