package assets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 Recupera datos meteorológicos de la API - esta lógica de backend
 obtendrá los datos meteorológicos más recientes de la API externa
 y los devolverá. La GUI mostrará estos datos al usuario.
 */
public class MeteoApp {
    // Recupera datos meteorológicos para la ubicación dada
    @SuppressWarnings("unchecked")
	public static JSONObject getWeatherData(String locationName){
        // Obtiene las coordenadas de la ubicación utilizando la API de geolocalización
        JSONArray locationData = getLocationData(locationName);

        // Extrae los datos de latitud y longitud
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // Construye la URL de la solicitud API con las coordenadas de la ubicación
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=America%2FLos_Angeles";

        try{
            // Llama a la API y obtiene la respuesta
            HttpURLConnection conn = fetchApiResponse(urlString);

            // Verifica el estado de la respuesta
            // 200 - significa que la conexión fue exitosa
            if(conn.getResponseCode() != 200){
                System.out.println("Error: No se pudo conectar a la API");
                return null;
            }

            // Almacena los datos JSON resultantes
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                // Lee y almacena en el StringBuilder
                resultJson.append(scanner.nextLine());
            }

            // Cierra el escáner
            scanner.close();

            // Cierra la conexión URL
            conn.disconnect();

            // Analiza nuestros datos
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            // Recupera datos horarios
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            // Queremos obtener los datos de la hora actual
            // así que necesitamos obtener el índice de nuestra hora actual
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            // Obtiene la temperatura
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            // Obtiene el código del tiempo
            JSONArray weathercode = (JSONArray) hourly.get("weathercode");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            // Obtiene la humedad
            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            // Obtiene la velocidad del viento
            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (double) windspeedData.get(index);

            // Construye el objeto JSON de datos meteorológicos que vamos a acceder en nuestro frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // Recupera coordenadas geográficas para el nombre de ubicación dado
    public static JSONArray getLocationData(String locationName){
        // Reemplaza cualquier espacio en blanco en el nombre de la ubicación por + para adherirse al formato de solicitud de la API
        locationName = locationName.replaceAll(" ", "+");

        // Construye la URL de la API con el parámetro de ubicación
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try{
            // Llama a la API y obtiene una respuesta
            HttpURLConnection conn = fetchApiResponse(urlString);

            // Verifica el estado de la respuesta
            // 200 significa conexión exitosa
            if(conn.getResponseCode() != 200){
                System.out.println("Error: No se pudo conectar a la API");
                return null;
            }else{
                // Almacena los resultados de la API
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                // Lee y almacena los datos JSON resultantes en nuestro StringBuilder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }

                // Cierra el escáner
                scanner.close();

                // Cierra la conexión URL
                conn.disconnect();

                // Analiza la cadena JSON en un objeto JSON
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                // Obtiene la lista de datos de ubicación que la API generó a partir del nombre de ubicación
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // No se pudo encontrar la ubicación
        return null;
    }

    @SuppressWarnings("deprecation")
	private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // Intenta crear la conexión
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establece el método de solicitud a GET
            conn.setRequestMethod("GET");

            // Conéctate a nuestra API
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // No se pudo hacer la conexión
        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();

        // Itera a través de la lista de tiempos y ve cuál coincide con nuestra hora actual
        for(int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                // Devuelve el índice
                return i;
            }
        }

        return 0;
    }

   public static String getCurrentTime(){
        // Obtiene la fecha y hora actual
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formatea la fecha para que sea 2023-09-02T00:00 (así es como se lee en la API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        // Formatea e imprime la fecha y hora actual
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    // Convierte el código del tiempo a su representacion real
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            // despejado
            weatherCondition = "Despejado";
        }else if(weathercode > 0L && weathercode <= 3L){
            // nublado
            weatherCondition = "Nublado";
        }else if((weathercode >= 51L && weathercode <= 67L)
                    || (weathercode >= 80L && weathercode <= 99L)){
            // lluvia
            weatherCondition = "Lluvia";
        }else if(weathercode >= 71L && weathercode <= 77L){
            // nieve
            weatherCondition = "Nieve";
        }

        return weatherCondition;
    }
}
