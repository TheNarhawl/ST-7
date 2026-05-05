package ru.unn.st7;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static String getIp(WebDriver webDriver) throws Exception {
        webDriver.get("https://api.ipify.org/?format=json");
        WebElement elem = webDriver.findElement(By.tagName("pre"));
        String jsonStr = elem.getText();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        Object ip = obj.get("ip");
        return ip == null ? "" : ip.toString();
    }
}

