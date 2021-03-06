package cmov.miguellucas.com.tp2;

import java.io.Serializable;

/**
 * Created by Miguel Lucas on 06/10/2018.
 */

public class Restaurant implements Serializable {

    private String name;
    private String address;
    private RestaurantType restaurantType;
    private String notes;

    public Restaurant(){

    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String name) {
        this.notes = name;
    }

    @Override
    public String toString(){
        return name;
    }
}

