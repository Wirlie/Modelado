package com.example.appcuentoscontarte;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Final extends AppCompatActivity {

    Button btnmenu,btnsalir,btncompartir;
    int SELECT_FILE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        btnmenu = (Button) findViewById(R.id.btnmenu);
        btnsalir = (Button) findViewById(R.id.btnsalir);
        btncompartir = (Button) findViewById(R.id.btncompartir);

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comenzar = new Intent(Final.this, MainActivity.class);
                Final.this.startActivity(comenzar);
                Final.this.finish();
                //Toast.makeText(getApplicationContext(), "Se presiono", Toast.LENGTH_LONG).show();
                //MainActivity.this.onBackPressed();
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Se presiono", Toast.LENGTH_LONG).show();
                //MainActivity.this.onBackPressed();
                System.exit(0);
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Se presiono COMPARTIR", Toast.LENGTH_LONG).show();

                Intent comenzar = new Intent(Final.this, Compartir.class);
                Final.this.startActivity(comenzar);
                Final.this.finish();


            }
        });
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();

    }
}

