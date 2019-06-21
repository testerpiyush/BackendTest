package com.mytaxi.apitest.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import com.mytaxi.apitest.base.TestBase;
import com.mytaxi.apitest.response.comments.Comment;
import com.mytaxi.apitest.response.posts.Post;
import com.mytaxi.apitest.response.users.User;
import com.mytaxi.apitest.responseValidator.ValidateResponse;
import com.mytaxi.apitest.utility.ReportUtil;
import com.mytaxi.apitest.utility.RestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ApiWorkflow extends TestBase {
    Response response;
    List<String> postId = new ArrayList<>();
    ValidateResponse validateResponse = new ValidateResponse();
    private ObjectMapper mapper = new ObjectMapper();
    private Comment[] comments;
    private User[] user;
    private Post[] posts;

    @Test(description = "Verify Email format in comments")
    public void verifyEmailInCommentsWorkflow() {
        try {
            ReportUtil.log("Start test", "Verify comments for post of a user", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("users"));
            Assert.assertNotNull(response.body());
            ReportUtil.log("Verify User API", "Successfully fetched User API response ", "Info");
            user = mapper.readValue(response.asString(), User[].class);
            int userId = validateResponse.getUserId(user, testProps.getProperty("user"));
            Assert.assertTrue(userId > 0, "User not found");
            ReportUtil.log("Find User", "User found in Users API response ", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("posts"), "userId", Integer.toString(userId));
            Assert.assertNotNull(response.body());
            ReportUtil.log("Verify Post API", "Successfully fetched Post API response ", "Info");
            posts = mapper.readValue(response.asString(), Post[].class);
            postId = validateResponse.getPostId(posts);
            Assert.assertTrue(postId.size() > 0, "User has no posts");
            ReportUtil.log("Verify Posts ", "Successfully fetched Post API response for a User ", "Info");
            validateResponse.validateEmailInCommentsAPI(postId);
            ReportUtil.log("End test ", "Emails in comments are in proper format ", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("Verify email format", "Error in test workflow", "Fail", e);
        }
    }
}

