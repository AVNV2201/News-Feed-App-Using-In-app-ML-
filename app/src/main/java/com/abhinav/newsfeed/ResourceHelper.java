package com.abhinav.newsfeed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    static ArrayList<String> countryNameList = new ArrayList<>(
            Arrays.asList(
                    "INDIA",
                    "UNITED STATES",
                    "UNITED KINGDOM",
                    "AUSTRALIA",
                    "JAPAN"
            )
    );

    static ArrayList<String> categoryNameList = new ArrayList<>(
            Arrays.asList(
                    "HEALTH",
                    "BUSINESS",
                    "TECHNOLOGY",
                    "ENTERTAINMENT",
                    "SCIENCE",
                    "SPORTS"
            )
    );

    static ArrayList<Integer> categoryImageList = new ArrayList<>(
            Arrays.asList(
                    R.drawable.cat_health,
                    R.drawable.cat_business,
                    R.drawable.cat_technology,
                    R.drawable.cat_entertainment,
                    R.drawable.cat_science,
                    R.drawable.cat_sports
            )
    );

    static ArrayList<Integer> countryFlagsImageList = new ArrayList<>(
            Arrays.asList(
                    R.drawable.c_in,
                    R.drawable.c_us,
                    R.drawable.c_gb,
                    R.drawable.c_au,
                    R.drawable.c_jp
            )
    );

    static  ArrayList<String> countryCodes = new ArrayList<>(
            Arrays.asList(
                    "in", "us", "gb", "au", "jp"
            )
    );

    static  ArrayList<String> categoryCodes = new ArrayList<>(
            Arrays.asList(
                    "health",
                    "business",
                    "technology",
                    "entertainment",
                    "science",
                    "sports"
            )
    );

    static public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

}
