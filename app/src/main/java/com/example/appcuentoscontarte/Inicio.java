package com.example.appcuentoscontarte;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inicio extends AppCompatActivity implements View.OnClickListener{

    ImageButton negro, cafe, naranja,rojo, rosa, morado, amarillo,verdelimon, verde,turquesa, celeste, azul;
    MediaPlayer mp;
    ImageButton btnreproducir, btnsiguiente,btnborrador, btntrazo, btnhojanueva, btnguardar;
    TextView tvCuento;
    private int current_frase;

    float ppequeno;
    float pmediano;
    float pgrande;
    float pdefecto;

    private String[] frases;
    private int[] tiempos;
    private  String f, a;
    int sound, i = 0, currentiempo = 0, temp, control = 0;
    boolean sw = true;

    LinearLayout paintLayout1;
    LinearLayout paintLayout2;

    String cs;

    Lienzo lienzo;

    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); */

        paintLayout1 = (LinearLayout)findViewById(R.id.paint_colors_1);
        paintLayout2 = (LinearLayout)findViewById(R.id.paint_colors_2);

        currPaint = (ImageButton)paintLayout2.getChildAt(5);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        btnreproducir = (ImageButton) findViewById(R.id.btnreproducir);
        btnsiguiente = (ImageButton) findViewById(R.id.btnsiguiente);
        btntrazo = (ImageButton)findViewById(R.id.btntrazo);
        btnborrador = (ImageButton)findViewById(R.id.btnborrador);
        btnguardar = (ImageButton)findViewById(R.id.btnguardar);
        btnhojanueva = (ImageButton)findViewById(R.id.btnhojanueva);

        tvCuento = (TextView) findViewById(R.id.tvCuento);

        negro = (ImageButton) findViewById(R.id.btnnegro);
        cafe = (ImageButton) findViewById(R.id.btncafe);
        naranja = (ImageButton) findViewById(R.id.btnnaranja);
        rojo = (ImageButton) findViewById(R.id.btnrojo);
        rosa = (ImageButton) findViewById(R.id.btnrosa);
        morado = (ImageButton) findViewById(R.id.btnmorado);
        amarillo = (ImageButton) findViewById(R.id.btnamarillo);
        verde = (ImageButton) findViewById(R.id.btnverdefuerte);
        verdelimon = (ImageButton) findViewById(R.id.btnverde);
        turquesa = (ImageButton) findViewById(R.id.btnturquesa);
        celeste = (ImageButton) findViewById(R.id.btnceleste);
        azul = (ImageButton) findViewById(R.id.btnazul);

        lienzo = (Lienzo) findViewById(R.id.lienzo);

        ppequeno=10;
        pmediano=20;
        pgrande=30;
        pdefecto = pmediano;

        negro.setOnClickListener(this);
        cafe.setOnClickListener(this);
        naranja.setOnClickListener(this);
        rojo.setOnClickListener(this);
        rosa.setOnClickListener(this);
        morado.setOnClickListener(this);
        amarillo.setOnClickListener(this);
        verde.setOnClickListener(this);
        verdelimon.setOnClickListener(this);
        turquesa.setOnClickListener(this);
        celeste.setOnClickListener(this);
        azul.setOnClickListener(this);

        btnsiguiente.setOnClickListener(this);
        btnreproducir.setOnClickListener(this);
        btntrazo.setOnClickListener(this);
        btnborrador.setOnClickListener(this);
        btnhojanueva.setOnClickListener(this);
        btnguardar.setOnClickListener(this);

        recibirDato();

        sound = getResources().getIdentifier(cs, "raw", getPackageName());
        mp = MediaPlayer.create(this, sound);
        Toast.makeText(getApplicationContext(), sound, Toast.LENGTH_LONG).show();
        temp = getResources().getIdentifier(cs + "frases", "array", getPackageName());
        frases = getResources().getStringArray(temp);
        temp = getResources().getIdentifier(cs + "tiempos", "array", getPackageName());
        tiempos = getResources().getIntArray(temp);

        control=0;
        current_frase = 0;
        f = frases[current_frase];
        tvCuento.setText(f);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mp.isPlaying()){
            mp.stop();
        }
    }

    private class playmp extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if(i == 0){
                mp.start();
                control++;
            }
            else{
                if(!mp.isPlaying() && sw == true){
                    mp.seekTo(tiempos[i-1]);
                    mp.start();
                    control++;
                }
            }
            while (mp.isPlaying()){
                currentiempo = mp.getCurrentPosition();
                if(currentiempo == tiempos[i]){
                    if(control == tiempos.length){
                        sw = false;
                        break;
                    }
                    else{
                        mp.pause();
                        i++;
                    }
                }
            }
            current_frase++;
            f = frases[current_frase];
            return null;
        }
    }
// BITMAP


    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas scanvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
           //Toast.makeText(getApplicationContext(), " tiene dibujable", Toast.LENGTH_LONG).show();
            bgDrawable.draw(scanvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            scanvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(scanvas);
        //return the bitmap
        return returnedBitmap;
    }

    private File saveBitMap(Context context, View drawView){

        File pictureFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!pictureFileDir.exists()) {
            //Toast.makeText(getApplicationContext(), " NO Sigue", Toast.LENGTH_LONG).show();
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                 //Toast.makeText(getApplicationContext(), "  No se pudo crear directorio", Toast.LENGTH_LONG).show();
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmsshhmmss");
        String date= simpleDateFormat.format(new Date());
        String name ="Img"+date+".jpg";
        String filename=pictureFileDir.getPath()+"/"+name;
        if(filename!=null){
           // Toast.makeText(getApplicationContext(), "Nombre archivo: "+filename, Toast.LENGTH_LONG).show();
        }

        File pictureFile = new File(filename);
        Bitmap sbitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
           // Toast.makeText(getApplicationContext(), " Se creó imagen 2", Toast.LENGTH_LONG).show();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            sbitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), " No se creó imagen", Toast.LENGTH_LONG).show();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }

    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

    private void recibirDato(){
        Bundle extras = getIntent().getExtras();
        cs = extras.getString("cuentoseleccionado");
    }
/*
    public static  Bitmap viewToBitmap(View view,int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        return bitmap;
    }

    public void startSave(){
        FileOutputStream fileOutputStream=null;
        File file=getDisc();
        if(!file.exists() && !file.mkdir()){
            Toast.makeText(getApplicationContext(),"No se puede guardar este directorio",Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmsshhmmss");
        String date= simpleDateFormat.format(new Date());
        String name ="Img"+date+".jpg";
        String file_name=file.getAbsolutePath()+"/"+name;
        File new_file=new File(file_name);
        try{
            fileOutputStream=new FileOutputStream(new_file);
            Bitmap bitmap = viewToBitmap(lienzo,lienzo.getWidth(),lienzo.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Toast.makeText(getApplicationContext(),"Save image sucess",Toast.LENGTH_SHORT).show();

            fileOutputStream.flush();
            fileOutputStream.close();


        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshGallery(new_file);

    }

    public  void  refreshGallery(File file){
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        return  new File(file,"Image Demogg");

    }

 */

    @Override
    public void onClick(View v) {

        String color = null;

        switch (v.getId()) {

            case R.id.btnhojanueva:
                AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Nuevo Dibujo");
                newDialog.setMessage("¿Comenzar un nuevo dibujo? (perderás el dibujo actual");
                newDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lienzo.NuevoDibujo();
                        dialog.dismiss();

                    }
                });
                newDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });
                newDialog.show();


                break;

            case R.id.btnguardar:

                AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
                salvarDibujo.setTitle("Guardar Dibujo");
                salvarDibujo.setMessage("¿Desea guardar dibujo? ");
                salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //   startSave();

                        View savingLayout = (View) findViewById(R.id.lienzo);
                        File file = saveBitMap(Inicio.this, savingLayout);
                        if (file != null) {
                            Toast.makeText(getApplicationContext(), "¡Dibujo guardado en la galería!", Toast.LENGTH_LONG).show();

                            Log.i("TAG", "Drawing saved to the gallery!");
                        } else {
                            Toast.makeText(getApplicationContext(), "¡ Oops, dibujo  no se ha guardado en la galería!", Toast.LENGTH_LONG).show();

                            Log.i("TAG", "Oops! Image could not be saved.");
                        }

                    }
                });
                salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });
                salvarDibujo.show();
                break;

            case R.id.btnborrador: /*Borrador*/
                final Dialog borrarpunto = new Dialog(this);
                borrarpunto.setTitle("Tamaño del borrado: ");
                borrarpunto.setContentView(R.layout.tamano_punto);

                TextView smallBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tpequeno);
                smallBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanoPunto(ppequeno);

                        borrarpunto.dismiss();

                    }
                });
                TextView mediumBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tmediano);
                mediumBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanoPunto(pmediano);

                        borrarpunto.dismiss();
                    }
                });
                TextView bigBtnBorrar = (TextView) borrarpunto.findViewById(R.id.tgrande);
                bigBtnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(true);
                        Lienzo.setTamanoPunto(pgrande);

                        borrarpunto.dismiss();
                    }
                });
                borrarpunto.show();
                break;

            case R.id.btntrazo: /* Tamaño de punto*/
                final Dialog tamanopunto = new Dialog(this);
                // Toast.makeText(getApplicationContext(), "Presiono trazo", Toast.LENGTH_LONG).show();
                tamanopunto.setTitle("Tamaño del punto: ");
                tamanopunto.setContentView(R.layout.tamano_punto);

                TextView smallBtn = (TextView) tamanopunto.findViewById(R.id.tpequeno);
                smallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Lienzo.setBorrado(false);
                        Lienzo.setTamanoPunto(ppequeno);

                        tamanopunto.dismiss();

                    }
                });
                TextView mediumBtn = (TextView) tamanopunto.findViewById(R.id.tmediano);
                mediumBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Lienzo.setBorrado(false);
                        Lienzo.setTamanoPunto(pmediano);

                        tamanopunto.dismiss();
                    }
                });
                TextView bigBtn = (TextView) tamanopunto.findViewById(R.id.tgrande);
                bigBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Lienzo.setBorrado(false);
                        Lienzo.setTamanoPunto(pgrande);

                        tamanopunto.dismiss();
                    }
                });
                tamanopunto.show();
                break;
            case R.id.btnreproducir:
//                String naudio = String.valueOf(current_audio);
//                Toast.makeText(getApplicationContext(), naudio, Toast.LENGTH_LONG).show();
//
//                Intent fin= new Intent(Inicio.this, Final.class);
//
//                Inicio.this.startActivity(fin);
//                Inicio.this.finish();
                break;

            case R.id.btnsiguiente:
                playmp nextau = new playmp();
                btnsiguiente.setEnabled(false);
                nextau.execute();
                btnsiguiente.setEnabled(true);
                tvCuento.setText(f);
                break;

            case R.id.btnnegro:
                color = v.getTag().toString();
                lienzo.setColor(color);

                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }



                break;
            case R.id.btncafe:
                color = v.getTag().toString();
                lienzo.setColor(color);

                if(v!=currPaint) {

                ImageButton imgView = (ImageButton) v;
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                currPaint = (ImageButton) v;
            }
                break;
            case R.id.btnnaranja:
                color = v.getTag().toString();
                lienzo.setColor(color);

                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnrojo:
                color = v.getTag().toString();
                lienzo.setColor(color);

                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnrosa:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnmorado:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnamarillo:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnverde:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnverdefuerte:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnturquesa:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnceleste:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            case R.id.btnazul:
                color = v.getTag().toString();
                lienzo.setColor(color);
                if(v!=currPaint) {

                    ImageButton imgView = (ImageButton) v;
                    imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                    currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
                    currPaint = (ImageButton) v;
                }
                break;
            default:
                break;

        }

    }
}