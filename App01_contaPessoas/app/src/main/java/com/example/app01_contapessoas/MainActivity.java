package com.example.app01_contapessoas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int numberBoy = 0;
    int numberGirl = 0;
    int numberTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final TextView campTextTotal = (TextView) findViewById(R.id.campTextTotal);

        final Button buttonBoy = (Button) findViewById(R.id.buttonBoy);
        final Button buttonGirl = (Button) findViewById(R.id.buttonGirl);
        final Button buttonReset = (Button) findViewById(R.id.buttonReset);

        buttonBoy.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            numberBoy++;
            numberTotal++;

            String message = Integer.toString(numberTotal);
            campTextTotal.setText("Total: " + message + " pessoas");
            buttonBoy.setText(Integer.toString(numberBoy));
            }
        });

        buttonGirl.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberGirl++;
                numberTotal++;

                String message = Integer.toString(numberTotal);
                campTextTotal.setText("Total: " + message + " pessoas");
                buttonGirl.setText(Integer.toString(numberGirl));
            }
        });

        buttonReset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberBoy = 0;
                numberGirl = 0;
                numberTotal = 0;

                String message = Integer.toString(numberTotal);
                campTextTotal.setText("Total: " + message + " pessoas");
                buttonBoy.setText(Integer.toString(numberBoy));
                buttonGirl.setText(Integer.toString(numberGirl));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}