package org.Neusort_QA;

import java.time.Duration;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Genric_QA {

    void executeAction(WebDriver driver, String type, JSONObject config) throws Exception {  // Declare that this method can throw exceptions
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            // Check subtype
            String subtype = (String) config.get("subtype");
            if (!"generic".equalsIgnoreCase(subtype)) {
                System.err.println("Skipping action: Not a generic subtype");
                return;
            }

            // Switch on the action type
            switch (type) {
                case "OPENURL":
                    String url = (String) config.get("URL");
                    driver.get(url);
                    break;

                case "WAIT":
                    Thread.sleep((long) config.get("duration"));
                    break;

                case "INPUT":
                    String xpath = (String) config.get("xpath");
                    String value = (String) config.get("value");
                    driver.findElement(By.xpath(xpath)).sendKeys(value);
                    break;

                case "CLICK":
                    driver.findElement(By.xpath((String) config.get("xpath"))).click();
                    break;

                case "INPUT_iframe":
                    WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                    driver.switchTo().frame(iframe);
                    String xpath2 = (String) config.get("xpath");
                    String value2 = (String) config.get("value");
                    driver.findElement(By.xpath(xpath2)).sendKeys(value2);
                    driver.switchTo().defaultContent();
                    break;

                case "CLICK_iframe":
                    WebElement iframe1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                    driver.switchTo().frame(iframe1);
                    driver.findElement(By.xpath((String) config.get("xpath"))).click();
                    driver.switchTo().defaultContent();
                    break;

                default:
                    System.err.println("Unsupported action type: " + type);
            }

        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
            throw e;  // Re-throw the exception to be handled in the main method
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
            throw e;  // Re-throw the exception to be handled in the main method
        } catch (Exception e) {
            System.err.println("Error executing action: " + e.getMessage());
            throw e;  // Re-throw the exception to be handled in the main method
        }
    }
}
