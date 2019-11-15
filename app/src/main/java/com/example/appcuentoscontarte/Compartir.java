package com.example.appcuentoscontarte;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Compartir extends AppCompatActivity {

    ImageView imagen;
    Button seleccionarImagen, compartir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);

        imagen = (ImageView) findViewById(R.id.imageView);
        seleccionarImagen = (Button) findViewById(R.id.btnseleccionar_imagen);
        compartir = (Button) findViewById(R.id.btncompartir_imagen);
        compartir.setVisibility(View.INVISIBLE);

        seleccionarImagen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable = imagen.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)myDrawable).getBitmap();

                //Compartir imagen

                try {
                    File file = new File(Compartir.this.getExternalCacheDir(),"Dibujo.jpeg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,80,fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true,false);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                    intent.setType("image/jpeg");
                    startActivity(Intent.createChooser(intent,"Compartir vía"));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(Compartir.this,"Archivo no encontrado",Toast.LENGTH_SHORT).show();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
        compartir.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}
