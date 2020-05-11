package com.abhinav.newsfeed;

public class ResourceHelper {

    static String NEWS_API_KEY = "ed99e8bfdeb44fc381142c485ef29a88" ;
    static String WEATHER_API_KEY = "4152fea3a1774f444bfb997c1464e1bd";

    static String NEWS_URL = "https://newsapi.org/v2/top-headlines?";
    static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=" ;

    static class Country{

        static String INDIA = "in";
        static String US = "us";
        static String UK = "gb";
        static String AUSTRALIA = "au";
        static String JAPAN = "jp";

    }

    static class Category{

        static String SCIENCE = "science";
        static String BUSINESS = "business";
        static String ENTERTAINMENT= "entertainment";
        static String HEALTH = "health";
        static String SPORTS = "sports";
        static String TECHNOLOGY = "technology";

    }

}
