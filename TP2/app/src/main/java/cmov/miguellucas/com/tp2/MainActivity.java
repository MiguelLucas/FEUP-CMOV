package cmov.miguellucas.com.tp2;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }
    }

    public void saveBtn(View view){
        EditText editText_name = findViewById(R.id.name);
        String name = editText_name.getText().toString();

        EditText editText_address = findViewById(R.id.address);
        String address = editText_address.getText().toString();
        Restaurant restaurant = new Restaurant(name, address);
    }
}
