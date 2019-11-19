package com.example.appcuentoscontarte;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btncomenzar_menu, btnsalir_menu, btninfo_menu;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //codigo adicional
        this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncomenzar_menu = (Button)findViewById(R.id.btncomenzar_menu);
        btnsalir_menu = (Button)findViewById(R.id.btnsalir_menu);
        btninfo_menu = (Button)findViewById(R.id.btninfo_menu);



        btncomenzar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comenzar = new Intent(MainActivity.this, Intermedio.class);
                MainActivity.this.startActivity(comenzar);
                MainActivity.this.finish();
            }
        });

        btnsalir_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Se presiono SALIR", Toast.LENGTH_LONG).show();
                System.exit(0);
            }
        });
        btninfo_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(MainActivity.this, Info.class);
                MainActivity.this.startActivity(info);
                MainActivity.this.finish();
            }
        });
    }
}
