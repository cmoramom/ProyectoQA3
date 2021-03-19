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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class PostEndPointTests extends BaseTest {
    private static String resourcePath = "/v1/post";
    private static Integer postID = 0;

    @BeforeGroups("create_post")
    public void CreatePosts(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());


        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        postID = jsonPathEvaluator.get("id");




    }
    @Test
    public void Test_Create_Post_Success(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test
    public void Test_Get_all_Post(){
        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath+"s")
                .then()
                .statusCode(200);

    }

    @Test(groups = "create_post")
    public void Test_Search_Post_ID(){

        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "/" + postID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());

    }

    @Test(groups = "create_post")
    public void Test_Modify_Post_by_ID(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());


        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .put(resourcePath + "/" + postID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());

    }

    @Test(groups = "create_post")
    public void Test_Delete_Post_by_ID(){




        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + postID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());

    }


}
