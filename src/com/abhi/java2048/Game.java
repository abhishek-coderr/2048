package com.abhi.java2048;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int[][] gameboard;
    private Random r=new Random();
    private GameState state;
    private int Score;

    public Game() {
        gameboard = new int[4][4];
        addNewNumber();
        addNewNumber();
        state=GameState.CONTINUE;
    }

    public int[][] getGameBoard(){
        return gameboard;
    }
    public GameState getState(){
        return state;
    }
    public int getScore(){
        return Score;
    }

    public void printArray() {
        for (int[] x : gameboard) {
            System.out.format("%6d%6d%6d%6d%n", x[0], x[1], x[2], x[3]);
        }
        System.out.format("%n");
    }

    public void addNewNumber(){
        if (checkBoardFull()){
            return;
        }
        ArrayList <Integer> emptySpacesX= new ArrayList<>();
        ArrayList <Integer> emptySpacesY= new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if(gameboard[x][y]==0){
                    emptySpacesX.add(new Integer(x));
                    emptySpacesY.add(new Integer(y));
                }
            }
        }
        int choice=r.nextInt(emptySpacesX.size());
        int numberChooser=r.nextInt(10); // val 0-9
        int newNumber=2;
        if (numberChooser==0) {
            newNumber=4;
        }
        int X = emptySpacesX.get(choice);
        int Y = emptySpacesY.get(choice);
        gameboard[X][Y]=newNumber;
    }

    public void pushUP(){
        System.out.println("pushing up ...");
        
        for (int y = 0; y < 4; y++) {
            boolean[] alreadyCombined={false, false, false, false};
            for (int x = 1; x < 4; x++) {
                if( gameboard[x][y]!=0){
                    int value= gameboard[x][y];
                    int X=x-1;
                    while ((X>=0)&&(gameboard[X][y]==0)) {
                        X--;
                    }
                    if(X==-1){
                        gameboard[0][y]=value;
                        gameboard[x][y]=0;
                    }
                    else if(gameboard[X][y]!=value){
                        gameboard[x][y]=0;
                        gameboard[X+1][y]=value;
                        
                    }
                    else{
                        if(alreadyCombined[X]){
                            gameboard[x][y]=0;
                            gameboard[X+1][y]=value;
                            
                        }
                        else{ //herr im combining 2 nums
                            gameboard[x][y]=0;
                            gameboard[X][y]*=2;
                            Score+=gameboard[X][y];
                            alreadyCombined[X]=true;
                            
                        }
                    }
                }
            }
        }
    }
    public boolean checkFor2048(){ // return true if 2048 occurs
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (gameboard[x][y]==2048){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkBoardFull(){ //return true if board is full
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (gameboard[x][y]==0){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkHasMoves(){ // return true if there are any adjacent numbers
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (x==0) {
                    if (y!=0){
                        if (gameboard[x][y]==gameboard[x][y-1]){
                            return true;
                        }
                    }
                }
                else{
                    if (y!=0){
                        if (gameboard[x][y]==gameboard[x][y-1]){
                            return true;
                        }
                    }
                    if(gameboard[x][y]==gameboard[x-1][y]){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void checkState(){
        if(checkFor2048()){
            state=GameState.WIN;
        }
        else if(checkBoardFull()){
            if(checkHasMoves()){
                state=GameState.CONTINUE;
            }
            else{
                state=GameState.LOSE;
            }
        }
        else{
            state=GameState.CONTINUE;
        }
        
    }


    public void pushDOWN(){
        System.out.println("pushing down ...");
        
        for (int y = 0; y < 4; y++) {
            boolean[] alreadyCombined={false, false, false, false};
            for (int x = 2; x > -1; x--) {
                if( gameboard[x][y]!=0){
                    int value= gameboard[x][y];
                    int X=x+1;
                    while ((X<=3)&&(gameboard[X][y]==0)) {
                        X++;
                    }
                    if(X==4){
                        gameboard[3][y]=value;
                        gameboard[x][y]=0;
                    }
                    else if(gameboard[X][y]!=value){
                        gameboard[x][y]=0;
                        gameboard[X-1][y]=value;
                        
                    }
                    else{
                        if(alreadyCombined[X]){
                            gameboard[x][y]=0;
                            gameboard[X-1][y]=value;
                            
                        }
                        else{
                            gameboard[x][y]=0;
                            gameboard[X][y]*=2;
                            Score+=gameboard[X][y];
                            alreadyCombined[X]=true;
                            
                        }
                    }
                }
            }
        }
    }


    public void pushLEFT(){
        System.out.println("pushing left ...");
        
        for (int x = 0; x < 4; x++) {
            boolean[] alreadyCombined={false, false, false, false};
            for (int y = 1; y < 4; y++) {
                if( gameboard[x][y]!=0){
                    int value= gameboard[x][y];
                    int Y=y-1;
                    while ((Y>=0)&&(gameboard[x][Y]==0)) {
                        Y--;
                    }
                    if(Y==-1){
                        gameboard[x][0]=value;
                        gameboard[x][y]=0;
                    }
                    else if(gameboard[x][Y]!=value){
                        gameboard[x][y]=0;
                        gameboard[x][Y+1]=value;
                        
                    }
                    else{
                        if(alreadyCombined[Y]){
                            gameboard[x][y]=0;
                            gameboard[x][Y+1]=value;
                            
                        }
                        else{
                            gameboard[x][y]=0;
                            gameboard[x][Y]*=2;
                            Score+=gameboard[x][Y];
                            alreadyCombined[Y]=true;
                            
                        }
                    }
                }
            }
        }
    }
    
    
    public void pushRIGHT(){
        System.out.println("pushing right ...");
        
        for (int x = 0; x < 4; x++) {
            boolean[] alreadyCombined={false, false, false, false};
            for (int y = 2; y > -1; y--) {
                if( gameboard[x][y]!=0){
                    int value= gameboard[x][y];
                    int Y=y+1;
                    while ((Y<=3)&&(gameboard[x][Y]==0)) {
                        Y++;
                    }
                    if(Y==4){
                        gameboard[x][3]=value;
                        gameboard[x][y]=0;
                    }
                    else if(gameboard[x][Y]!=value){
                        gameboard[x][y]=0;
                        gameboard[x][Y-1]=value;
                        
                    }
                    else{
                        if(alreadyCombined[Y]){
                            gameboard[x][y]=0;
                            gameboard[x][Y-1]=value;
                            
                        }
                        else{
                            gameboard[x][y]=0;
                            gameboard[x][Y]*=2;
                            Score+=gameboard[x][Y];
                            alreadyCombined[Y]=true;
                            
                        }
                    }
                }
            }
        }
    }    


}
 