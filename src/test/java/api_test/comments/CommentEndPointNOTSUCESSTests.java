package api_test.comments;

import api_test.BaseTest;
import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;
import specifications.ResponseSpecsHTML;

import static io.restassured.RestAssured.given;


public class CommentEndPointNOTSUCESSTests extends BaseTest {
    private static String resourcePath = "/v1/comment";
    private static String resourcePostPath = "https://api-coffee-testing.herokuapp.com/v1/post";
    private static Integer postID = 0;

    @BeforeGroups("create_post")
    public void CreatePosts(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());


        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePostPath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        postID = jsonPathEvaluator.get("id");




    }
    @Test(groups = "create_post",priority = 0)
    public void Test_Create_Comment_wrong_AuthoritionMode(){

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testComment)
                .post(resourcePath+ "/" + postID.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


    @Test(groups = "create_comment",priority = 2)
    public void Test_Get_ONE_Comment_wrong_path(){

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateBasicAuth())

                .get(resourcePath+ "/" + postID.toString()+"/")
                .then()
                .statusCode(404)
                .spec(ResponseSpecsHTML.defaultSpec());
    }


}
