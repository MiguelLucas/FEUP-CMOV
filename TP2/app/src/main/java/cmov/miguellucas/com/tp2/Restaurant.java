package cmov.miguellucas.com.tp2;

/**
 * Created by Miguel Lucas on 06/10/2018.
 */

public class Restaurant {

    private String name;
    private String address;
    private RestaurantType restaurantType;

    public Restaurant(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    @Override
    public String toString(){
        return name;
    }
}

