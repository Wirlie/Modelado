package com.example.appcuentoscontarte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText usuario,correo;
    Button btnRegistro,btnInicioSesion;
    boolean existe;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = findViewById(R.id.etUsuario);
        correo = findViewById(R.id.etCorreo);
        btnRegistro = findViewById(R.id.btnregistrar_registro);
        btnInicioSesion = findViewById(R.id.btningreso_sesion);

        inicializarFirebase();

        btnRegistro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String user = usuario.getText().toString();
                String mail = correo.getText().toString();
                user.replaceAll("\\s","");
                mail.replaceAll("\\s","");
                if (user.equals("") || mail.equals("")){
                    validacion();
                }
                else{
//                   validarUsuario(user);
                   if(!validarUsuario(user)){
                       usuario.setError("El usuario ya existe");
                   }
                   else {
//                       validarEmail(mail);
                       if(!validarEmail(mail)){
                           correo.setError("Correo no valido");
                       }
                       else {

                           Usuario u = new Usuario();
                           u.setUsuario(user);
                           u.setCorreo(mail);
                           databaseReference.child("Usuario").child(u.getUsuario()).setValue(u);
                           Toast.makeText(Registro.this, "Se ha registrado", Toast.LENGTH_LONG).show();

                           limpiarCampos();
                       }
                   }
                }


            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent comenzar = new Intent(Registro.this, InicioSesion.class);
                Registro.this.startActivity(comenzar);
            }
        });


    }

    private boolean validarUsuario(String user) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Usuario").orderByChild("usuario").equalTo(user);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        existe = true;

                    }
                } else {
                    existe = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return existe;
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Guarda los cambios en la BD local cuando no hay internet, cuando se conecta hace push de los cambios
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiarCampos() {
        usuario.setText("");
        correo.setText("");
    }

    private void validacion() {
        String user = usuario.getText().toString();
        String mail = correo.getText().toString();
        if(user.equals("")){
            usuario.setError("Obligatorio");
        }
        if(mail.equals("")){
            correo.setError("Obligatorio");
        }
    }


}
