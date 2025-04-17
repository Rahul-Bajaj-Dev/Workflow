package org.Neusort_QA;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlatfformD_QA {

    void platformD(WebDriver driver, String type, JSONObject config) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            switch (type) {
                case "CHECK_VALUE":
                    try {
                        WebElement data = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((String) config.get("xpath"))));
                        String coins = data.getText();
                        System.out.println("Value retrieved: " + coins);
                    } catch (NoSuchElementException e) {
                        System.out.println("Coin element not found: " + e.getMessage());
                        throw e;
                    }
                    break;

                case "navigate_Forms":
                    try {
                        int iterations = ((Long) config.get("Iterations")).intValue();
                        for (int i = 0; i < iterations; i++) {
                            String xpath = "//tbody[contains(@class, 'MuiTableBody-root')]//tr[@data-index='" + i + "']//td[@data-index='0']";
                            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                            element.click();
                            Thread.sleep(1000);
                            driver.navigate().back();
                        }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread was interrupted: " + e.getMessage());
                        throw e;
                    }
                    break;

                case "Selection":
                    String path = (String) config.get("xpath");
                    String sub_sub_type  = (String) config.get("sub_sub_type");
                    if (sub_sub_type.equals("Tech Stack")) {
                        System.out.println("Now Creating the Job form");  // Log to file
                    }
                    List<WebElement> skills = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(path)));
                    int minimumSelections = ((Long) config.get("Minimum_Selections")).intValue();

                    Random random = new Random();
                    Set<Integer> selectedIndices = new HashSet<>();
                    int selections = Math.min(minimumSelections, skills.size());

                    for (int i = 0; i < selections; i++) {
                        int randomIndex;
                        do {
                            randomIndex = random.nextInt(skills.size());
                        } while (selectedIndices.contains(randomIndex));
                        selectedIndices.add(randomIndex);
                        skills.get(randomIndex).click();
                        System.out.println(sub_sub_type + " Selected: " + skills.get(randomIndex).getText());  // Log to file
                    }

                    Actions actions = new Actions(driver);
                    actions.moveByOffset(80, 80).click().perform(); // Adjust offsets as needed
                    break;

                case "Skill_Panel_Selection":
                    try {
                        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
                        List<WebElement> Cskills = wait2.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@role='button' and @aria-controls='panel2-content']")));
                        Set<Integer> Selected_panel = new HashSet<>();
                        int randomskill;
                        int random_custom;
                        Random random2 = new Random();
                        for (int i = 0; i < Cskills.size(); i++) {
                            randomskill = random2.nextInt(Cskills.size());
                            if (!Selected_panel.contains(randomskill)) {
                                Selected_panel.add(randomskill);
                                Cskills.get(randomskill).click();
                                List<WebElement> Custom_Skills = wait2.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'Mui-expanded')]//*[name()='svg'][@aria-label='Add']")));
                                Set<Integer> Custom_Skill_Set = new HashSet<>();
                                int a = Math.min(4, Custom_Skills.size());

                                for (int j = 0; j < a; j++) {
                                    random_custom = random2.nextInt(Custom_Skills.size());
                                    if (!Custom_Skill_Set.contains(random_custom)) {
                                        Custom_Skill_Set.add(random_custom);
                                        WebElement customSkill = Custom_Skills.get(random_custom);
                                        wait2.until(ExpectedConditions.elementToBeClickable(customSkill)).click();
                                    }
                                }
                                Cskills.get(randomskill).click();
                                System.out.println("Skills selected form skill pannel :" + Cskills.get(randomskill).getText());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error during Skill Panel Selection: " + e.getMessage());
                        throw e;
                    }
                    break;

                default:
                    System.out.println("Invalid action specified.");  // Log to file
            }

        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error executing action: " + e.getMessage());
            throw e;
        }
    }
}
