package cmov.miguellucas.com.tp2;

/**
 * Created by Miguel Lucas on 06/10/2018.
 */

public class Restaurant {

    private String name;
    private String address;

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
}
