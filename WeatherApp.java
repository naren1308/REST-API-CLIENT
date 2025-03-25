import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "YOUR_API_KEY"; // 🔴 Replace with your OpenWeather API Key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static void main(String[] args) {
        String city = "Vellore"; // Change the city as needed
        fetchWeatherData(city);
    }

    public static void fetchWeatherData(String city) {
        try {
            // Construct API URL
            String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Check HTTP Response Code
            if (conn.getResponseCode() != 200) {
                System.out.println("❌ Error: Failed to fetch data. HTTP Code: " + conn.getResponseCode());
                return;
            }

            // Read API Response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Parse and Display Weather Data
            parseAndDisplayWeather(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseAndDisplayWeather(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String cityName = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");

        // Display Output
        System.out.println("\n🌤️ ======= Weather Information =======");
        System.out.println("🏙️ City: " + cityName);
        System.out.println("🌡️ Temperature: " + temperature + "°C");
        System.out.println("💧 Humidity: " + humidity + "%");
        System.out.println("⛅ Condition: " + description);
        System.out.println("===================================\n");
    }
}