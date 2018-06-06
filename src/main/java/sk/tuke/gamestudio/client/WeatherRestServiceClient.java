package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.weather.CurrentWeather;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WeatherRestServiceClient {

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=432bc534c2b452b7690e5394a72d2e4a";

    public CurrentWeather getWeather() {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<CurrentWeather>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading weather", e);
        }
    }


    public void printWeather(CurrentWeather currentWeather) {
        System.out.println("aktualne pocasie v meste " + currentWeather.getName() + ":");
        System.out.println("teplota: " + currentWeather.getMain().getTemp() + " stupnov" );
        System.out.println("tlak: " + currentWeather.getMain().getPressure() + " paskalov");
    }
}

