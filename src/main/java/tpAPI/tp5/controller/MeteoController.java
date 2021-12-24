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
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        //url de l'api Adresse permettant de récupérer les coordonnées géographiques de l'adresse saisie
        final String adresseUri = "https://api-adresse.data.gouv.fr/search/?q=" + saisie + "&limit=1";
        AddressQuery addr = restTemplate.getForObject(adresseUri, AddressQuery.class);  //faire une requête vers Adresse API
        
        String city = addr.getFeatures()[0].getProperties().getCity();          //récupérer la ville de l'adresse
        double [] coor = addr.getFeatures()[0].getGeometry().getCoordinates();  //récupérer les coordonnées géographiques de l'adresse
        model.addAttribute("city", city);


        //token d'identification de MeteoConcept api
        final String token = "17761dd05bbe22ee6f61b4b0a72c118f5df3f5bf3df409b86bb1ff2fb48afc61";
        //url de l'api météo permettant de récupérer les prévisions météo selon les coordonnées géographiques
        final String meteoUri = "https://api.meteo-concept.com/api/forecast/daily?token=" + token +
                                "&latlng=" + coor[1] + "," + coor[0];


        WeatherQuery meteo = restTemplate.getForObject(meteoUri, WeatherQuery.class);   //requête vers Meteo API

        LocalDate date = LocalDate.now();       //récupérer la date d'aujourd'hui
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");    //Permet de formatter la date sous la forme jour mois année


        //on récupère les prévisions météo de chaque jour pour les envoyer au template
        for (int i = 0; i < meteo.getForecast().length; i++) {
            int weather_code, tmin, tmax, probarain;
            
            weather_code = meteo.getForecast()[i].getWeather();     //code météo du jour (cf documentation API météo)
            tmin = meteo.getForecast()[i].getTmin();                //température minimale
            tmax = meteo.getForecast()[i].getTmax();                //température maximale
            probarain = meteo.getForecast()[i].getProbarain();      //probabilité de pluie en %

            //envoyer chaque attribut au template (le numéro _i permet de les différencier)
            model.addAttribute("weather_" + i, weather_code);
            model.addAttribute("tmin_" + i, tmin);
            model.addAttribute("tmax_" + i, tmax);
            model.addAttribute("probarain_" + i, probarain);
            model.addAttribute("date_" + i, date.plusDays(i).format(formatter));    //.format(formatter) permet d'envoyer la date au formattage défini précédement
            model.addAttribute("icon_" + i, weatherIconConverter(weather_code));

        }

        return "meteo";
    }


    /**
     *fonction permettant de lier le code météo fournit par l'api meteo avec l'icone correspondant
     * @param weatherCode
     * @return icon file name
     */
    String weatherIconConverter(int weatherCode){
        //ensoleillé
        if (weatherCode<= 3){
            return "sunny.svg";
        }
        //nuageux
        else if (weatherCode>=4 && weatherCode<=7){
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
