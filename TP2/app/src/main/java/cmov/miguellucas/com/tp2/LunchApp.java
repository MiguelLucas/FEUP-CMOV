package cmov.miguellucas.com.tp2;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel Lucas on 15/10/2018.
 */

public class LunchApp extends Application {
    public List<Restaurant> restaurants = new ArrayList<>();
    public MainActivity.RestaurantAdapter adapter;
    public Restaurant currentRestaurant;
}
