package org.Neusort_QA;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class Main {
    public static void main(String[] args) {
        System.out.println("Starting test...");

        // Initialize your test classes
        Genric_QA genericQA = new Genric_QA();
        PlatfformD_QA platformDQA = new PlatfformD_QA();


        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--remote-allow-origins=*");
        options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://20.40.47.123:3000,http://20.40.47.123:4000,http://20.40.47.123:7000,http://20.40.47.123:8080");
        options.addArguments("--use-fake-ui-for-media-stream");
        options.addArguments("--proxy-server=http://localhost:8082");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-features=HttpsOnlyMode");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        String result = "mergeable:true";

        try {
            System.out.println("Loading configuration file...");
            JSONParser parser = new JSONParser();
            JSONArray configurationFiles = (JSONArray) parser.parse(new FileReader("config.json"));
            System.out.println("Configuration file loaded successfully.");

            for (Object obj : configurationFiles) {
                JSONObject config = (JSONObject) obj;
                String subtype = (String) config.get("subtype");
                String type = (String) config.get("type");

                if (subtype != null) {

                    switch (subtype) {
                        case "generic":
                            genericQA.executeAction(driver, type, config);
                            break;

                        case "platform_specific":
                            platformDQA.platformD(driver, type, config);
                            break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "mergeable:false";
            sendResultToEndpoint(result);
            System.exit(1);
        }

        sendResultToEndpoint(result);

        System.out.println("Test completed.");
    }

    public static void sendResultToEndpoint(String result) {
        try {
            URL url = new URL("http://20.40.47.123:8085/result");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("status", result);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Result sent successfully to the endpoint.");
            } else {
                System.err.println("Failed to send result to the endpoint.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
