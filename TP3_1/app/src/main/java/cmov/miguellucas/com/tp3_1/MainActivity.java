package cmov.miguellucas.com.tp3_1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_calculate = findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText edit_capital = findViewById(R.id.capital);
        String str_capital = edit_capital.getText().toString();

        EditText edit_interest = findViewById(R.id.interest);
        String str_interest = edit_interest.getText().toString();

        EditText edit_duration = findViewById(R.id.duration);
        String str_duration = edit_duration.getText().toString();

        try {
            double capital = Double.parseDouble(str_capital);
            double interest = Double.parseDouble(str_interest);
            int duration = Integer.parseInt(str_duration);

            double temp = Math.pow(1 + ((interest/100) / 12), duration);
            double payment = capital * ((((interest/100) / 12) * temp) / (temp - 1));

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            String payment_rounded = df.format(payment);

            TextView txtvw_payment = findViewById(R.id.payment);
            txtvw_payment.setText("Your payment is " + payment_rounded);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
