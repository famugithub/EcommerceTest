package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvoiceApiTest {
    // Base URL (mock server adresinle değiştir)
    String baseUrl = "https://f9b9ffcb-f72d-4696-9adf-6c1319041437.mock.pstmn.io";

    @Test
    public void testApiFlow() throws IOException {
        String token = getToken("testuser", "testpass");
        JSONObject viewResponse = viewInvoice("123456");
        saveToFile("viewInvoiceResponse.json", viewResponse.toString(4));
        JSONObject sendResponse = sendInvoice("123456", token);
        saveToFile("sendInvoiceResponse.json", sendResponse.toString(4));
        assertTrue(viewResponse.getJSONObject("Result").getBoolean("success"));
        assertTrue(sendResponse.getJSONObject("Result").getBoolean("success"));
    }
    public String getToken(String user, String pass) {
        Response response = given()
                .header("user", user)
                .header("pass", pass)
                .post(baseUrl + "/token");
        return response.jsonPath().getString("token");
    }
    public JSONObject viewInvoice(String barcode) {
        Response response = given()
                .get(baseUrl + "/viewInvoice?barcode=" + barcode);
        return new JSONObject(response.getBody().asString());
    }
    public JSONObject sendInvoice(String barcode, String token) {
        JSONObject body = new JSONObject();
        body.put("Barcode", barcode);
        Response response = given()
                .header("token", token)
                .body(body.toString())
                .header("Content-Type", "application/json")
                .post(baseUrl + "/sendInvoice");
        return new JSONObject(response.getBody().asString());
    }
    public void saveToFile(String filename, String content) throws IOException {
        FileWriter file = new FileWriter(filename);
        file.write(content);
        file.close();
    }
}
