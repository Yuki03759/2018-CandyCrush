package com.zotcomm.zotcrush;

import android.app.Activity;

import java.util.Random;

enum CandyTypes{
    RedCandy,
    BlueCandy,
    YellowCandy,
    GreenCandy,
    GrayCandy,
    PurpleCandy,
    BlackCandy
}

public class CandyGame extends Activity {
    private CandyTypes[][] board;
    public int score;
    public int turns;
    float FirstLeftRight  = 90;
    float FirstTopBottom = 40;
    float CandySize = 100;

    public CandyGame (){
        board = new CandyTypes[9][9];
        score  = 0;

        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < 9; j++) {
                boolean accepted = false;
                while(!accepted) {
                    board[i][j] = getColorType();

                    accepted =  !( (i > 1 && board[i - 1][j] == board[i][j] && board[i - 2][j] == board[i][j])    //above
                                         ||
                            (j > 1 && board[i][j - 1] == board[i][j] && board[i][j - 2] == board[i][j]) )  ;   //left
                }
            }
        }
    }

    public CandyTypes getColorType(){
        Random rand = new Random();
        return CandyTypes.values()[rand.nextInt(6)];
    }

    public CandyTypes[][] getBoard() {
        return this.board;
    }

    public int getCoordinateX(float x){
        if (x > FirstLeftRight + 9*CandySize || x < FirstLeftRight)
            return -1;
        else {
            return (int) ((x-FirstLeftRight)/CandySize);
        }
    }

    public int getCoordinateY(float y){
        if (y > FirstTopBottom + 9*CandySize || y < FirstTopBottom )
            return -1;
        else{
            return (int) ((y-FirstTopBottom)/CandySize);
        }
    }

    public boolean isValidMove(int row1, int col1, int row2, int col2){
        if(row1 == -1 || col1 ==-1 || row2 == -1 || col2 == -1) //only one move
            return false;
        if(
                !( row1 == row2 && col2 == col1 + 1) &&   //right
                ! ( row1 == row2 && col2 == col1 -1) &&   //left
                !( row2 == row1+1 && col1 == col2) &&     //below
                !( row2 == row1-1 && col1 == col2) )      //above
            return false;
        turns++;
        //check if move would create a family
        performMove( row1, col1, row2, col2);
        boolean createsFamily = (isPartOfFamily(row1, col1) || isPartOfFamily(row2, col2));
        performMove(row2, col2, row1, col1);
        System.out.println( "Is valid move :" + createsFamily +" row1 = " + row1 + ", col1 = " + col1 + "  " + " row2 = " + row2 + " col2 = " + col2  );

        return createsFamily;
        }

    public void shiftDown(){
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j] == CandyTypes.BlackCandy){
                    int k = j;
                    while(k >0){
                        board[i][k] = board[i][k-1];
                        k--;
                    }
                    //only changing column
                    board[i][0] = getColorType();
                    eliminateFamily();
                }
            }
        }
    }
//i - col, j 0 row
    public boolean isPartOfFamily(int j, int i) {

        return (j<7 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2])  ||
                (j>1 && board[i][j] == board[i][j-1] && board[i][j] == board[i][j-2]) ||
                (i<7 && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j]) ||
                (i>1 && board[i][j] == board[i-1][j] && board[i][j] == board[i-2][j]) ||
                (j>0 && j < 8 && board[i][j] == board[i][j-1] && board[i][j] == board[i][j+1]) ||
                (i>0 && i < 8 && board[i][j] == board[i-1][j] && board[i][j] == board[i+1][j]);
    }

    public void performMove(int row1, int col1, int row2, int col2) {
        CandyTypes temp = this.board[col1][row1];
        this.board[col1][row1] = this.board[col2][row2];
        this.board[col2][row2] = temp;
    }


    public void eliminateFamily() {
        boolean[][] partOfFamily = new boolean[9][9];
        boolean somethingEliminated = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //right, left, down, up, right-left, up,down,
                partOfFamily[i][j] = (j < 7 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]) ||
                        (j > 1 && board[i][j] == board[i][j - 1] && board[i][j] == board[i][j - 2]) ||
                        (i < 7 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]) ||
                        (i > 1 && board[i][j] == board[i - 1][j] && board[i][j] == board[i - 2][j]) ||
                        (j > 0 && j < 8 && board[i][j] == board[i][j - 1] && board[i][j] == board[i][j + 1]) ||
                        (i > 0 && i < 8 && board[i][j] == board[i - 1][j] && board[i][j] == board[i + 1][j]);
            }
        }
        int count = 0;
        int max = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (partOfFamily[i][j]) {
                        board[i][j] = CandyTypes.BlackCandy;
                        somethingEliminated = true;
                        count++;
                        if(count > max) {
                            max = count;
                        }
                    }
                }
            }
            if(somethingEliminated)
            {
                shiftDown();
                eliminateFamily();
            }
        score += 10*max;
        }

    public int getScore(){
        return this.score;
    }
    public int getTurns(){
        return this.turns;
    }

    void terminate(){
        if(getScore() >= 100) {


            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }
    }
}


