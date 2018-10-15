package cmov.miguellucas.com.tp2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, TabLayout.OnTabSelectedListener{

    List<Restaurant> rests = new ArrayList<>();
    RestaurantAdapter adapter;
    TabLayout.Tab listTab, details;
    View tab1, tab2;
    Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }

        ListView listView = findViewById(R.id.list_view);
        adapter = new RestaurantAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        TabLayout tabs = findViewById(R.id.tabs);
        listTab = tabs.newTab().setText("List");
        tabs.addTab(listTab);
        details = tabs.newTab().setText("Details");
        tabs.addTab(details);
        tabs.addOnTabSelectedListener(this);
        tab1 = listView;
        tab2 = findViewById(R.id.linear_layout);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putSerializable("currentRestaurant", currentRestaurant);
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        currentRestaurant = (Restaurant) state.getSerializable("currentRestaurant");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toast) {
            String message="No restaurant selected";
            if (currentRestaurant != null)
                message = currentRestaurant.getNotes();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void saveBtn(View view){
        EditText editText_name = findViewById(R.id.name);
        String name = editText_name.getText().toString();

        EditText editText_address = findViewById(R.id.address);
        String address = editText_address.getText().toString();

        EditText editText_notes = findViewById(R.id.notes);
        String notes = editText_notes.getText().toString();

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Restaurant restaurant = new Restaurant(name, address);
        restaurant.setNotes(notes);

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.type_sit_down:
                restaurant.setRestaurantType(RestaurantType.SIT_DOWN);
                break;
            case R.id.type_take_out:
                restaurant.setRestaurantType(RestaurantType.TAKE_OUT);
                break;
            case R.id.type_delivery:
                restaurant.setRestaurantType(RestaurantType.DELIVERY);
                break;
        }
        adapter.add(restaurant);
        clearKeyboard(this);   // hide the soft keyboard, if present
        currentRestaurant = restaurant;
        listTab.select();     // switch to the list tab
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Restaurant rest = rests.get(pos);

        EditText editText_name = findViewById(R.id.name);
        editText_name.setText(rest.getName());

        EditText editText_address = findViewById(R.id.address);
        editText_address.setText(rest.getAddress());

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        if (rest.getRestaurantType().equals(RestaurantType.SIT_DOWN)) {
            radioGroup.check(R.id.type_sit_down);
        }
        else if (rest.getRestaurantType().equals(RestaurantType.TAKE_OUT)) {
            radioGroup.check(R.id.type_take_out);
        }
        else {
            radioGroup.check(R.id.type_delivery);
        }

        currentRestaurant = rest;
        details.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab1.setVisibility(View.VISIBLE);
                break;
            case 1:
                tab2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab1.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tab2.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        RestaurantAdapter() {
            super(MainActivity.this, R.layout.row, rests);
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false);    // get our custom layout
            }
            Restaurant r = rests.get(position);
            ((TextView)row.findViewById(R.id.title)).setText(r.getName());      // fill restaurant name
            ((TextView)row.findViewById(R.id.address_row)).setText(r.getAddress());      // fill restaurant address
            ImageView symbol = row.findViewById(R.id.symbol);
            if (r.getRestaurantType().equals(RestaurantType.SIT_DOWN))
                symbol.setImageResource(R.drawable.ball_red);
            else if (r.getRestaurantType().equals(RestaurantType.TAKE_OUT))
                symbol.setImageResource(R.drawable.ball_yellow);
            else
                symbol.setImageResource((R.drawable.ball_green));
            return (row);
        }
    }

    void clearKeyboard(Activity act) {
        View view = act.findViewById(android.R.id.content);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
