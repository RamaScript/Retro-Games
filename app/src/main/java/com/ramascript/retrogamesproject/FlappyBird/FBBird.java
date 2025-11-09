package com.ramascript.retrogamesproject.FlappyBird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class FBBird extends FBBaseObject {
    private ArrayList<Bitmap> arrbms = new ArrayList<>();
    public int count,vFlap,idCurrentBitmap;
    private float drop;
    public FBBird(){
        this.count = 0;
        this.vFlap = 5 ;
        this.idCurrentBitmap = 0;
        this.drop = 0;
    }
    public void draw(Canvas canvas){
        drop();
        canvas.drawBitmap(this.getBm(),this.x,this.y,null);
    }

    private void drop() {
        this.drop+=0.9;
        this.y+=this.drop;
    }

    public ArrayList<Bitmap> getArrbms() {
        return arrbms;
    }

    public void setArrbms(ArrayList<Bitmap> arrbms) {
        this.arrbms = arrbms;
        for (int i=0; i< arrbms.size(); i++){
            this.arrbms.set(i,Bitmap.createScaledBitmap(this.arrbms.get(i),this.width,this.height,true));
        }
    }

    @Override
    public Bitmap getBm() {
        count++;
        if(this.count == this.vFlap){
            for(int i = 0; i < arrbms.size(); i++){
                if (i == arrbms.size()-1){
                    this.idCurrentBitmap=0;
                    break;
                }else if(this.idCurrentBitmap == i){
                    idCurrentBitmap = i+1;
                    break;
                }
            }
            count = 0;
        }
        if(this.drop<0){
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(arrbms.get(idCurrentBitmap),0,0,arrbms.get(idCurrentBitmap).getWidth(),arrbms.get(idCurrentBitmap).getHeight(),matrix,true);
        }else if (drop>=0){
            Matrix matrix = new Matrix();
            if (drop<70){
                matrix.postRotate(-25+(drop*2));
            }else{
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(arrbms.get(idCurrentBitmap),0,0,arrbms.get(idCurrentBitmap).getWidth(),arrbms.get(idCurrentBitmap).getHeight(),matrix,true);
        }
        return this.arrbms.get(idCurrentBitmap);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}
