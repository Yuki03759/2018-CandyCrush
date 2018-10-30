package com.zotcomm.zotcrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.TimeUnit;


public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.image);   //put an image called 'image(you can of change it to other name)' under the folder drawble
    Bitmap RedCandy = BitmapFactory.decodeResource(getResources(), R.drawable.redcandy);
    Bitmap BlueCandy = BitmapFactory.decodeResource(getResources(), R.drawable.bluecandy);
    Bitmap GreenCandy = BitmapFactory.decodeResource(getResources(), R.drawable.greencandy);
    Bitmap YellowCandy = BitmapFactory.decodeResource(getResources(), R.drawable.yellowcandy);
    Bitmap chocolate = BitmapFactory.decodeResource(getResources(), R.drawable.chocolate);
    Bitmap PurpleCandy = BitmapFactory.decodeResource(getResources(), R.drawable.purplecandy);
    Bitmap textboxBG = BitmapFactory.decodeResource(getResources(), R.drawable.textbox_bg);
    Rect dst = new Rect();
    Rect scoreBox = new Rect();
    Rect turnBox = new Rect();


    float FirstLeftRight = 90;
    float FirstTopBottom = 40;
    float CandySize = 100;
    int column1=-1, row1=-1;
    int column2=-1, row2=-1;
    int counter = 0;

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean gameOver = false;

    Context board1;

    private CandyGame game = new CandyGame();
    CandyTypes[][] board = game.getBoard();

    public BoardView(Context context) {
        super(context); // Notify the SurfaceHolder that you'd like to receive SurfaceHolder callbacks
        getHolder().addCallback(this);
        setFocusable(true); // Very important
        setWillNotDraw(false); // initialize game state variables and the game board variables
                               // don't render the game let

        board1 = context;
        invalidate();

    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); // Notify the SurfaceHolder that you'd like to receive SurfaceHolder callbacks
        getHolder().addCallback(this);
        setFocusable(true); // Very important
        setWillNotDraw(false); // initialize game state variables and the game board variables
        // don't render the game let
        board1 = context;
        invalidate();

    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs); // Notify the SurfaceHolder that you'd like to receive SurfaceHolder callbacks
        getHolder().addCallback(this);
        setFocusable(true); // Very important
        setWillNotDraw(false); // initialize game state variables and the game board variables
        // don't render the game let
        board1 = context;
        invalidate();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//Construct game initial state, //Create the Candy Board, //Initialize the board with random candies


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {   //Construct game initial state

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {        //This is called immediately before a surface is being destroyed ,//Write code here that needs to be executed just before the surface is destroyed

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        //change the matrix
        if (e.getAction() == MotionEvent.ACTION_DOWN){
            column1 = column2 = game.getCoordinateX(e.getX());
            row1 = row2 = game.getCoordinateY(e.getY());
            System.out.println("row1 = " + row1 + ", column1 = " + column1);
        }

        else if (e.getAction() == MotionEvent.ACTION_UP) {

            column2 = game.getCoordinateX(e.getX());
            row2 = game.getCoordinateY(e.getY());

            System.out.println("row2 = " + row2 + ", column2 = " + column2);
            if (game.isValidMove(row1, column1, row2, column2)) {
                game.performMove(row1, column1, row2, column2);
                counter++;
                System.out.println("counter = " + counter);
                game.eliminateFamily();
                game.shiftDown();
                System.out.println("score = " + game.getScore());

                if (game.getScore() >= 5000) {
                    gameOver = true;
                }
            }
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        dst.set(0, 0, this.getWidth(), this.getHeight());
        c.drawBitmap(background, null, dst, null);//draw the image you putted in the folder drawable


        scoreBox.set((this.getWidth()) - 450, (this.getHeight()) - 250, this.getWidth(), this.getHeight());
        turnBox.set(0, this.getHeight(), this.getWidth() - 620, this.getHeight() - 250);
        c.drawBitmap(textboxBG, null, scoreBox, null);
        c.drawBitmap(textboxBG, null, turnBox, null);

       if (gameOver != true) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case RedCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(RedCandy, null, dst, null);
                        break;
                    case BlueCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(BlueCandy, null, dst, null);
                        break;
                    case GreenCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(GreenCandy, null, dst, null);

                        break;
                    case YellowCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(YellowCandy, null, dst, null);
                        break;
                    case GrayCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(chocolate, null, dst, null);
                        break;
                    case PurpleCandy:
                        dst.set((int) (FirstLeftRight + i * CandySize), (int) (FirstTopBottom + j * CandySize),
                                (int) (FirstLeftRight + (i + 1) * CandySize), (int) (FirstTopBottom + (j + 1) * CandySize));
                        c.drawBitmap(PurpleCandy, null, dst, null);
                        break;
                }
             }
            }
        }

        else
       {
           Paint win = new Paint();
           Paint bgPaint = new Paint();
           bgPaint.setARGB(170,255,255,255);
           win.setTextSize(140);
           win.setColor(Color.BLACK);
           Rect bg = new Rect();
           bg.set(0, 700, this.getHeight(), 300);
           c.drawRect(bg, bgPaint);
           c.drawText("You Win! :D",getWidth()/6,getHeight()/3, win);


       }

        Paint paint = new Paint();
        paint.setTextSize(70);
        paint.setColor(Color.BLACK);
        c.drawText("Score",5*getWidth()/6 - 130,6*getHeight()/6 - 160,paint);
        c.drawText("Moves",1*getWidth()/6 - 50,6*getHeight()/6 - 140,paint);
        if (String.valueOf(game.score).length() == 1)
        {
            c.drawText("" + Integer.toString(game.score),5*getWidth()/6 - 60,6*getHeight()/6 - 60,paint);
        }
        else if (String.valueOf(game.score).length() == 2)
        {
            c.drawText("" + Integer.toString(game.score),5*getWidth()/6 - 85,6*getHeight()/6 - 60,paint);
        }
        else if (String.valueOf(game.score).length() == 3)
        {
            c.drawText("" + Integer.toString(game.score),5*getWidth()/6 - 100,6*getHeight()/6 - 60,paint);
        }
        else if (String.valueOf(game.score).length() == 4)
        {
            c.drawText("" + Integer.toString(game.score),5*getWidth()/6 - 130,6*getHeight()/6 - 60,paint);
        }
        else if (String.valueOf(game.score).length() == 5)
        {
            c.drawText("" + Integer.toString(game.score),5*getWidth()/6 - 150,6*getHeight()/6 - 60,paint);
        }

        if (String.valueOf(game.turns).length() == 1) {
            c.drawText("" + Integer.toString(game.turns), 1 * getWidth() / 6 + 25, 6 * getHeight() / 6 - 60, paint);
        }
        else if (String.valueOf(game.turns).length() == 2) {
            c.drawText("" + Integer.toString(game.turns), 1 * getWidth() / 6 + 15, 6 * getHeight() / 6 - 60, paint);
        }
    }

    }




