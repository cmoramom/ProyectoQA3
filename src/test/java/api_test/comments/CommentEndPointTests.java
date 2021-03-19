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

import static io.restassured.RestAssured.given;


public class CommentEndPointTests extends BaseTest {
    private static String resourcePath = "/v1/comment";
    private static String resourcePostPath = "https://api-coffee-testing.herokuapp.com/v1/post";
    private static Integer postID = 0;
    private static Integer commentID = 0;

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
    @BeforeGroups("create_comment")
    public void CreateComment(){

        CreatePosts();
        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());



        Response response = given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(testComment)
                .post(resourcePath+ "/" + postID.toString());



        JsonPath jsonPathEvaluator = response.jsonPath();
        commentID = jsonPathEvaluator.get("id");
    }



    @Test(groups = "create_post",priority = 0)
    public void Test_Create_Comment(){

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(testComment)
                .post(resourcePath+ "/" + postID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post",priority = 1)
    public void Test_Get_All_comments(){

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(testComment)
                .get(resourcePath+"s"+ "/" + postID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_comment",priority = 2)
    public void Test_Get_ONE_Comment(){

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(testComment)
                .get(resourcePath+ "/" + postID.toString()+"/" + commentID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_comment",priority = 3)
    public void Test_Update_Comment() {

        Comment testComment = new Comment(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(testComment)
                .put(resourcePath + "/" + postID.toString() + "/" + commentID.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());


    }



    }
