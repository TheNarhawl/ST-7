package ru.unn.st7;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task3 {
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";

    public static void saveForecast(WebDriver webDriver) throws Exception {
        webDriver.get(FORECAST_URL);
        WebElement elem = webDriver.findElement(By.tagName("pre"));
        String jsonStr = elem.getText();

        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(jsonStr);
        JSONObject hourly = (JSONObject) root.get("hourly");
        JSONArray time = (JSONArray) hourly.get("time");
        JSONArray temperature = (JSONArray) hourly.get("temperature_2m");
        JSONArray rain = (JSONArray) hourly.get("rain");

        StringBuilder sb = new StringBuilder();
        sb.append("|№   |  Дата/время   | Температура | Осадки (мм)  |").append(System.lineSeparator());
        sb.append("| -- | ------------- | ----------- | ------------ |").append(System.lineSeparator());

        int rows = Math.min(time.size(), Math.min(temperature.size(), rain.size()));
        for (int i = 0; i < rows; i++) {
            sb.append("|")
                    .append(i + 1)
                    .append(" | ")
                    .append(String.valueOf(time.get(i)))
                    .append(" | ")
                    .append(String.valueOf(temperature.get(i)))
                    .append(" | ")
                    .append(String.valueOf(rain.get(i)))
                    .append(" |")
                    .append(System.lineSeparator());
        }

        System.out.print(sb);

        Path out = Path.of("result", "forecast.txt");
        Files.createDirectories(out.getParent());
        Files.writeString(out, sb.toString(), StandardCharsets.UTF_8);
    }
}

