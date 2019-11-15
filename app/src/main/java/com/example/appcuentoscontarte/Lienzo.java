package com.example.appcuentoscontarte;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class Lienzo extends View {

    private Path drawPath;
    private static Paint drawPaint;
    private Paint canvasPaint;
    private static int paintColor = 0xFF353535;
    //private int paintColor = 0xFFFF0000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

   static float TamanoPunto;
   private static boolean borrado = false;

    public Lienzo(Context context, AttributeSet attrs) {

        super(context, attrs);
        setupDrawing();
    }


    private void setupDrawing(){

        //Configuracion del area sobre la que pintar
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        //TamanoPunto= 20;
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX= event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX,touchY);
                drawCanvas.drawPath(drawPath,drawPaint);
                drawPath.reset();
                break;
             default:
               return  false;
        }
        invalidate();
        return true;


        //return super.onTouchEvent(event);
    }

    public  void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);


    }

    //Pinta la vista. Será llamado desde el OnTouchEvent

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap,0,0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }

    public static void setTamanoPunto(float nuevoTamano){
        //float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
          //  nuevoTamano,getResources().getDisplayMetrics());
        //TamanoPunto =pixel;
        drawPaint.setStrokeWidth(nuevoTamano);
    }

    public static void setBorrado(boolean estaborrado){
        borrado = estaborrado;
        if(borrado) {
            drawPaint.setColor(Color.WHITE);
        }
        else{
             drawPaint.setColor(paintColor);
        }

    }

    public void NuevoDibujo(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
