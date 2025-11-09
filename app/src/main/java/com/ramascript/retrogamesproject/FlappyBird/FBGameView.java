package com.ramascript.retrogamesproject.FlappyBird;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.ramascript.retrogamesproject.R;

import java.util.ArrayList;

public class FBGameView extends View {
    private FBBird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<FBpipe> arrPipes = new ArrayList<>();
    private int sumpipe,distance;
    private int score,bestscore=0;
    private boolean start;
    private Context context;

    public FBGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting",Context.MODE_PRIVATE);
        if (sp!=null){
            bestscore = sp.getInt("bestscore",0);
        }
        score = 0;
        start = false;
        initBird();
        initPipe();

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private void initPipe() {
        sumpipe = 6;
        distance = 450* FBConstants.SCREEN_HEIGHT/1920;
        arrPipes=new ArrayList<>();
        for (int i = 0; i<sumpipe;i++){
            if (i<sumpipe/2){
                this.arrPipes.add(new FBpipe(FBConstants.SCREEN_WIDTH+i*(FBConstants.SCREEN_WIDTH+200* FBConstants.SCREEN_WIDTH/1000)/(sumpipe/2),0,200* FBConstants.SCREEN_WIDTH/1080, FBConstants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.fb_pipe2));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();
            }else{
                this.arrPipes.add(new FBpipe(this.arrPipes.get(i-sumpipe/2).getX(),this.arrPipes.get(i-sumpipe/2).getY()
                        +this.arrPipes.get(i-sumpipe/2).getHeight()+this.distance,200* FBConstants.SCREEN_WIDTH/1080, FBConstants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.fb_pipe1));
            }
        }
    }

    private void initBird() {
        bird = new FBBird();
        bird.setWidth(100* FBConstants.SCREEN_WIDTH/1000);
        bird.setHeight(100* FBConstants.SCREEN_HEIGHT/1920);
        bird.setX((float) (100 * FBConstants.SCREEN_WIDTH) /1000);
        bird.setY((float) FBConstants.SCREEN_HEIGHT /2- (float) bird.getHeight() /2);
        ArrayList<Bitmap> arrbms = new ArrayList<>();
        arrbms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.fb_bird1));
        arrbms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.fb_bird2));
        bird.setArrbms(arrbms);
    }

    public void draw (Canvas canvas) {
        super.draw(canvas);
//        canvas.drawColor(Color.BLUE);
        if(start){
            bird.draw(canvas);
            for (int i=0; i<sumpipe;i++){
                if (bird.getRect().intersect(arrPipes.get(i).getRect())||bird.getHeight()<0||bird.getY()> FBConstants.SCREEN_HEIGHT){
                    FBpipe.speed = 0;
                    FBmain.txt_score_over.setText(FBmain.txt_score.getText());
                    FBmain.txt_best_score.setText("Best : "+bestscore);
                    FBmain.txt_score.setVisibility(INVISIBLE);
                    FBmain.rl_game_over.setVisibility(VISIBLE);
                }
                if (this.bird.getX()+this.bird.getWidth()>arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth()<=arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2+FBpipe.speed
                        && i<sumpipe/2){
                    score++;
                    if(score>bestscore){
                        bestscore = score;
                        SharedPreferences sp = context.getSharedPreferences("gamesetting",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestscore",bestscore);
                        editor.apply();
                    }
                    FBmain.txt_score.setText(""+score);
                }
                if (this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()){
                    this.arrPipes.get(i).setX(FBConstants.SCREEN_WIDTH);
                    if (i<sumpipe/2){
                        arrPipes.get(i).randomY();
                    }else {
                        arrPipes.get(i).setY(this.arrPipes.get(i-sumpipe/2).getY()
                                +this.arrPipes.get(i-sumpipe/2).getHeight()+this.distance);
                    }
                }
                this.arrPipes.get(i).draw(canvas);
            }
        }else {
            if (bird.getY()> FBConstants.SCREEN_HEIGHT/2){
                bird.setDrop(-15* FBConstants.SCREEN_HEIGHT/1920);
            }
            bird.draw(canvas);
        }
        handler.postDelayed(r,25);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            bird.setDrop(-15);
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        FBmain.txt_score.setText("0");
        score=0;
        initPipe();
        initBird();
    }
}
