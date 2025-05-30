package stepDefinitions.apiStepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiStepDefs1 {

    private String baseUrl;
    private Response response;
    private String token;

    @Given("API base URL {string}")
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    @When("user sends POST request to {string} with user {string} and pass {string}")
    public void postToken(String endpoint, String user, String pass) {
        response = RestAssured
                .given()
                .header("user", user)
                .header("pass", pass)
                .post(baseUrl + endpoint);
        token = response.jsonPath().getString("token");
    }

    @Then("response status code should be {int}")
    public void checkStatus(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }

    @Then("token should be present in response")
    public void tokenPresent() {
        assertTrue(token != null && !token.isEmpty());
    }

    @When("user sends GET request to {string}")
    public void getRequest(String endpoint) {
        response = RestAssured
                .given()
                .get(baseUrl + endpoint);
    }

    @Then("response should contain {string}")
    public void responseContains(String expected) {
        assertTrue(response.getBody().asString().contains(expected));
    }

    @Given("token {string} is used")
    public void tokenIsUsed(String givenToken) {
        this.token = givenToken;
    }

    @When("user sends POST request to {string} with body:")
    public void postWithBody(String endpoint, String body) {
        response = RestAssured
                .given()
                .header("token", token)
                .header("Content-Type", "application/json")
                .body(body)
                .post(baseUrl + endpoint);
    }
}