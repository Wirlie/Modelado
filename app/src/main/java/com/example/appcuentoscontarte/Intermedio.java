package com.example.appcuentoscontarte;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Intermedio extends AppCompatActivity {
    ImageButton btncuento1, btncuento2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermedio);

        btncuento1 = findViewById(R.id.btncuento1);
        btncuento2 = findViewById(R.id.btncuento2);

        btncuento1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Seleccion de cuento
                Intent cuento = new Intent(Intermedio.this, Inicio.class);
                cuento.putExtra("cuentoseleccionado","caperucita");
                Intermedio.this.startActivity(cuento);
                Intermedio.this.finish();
            }
        });

        btncuento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seleccion de cuento
                Intent cuento = new Intent(Intermedio.this, Inicio.class);
                cuento.putExtra("cuentoseleccionado","pollitocurioso");
                Intermedio.this.startActivity(cuento);
                Intermedio.this.finish();
            }
        });
    }

    private void Creacionmenu(){

    }
}
