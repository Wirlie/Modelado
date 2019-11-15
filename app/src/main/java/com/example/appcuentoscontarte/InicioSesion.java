package com.example.appcuentoscontarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InicioSesion extends AppCompatActivity  {

    Button btnregistro,btningresar;
    DatabaseReference databaseReference;
    EditText EdtUsuario;
    String user,usingresado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnregistro = findViewById(R.id.btnregistro_sesion);
        btningresar = findViewById(R.id.btnigresar_sesion);
        EdtUsuario = findViewById(R.id.etUsuario);

        //btningresar.setOnClickListener(this);
        //btnregistro.setOnClickListener(this);


        //Hace referencia al nodo principal de BD
        /* databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.exists()){
                        usuario = snapshot.getValue().toString();
                        //usuario = dataSnapshot.child("usuario").getValue().toString();
                        user = EdtUsuario.getText().toString();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */


        btnregistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Se presiono registro", Toast.LENGTH_LONG).show();
                Intent comenzar = new Intent(InicioSesion.this, Registro.class);
                InicioSesion.this.startActivity(comenzar);
                InicioSesion.this.finish();
            }

        });


        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usingresado = EdtUsuario.getText().toString();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                Query query = databaseReference.child("Usuario").orderByChild("usuario").equalTo(usingresado);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                user = snapshot.getValue().toString();
                                Ingresar();

                                //usuario = dataSnapshot.child("usuario").getValue().toString();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
                /*if(usuario==usingresado){
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                    Intent iniciar = new Intent(InicioSesion.this, MainActivity.class);
                    InicioSesion.this.startActivity(iniciar);
                }
                else{
                    EdtUsuario.setText("El usuario no existe");
                }
                Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                Intent iniciar = new Intent(InicioSesion.this, MainActivity.class);
                InicioSesion.this.startActivity(iniciar);*/

        //Toast.makeText(getApplicationContext(), "Se presiono ingresar", Toast.LENGTH_LONG).show();
    }
    private void Ingresar() {
        Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
        Intent iniciar = new Intent(InicioSesion.this, MainActivity.class);
        InicioSesion.this.startActivity(iniciar);
        InicioSesion.this.finish();
    }
}
