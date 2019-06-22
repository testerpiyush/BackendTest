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
            Assert.assertNotNull(response.body(), "User API response is Null");
            ReportUtil.log("Verify User API", "Successfully fetched User API response ", "Info");
            user = mapper.readValue(response.asString(), User[].class);
            int userId = validateResponse.getUserId(user, testProps.getProperty("user"));
            Assert.assertTrue(userId > 0, "User not found");
            ReportUtil.log("Find User", "User found in Users API response ", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("posts"), "userId", Integer.toString(userId));
            Assert.assertNotNull(response.body(), "Post API response is Null");
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

    @Test(description = "Verify user API response")
    public void verifyUserResponseNotNull() {
        try {
            ReportUtil.log("Start test", "Verify User API response", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("users"));
            Assert.assertNotNull(response.body(), "User API response is Null");
            ReportUtil.log("Verify User API", "Successfully fetched User API response ", "Info");
            user = mapper.readValue(response.asString(), User[].class);
            for (User ite : user) {
                Assert.assertNotNull(ite.getUsername(), "Username is Null for: " + ite);
                Assert.assertNotNull(ite.getName(), "Name is Null: " + ite);
                Assert.assertNotNull(ite.getEmail(), "Emailis Null: " + ite);
            }
            ReportUtil.log("Verify User API response", "Username, Name are not null ", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("Error in User API response", "Username, Name are Null", "Fail", e);
        }
    }

    @Test(description = "Verify Posts API response")
    public void verifyPostResponseNotNull() {
        try {
            ReportUtil.log("Start test", "Verify Post API response", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("posts"));
            Assert.assertNotNull(response.body());
            posts = mapper.readValue(response.asString(), Post[].class);
            for (Post ite : posts) {
                Assert.assertNotNull(ite.getUserId(), "UserId is Null for post : " + ite);
                Assert.assertNotNull(ite.getBody(), "Body is Null for post : " + ite);
                Assert.assertNotNull(ite.getTitle(), "Title is Null for post : " + ite);
            }
            ReportUtil.log("Verify Post API response", "UserId, Body, Title are not Null", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("Error in Post API response", "UserId, Body, Title are Null", "Fail", e);
        }
    }

    @Test(description = "Verify Comment API response")
    public void verifyCommentsNotNull() {
        try {
            ReportUtil.log("Start test", "Verify Comments API response", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("comments"));
            Assert.assertNotNull(response.body());
            comments = mapper.readValue(response.asString(), Comment[].class);
            for (Comment ite : comments) {
                Assert.assertNotNull(ite.getPostId(), "Postid is Null for comment : " + ite);
                Assert.assertNotNull(ite.getEmail(), "Email is Null for comment : " + ite);
                Assert.assertNotNull(ite.getBody(), "Body is Null for comment : " + ite);
                Assert.assertNotNull(ite.getName(), "Name is Null for comment : " + ite);
            }
            ReportUtil.log("Verify Comment API response", "PostId, Email ,Body, Name are not Null", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("Error in Comment API response", "UserId, Body, Title are Null", "Fail", e);

        }
    }

    @Test(description = "Verify specific userId in post API ")
    public void verifyUserIdPost() {
        try {
            ReportUtil.log("Start test", "Verify userId in post of a user", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("users"));
            Assert.assertNotNull(response.body(), "User API response is Null");
            ReportUtil.log("Verify User API", "Successfully fetched User API response ", "Info");
            user = mapper.readValue(response.asString(), User[].class);
            int userId = validateResponse.getUserId(user, testProps.getProperty("user"));
            Assert.assertTrue(userId > 0, "User not found");
            ReportUtil.log("Find User", "User found in Users API response ", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("posts"), "userId", Integer.toString(userId));
            Assert.assertNotNull(response.body(), "Post API response is Null");
            ReportUtil.log("Verify Post API", "Successfully fetched Post API response ", "Info");
            posts = mapper.readValue(response.asString(), Post[].class);
            for (Post ite : posts) {
                Assert.assertEquals(ite.getUserId().toString(), Integer.toString(userId), "UserId is not matched : " + ite.toString());
            }
            ReportUtil.log("Verify UserId in post", "UserId is matched in posts", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("Error in post API response", "UserId didn't matched", "Fail", e);
        }
    }

    @Test(description = "Verify anonymous user posts ")
    public void verifyAnonymousUserPost() {
        try {
            ReportUtil.log("Start test", "Verify anonymous user posts", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("users"));
            Assert.assertNotNull(response.body(), "User API response is Null");
            ReportUtil.log("Verify User API", "Successfully fetched User API response ", "Info");
            user = mapper.readValue(response.asString(), User[].class);
            int userId = validateResponse.getUserId(user, testProps.getProperty("user"));
            Assert.assertTrue(userId > 0, "User not found");
            ReportUtil.log("Find User", "User found in Users API response ", "Info");
            response = RestUtil.callGet(testProps.getProperty("baseurl"), testProps.getProperty("posts"));
            Assert.assertNotNull(response.body(), "Post API response is Null");
            ReportUtil.log("Verify Post API", "Successfully fetched Post API response ", "Info");
            posts = mapper.readValue(response.asString(), Post[].class);
            List<String> allUserId = validateResponse.getAllUserId(user);
            if (validateResponse.verifyPosts(posts, allUserId)) {
                Assert.fail("Anonymous user found");
            }
            ReportUtil.log("find anonymous user posts", "anonymous user posts not found", "Pass");
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.log("find anonymous user posts", "anonymous user posts not found", "Fail");
        }
    }

}

