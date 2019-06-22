package com.mytaxi.apitest.responseValidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import com.mytaxi.apitest.response.comments.Comment;
import com.mytaxi.apitest.response.posts.Post;
import com.mytaxi.apitest.response.users.User;
import com.mytaxi.apitest.utility.CustomExceptions;
import com.mytaxi.apitest.utility.ReportUtil;
import com.mytaxi.apitest.utility.RestUtil;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mytaxi.apitest.base.TestBase.testProps;

public class ValidateResponse {
    Response response;
    private int userId;
    private Comment[] comments;
    private ObjectMapper mapper = new ObjectMapper();


    public void validateEmail(Comment[] comment) {
        try {
            ReportUtil.log("Verify email format ", "email format verification starts ", "Info");
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            List<String> emailList = new ArrayList<>();
            for (Comment com : comment) {
                emailList.add(com.getEmail());
            }
            for (int email = 0; email < emailList.size(); email++) {
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertFalse(emailList.get(email).isEmpty(), "Email " + emailList.get(email) + "is not Valid");
                softAssert.assertTrue(pat.matcher(emailList.get(email)).matches(), "Email is valid " + emailList.get(email));
                softAssert.assertAll();
            }
            ReportUtil.log("Verify email format for : " + emailList.toString(), "Emails are in proper format ", "Info");

        } catch (AssertionError error) {
            ReportUtil.log("Verify email ", "Email format is wrong: ", "fail", error);
            error.printStackTrace();
        }
    }

    public int getUserId(User[] user, String username) throws CustomExceptions {
        for (User ite : user) {
            if (ite.getUsername().equalsIgnoreCase(username)) {
                userId = ite.getId();
                break;
            } else {
                userId = 0;
            }
        }
        return userId;
    }

    public List<String> getPostId(Post[] post) {
        List<String> postids = new ArrayList<>();
        try {
            for (Post id : post) {
                postids.add(id.getId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postids;
    }


    public void validateEmailInCommentsAPI(List<String> postId) {
        try {
            for (String s : postId) {
                response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("comments"), "postId", s);
                Assert.assertNotNull(response.body(), "Comment API response is Null");
                ReportUtil.log("Fetch Comments for postId: " + s, "Successfully fetched comments API response for a post ", "Info");
                comments = mapper.readValue(response.asString(), Comment[].class);
                validateEmail(comments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
