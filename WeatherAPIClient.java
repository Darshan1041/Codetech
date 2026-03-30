package Programs;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPIClient {

    public static void main(String[] args) {
        try {
            // API URL (Bangalore coordinates)
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=12.97&longitude=77.59&current_weather=true";

            // Create URL object
            URL url = new URL(apiUrl);

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Response code check
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP Error Code: " + responseCode);
            }

            // Read response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Convert response to JSON
            JSONObject json = new JSONObject(response.toString());

            // Extract weather data
            JSONObject currentWeather = json.getJSONObject("current_weather");

            double temperature = currentWeather.getDouble("temperature");
            double windspeed = currentWeather.getDouble("windspeed");

            // Display structured output
            System.out.println("===== Weather Report =====");
            System.out.println("Location: Bangalore");
            System.out.println("Temperature: " + temperature + " °C");
            System.out.println("Wind Speed: " + windspeed + " km/h");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
    
