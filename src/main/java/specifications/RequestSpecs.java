package specifications;

import helpers.DataHelper;
import helpers.RequestHelper;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification generateToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        String token = RequestHelper.getUserToken();

        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return requestSpecBuilder.build();
    };

    public static RequestSpecification generateFakeToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Beasadrer wrongtoken");
        return requestSpecBuilder.build();
    };

    public static RequestSpecification generateBasicAuth(){

        BasicAuthScheme BasicAuth = new BasicAuthScheme();

        BasicAuth.setUserName(DataHelper.getTestCommentUser().getName());
        BasicAuth.setPassword(DataHelper.getTestCommentUser().getPassword());

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAuth(BasicAuth);

        return requestSpecBuilder.build();
    }

}
