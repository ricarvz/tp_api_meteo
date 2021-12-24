package tpAPI.tp5.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import tpAPI.tp5.adresse_api_objects.AddressQuery;
import tpAPI.tp5.meteo_api_object.WeatherData;
import tpAPI.tp5.meteo_api_object.WeatherQuery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class MeteoController {

    @PostMapping("/meteo")
    public String meteo(@RequestBody String saisie, Model model){
        //model.addAttribute("userAddress", saisie);

        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        final String adresseUri = new String("https://api-adresse.data.gouv.fr/search/?q="+ saisie + "&limit=1");
        AddressQuery addr = restTemplate.getForObject(adresseUri, AddressQuery.class);

        String street = addr.getFeatures()[0].getProperties().getStreet();
        String city = addr.getFeatures()[0].getProperties().getCity();
        double [] coor = addr.getFeatures()[0].getGeometry().getCoordinates();

        model.addAttribute("street", street);
        model.addAttribute("city", city);

        model.addAttribute("lat", coor[0]);
        model.addAttribute("long", coor[1]);



        //---------------------------------------------------
        //token d'identification de MeteoConcept api
        final String token = new String("17761dd05bbe22ee6f61b4b0a72c118f5df3f5bf3df409b86bb1ff2fb48afc61");
        final String meteoUri = new String("https://api.meteo-concept.com/api/forecast/daily?token=") + token+
                                "&latlng=" + coor[1] + "," + coor[0];


        WeatherQuery meteo = restTemplate.getForObject(meteoUri, WeatherQuery.class);

        LocalDate date = LocalDate.now();       //récupérer la date d'aujourd'hui
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");    //Permet de formatter la date en jour/mois/année



        for (int i = 0; i < meteo.getForecast().length; i++) {
            int weather, tmin, tmax, probarain;

//            day = meteo.getForecast()[i].getDay();
            weather = meteo.getForecast()[i].getWeather();
            tmin = meteo.getForecast()[i].getTmin();
            tmax = meteo.getForecast()[i].getTmax();
            probarain = meteo.getForecast()[i].getProbarain();

            model.addAttribute("weather_" + i, weather);
            model.addAttribute("tmin_" + i, tmin);
            model.addAttribute("tmax_" + i, tmax);
            model.addAttribute("probarain_" + i, probarain);
            model.addAttribute("date_" + i, date.plusDays(i).format(formatter));    //.format(formatter) permet d'envoyer la date au formattage défini précédement
            model.addAttribute("icon_" + i, weatherIconConverter(weather));

        }

        return "meteo";
    }


    //fonction permettant de lier le code météo fournit par l'api meteo avec l'icone correspondant
    String weatherIconConverter(int weatherCode){
        //ensoleillé
        if (weatherCode<= 2 ){
            return "sunny.svg";
        }
        //nuageux
        else if (weatherCode>=3 && weatherCode<=7){
            return "cloudy.svg";
        }
        //pluie
        else if ((weatherCode>=10 && weatherCode<=16) || (weatherCode>=40 && weatherCode<=48) || (weatherCode>=210 && weatherCode<=212) || weatherCode==235){
            return "rainy.svg";
        }
        //neige
        else if ((weatherCode>=20 && weatherCode<=32) || (weatherCode>=60 && weatherCode<=78) || (weatherCode>=220 && weatherCode<=232)){
            return "snowy.svg";
        }
        //orage
        else if (weatherCode>=100 && weatherCode<=142){
            return "thunder.svg";
        }
        else return "";
    }
}
