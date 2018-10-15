package cmov.miguellucas.com.tp2;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DetailsActivity extends AppCompatActivity {

    LunchApp app;
    int rPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_details);
        app = (LunchApp)getApplicationContext();
        findViewById(R.id.btn_save).setOnClickListener((View v)->onBtSaveClick());

        rPos=getIntent().getIntExtra(MainActivity.ID_EXTRA, -1);
        if (rPos != -1)
            load();
    }

    void onBtSaveClick() {
        RestaurantType type = null;

        switch (((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId()) {
            case R.id.type_sit_down:
                type = RestaurantType.SIT_DOWN;
                break;
            case R.id.type_take_out:
                type = RestaurantType.TAKE_OUT;
                break;
            case R.id.type_delivery:
                type = RestaurantType.DELIVERY;
                break;
        }
        if (rPos!=-1)
            app.adapter.remove(app.currentRestaurant);
        app.currentRestaurant = new Restaurant();
        app.currentRestaurant.setName(((EditText)findViewById(R.id.name)).getText().toString());
        app.currentRestaurant.setAddress(((EditText)findViewById(R.id.address)).getText().toString());
        app.currentRestaurant.setNotes(((EditText)findViewById(R.id.notes)).getText().toString());
        app.currentRestaurant.setRestaurantType(type);
        if (rPos!=-1)
            app.adapter.insert(app.currentRestaurant, rPos);
        else
            app.adapter.add(app.currentRestaurant);
        finish();
    }

    void load() {
        ((EditText)findViewById(R.id.name)).setText(app.currentRestaurant.getName());
        ((EditText)findViewById(R.id.address)).setText(app.currentRestaurant.getAddress());
        ((EditText)findViewById(R.id.notes)).setText(app.currentRestaurant.getNotes());
        RadioGroup rgTypes = findViewById(R.id.radioGroup);
        if (app.currentRestaurant.getRestaurantType() == RestaurantType.SIT_DOWN)
            rgTypes.check(R.id.type_sit_down);
        else if (app.currentRestaurant.getRestaurantType() == RestaurantType.TAKE_OUT)
            rgTypes.check(R.id.type_take_out);
        else
            rgTypes.check(R.id.type_delivery);
    }
}
