import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Delico extends PApplet {

int M;
int n;
int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
Board main_board;
Board mini_board;
Shape fig;
public void setup() {
    
    n = 15;
    main_board = new Board(20, n, 255, 80);
    mini_board = new Board(5, 5, 255, 1120);
    fig = new Shape();
}

public void draw() {
    background(140);
    main_board.display();
    mini_board.display();
    fig.ShowShape();
    fig.GoDown(0);
    textSize(20);
    push();
    fill(0);
    text("Bloque 1",1000,400);
    text("x"+str(fig.ShapeD[0][0]),1100,400);
    text("y"+str(fig.ShapeD[0][1]),1150,400);
    text("Bloque 2",1000,500);
    text("x"+str(fig.ShapeD[1][0]),1100,500);
    text("y"+str(fig.ShapeD[1][1]),1150,500);
    text("Bloque 3",1000,600);
    text("x"+str(fig.ShapeD[2][0]),1100,600);
    text("y"+str(fig.ShapeD[2][1]),1150,600);
    text("Bloque 4",1000,700);
    text("x"+str(fig.ShapeD[3][0]),1100,700);
    text("y"+str(fig.ShapeD[3][1]),1150,700);
    pop();
}

public void keyPressed() {
    if(keyCode == RIGHT){
        fig.MoveShape("RIGHT");
    }
    if(keyCode == LEFT){
        fig.MoveShape("LEFT");
    }
    if(keyCode == DOWN){
        fig.MoveShape("DOWN");
    }
}
public void keyReleased() {
    if(keyCode == UP){
        fig.rotate();
        fig.rotate();
    }
    fig.rotcont++;
}

class Board{

    private int[][] board_matrix;
    private int board_color;
    private int y;
    private int x;
    private int block_size;
    private int space_x;

    Board(int matrix_lines, int matrix_columns, int color_b, int space) {
        board_matrix = new int[matrix_lines][matrix_columns];
        board_color = color_b;
        block_size = 40;
        y = matrix_lines;
        x = matrix_columns;
        space_x = space;

        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                board_matrix[i][j] = board_color; 
            }
        }
    }

    public void display(){
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                fill(board_matrix[i][j]);
                square(j * block_size + space_x, i * block_size + 80, block_size);
            }
        }
    }


}
class Shape{
    //Monomino//
    private int[][] M0 = {{0,0}};
    //Binomino//
    private int[][] MR = {{0,0},{0,1}};
    //Triminos//
    private int[][] C0 = {{0,0},{1,0},{0,1}};
    private int[][] R0 = {{0,0},{0,1},{0,2}};
    //Tetronimos//
    private int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
    private int[][] line = {{0,0},{1,0},{2,0},{3,0},};
    private int[][] treh = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] eleL = {{0,0},{0,1},{1,0},{2,0}};
    private int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] S1 = {{0,0},{1,0},{1,1},{2,1}};
    private int[][] S2 = {{0,1},{1,1},{1,0},{2,0}};
    //Pentominos//
    private int[][] F1 = {{1,0},{2,0},{0,1},{1,1},{1,2}};
    private int[][] F2 = {{0,0},{1,0},{1,1},{2,1},{1,2}};
    private int[][] line2 = {{0,0},{1,0},{2,0},{3,0},{4,0}};
    private int[][] L1 = {{0,0},{0,1},{0,2},{0,3},{1,3}};
    private int[][] L2 = {{0,3},{1,0},{1,1},{1,2},{1,3}};
    private int[][] N1 = {{1,0},{1,1},{0,1},{0,2},{0,3}};
    private int[][] N2 = {{0,0},{0,1},{1,1},{1,2},{1,3}};
    private int[][] P1 = {{1,0},{0,1},{1,1},{0,2},{1,2}};
    private int[][] P2 = {{0,0},{0,1},{1,1},{0,2},{1,2}};
    private int[][] T0 = {{0,0},{1,0},{2,0},{1,1},{1,2}};
    private int[][] U0 = {{0,0},{2,0},{0,1},{1,1},{2,1}};
    private int[][] V0 = {{0,0},{0,1},{0,2},{1,2},{2,2}};
    private int[][] W1 = {{0,0},{0,1},{1,1},{1,2},{2,2}};
    private int[][] W2 = {{0,2},{1,2},{1,1},{2,1},{2,0}};
    private int[][] X0 = {{1,0},{0,1},{1,1},{2,1},{1,2}};
    private int[][] Y1 = {{1,0},{0,1},{1,1},{1,2},{1,3}};
    private int[][] Y2 = {{0,0},{0,1},{1,1},{0,2},{0,3}};
    private int[][] Z0 = {{0,0},{1,0},{1,1},{1,2},{2,2}};
    //
    private int[][] ShapeD, OS;
    private int coloration, order,blocks;
    private boolean Moving;
    private float M;
    private int cont, rotcont;
    public Shape(){
        M = 40;

        order = (int)random(6);
        switch(order){
            case 0:
                ShapeD = drado;
                coloration = 0xff80aaff;
                blocks = 4;
                break;
            case 1:
                ShapeD = line;
                coloration = 0xffe62e00;
                blocks = 4;
                break;
            case 2:
                ShapeD = treh;
                coloration = 0xffe67300;
                blocks = 4;
                break;
            case 3:
                ShapeD = eleL;
                coloration = 0xff999999;
                blocks = 4;
                break;
            case 4:
                ShapeD = eleD;
                coloration = 0xffe6e6e6;
                blocks = 4;
                break;
            case 5:
                ShapeD = S1;
                coloration = 0xff00cc00;
                blocks = 4;
                break;
            case 6:
                ShapeD = S2;
                coloration = 0xffb3e6ff;
                blocks = 4;
                break;
        }
        cont = 1;
        ShapeD = W1;
        OS = ShapeD;
        blocks = 5;
        rotcont = 0;
    }
    public void rotate(){
        if (ShapeD != drado) {
            int[][] rotated = new int[blocks][2];
            if (rotcont % 4 == 0) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OS[i][1] - ShapeD[1][0];
                    rotated[i][1] = OS[i][0] - ShapeD[1][1];
                }
            }else if (rotcont % 4 == 1) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OS[i][0] - ShapeD[1][0];
                    rotated[i][1] = -OS[i][1] - ShapeD[1][1];
                }
            }else if (rotcont % 4 == 2) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OS[i][1] - ShapeD[1][0];
                    rotated[i][1] = -OS[i][0] - ShapeD[1][1];
                }
            
            }else if (rotcont % 4 == 3) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OS[i][0] - ShapeD[1][0];
                    rotated[i][1] = OS[i][1] - ShapeD[1][1];
                }
            }
            ShapeD = rotated;
        }
    }
    public void ShowShape(){
        fill(coloration);
        for (int i = 0; i < blocks; i++) {
            rect((ShapeD[i][0]*M)+80, (ShapeD[i][1]*M)+80,M,M);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //FUNCIÓN PARA IR HACIA DOWN CONSTANTEMENTE
    //-----------------------------------------------------------------------------------------------------------------------------
    public void GoDown(int nivel){
        if(cont%(55-nivel) == 0){
            MoveShape("DOWN");
        }
        cont++;
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //BOOLEANO PARA CONTROLAR LOS LÍMITES DE LA CUADRÍCULA
    //-----------------------------------------------------------------------------------------------------------------------------
    public boolean Limit(String dir){
        switch(dir){
            case "RIGHT":
                for (int i = 0; i < blocks; ++i) {
                    if(ShapeD[i][0]>(n-2)){
                        return false;
                    }
                }
                break;
            case "LEFT":
                for (int i = 0; i < blocks; ++i) {
                    if(ShapeD[i][0]<1){
                        return false;
                    }
                }
                break;
            case "DOWN":
                for (int i = 0; i < blocks; ++i) {
                    if(ShapeD[i][1]>18){
                        return false;
                    }
                }
                break;
        }
        return true;
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //MOVIMIENTOS LATERALES Y HACIA DOWN CONTROLADOS
    //-----------------------------------------------------------------------------------------------------------------------------
    public void MoveShape(String dir){
        if(Limit(dir)){
            if(dir == "RIGHT"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]++;  //Dereiaaaaa
                }
            }
            if(dir == "LEFT"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]--;  //Izquierdaaaaaa
                }
            }
            if(dir == "DOWN"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][1]++;  //Abajoooooo
                }
            }
        }
    }

}
  public void settings() {  size(1600,960); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Delico" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
