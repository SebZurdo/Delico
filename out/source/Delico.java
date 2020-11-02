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
int[][] drado = {{1,1},{2,1},{1,2},{2,2}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
public void setup() {
    
}

public void draw() {
    n = 4;
    nivel(n);
    matrx();
    for (int i = 0; i < 4; ++i) {
        square((drado[i][0]*40)+80,(drado[i][1]*40)+80,40);
    }
}
public void matrx(){
    for (int i = 0; i < 21; ++i) {
        line(80, 80+(40*i), 80+(40*M), 80+(40*i));
    }
    for (int i = 0; i < (M+1); ++i) {
        line(80+(40*i), 80, 80+(40*i), 880);
    }
}
public void nivel(int n){
    switch(n){
        case 1:
            M = 4;
            break;
        case 2:
            M = 6;
            break;
        case 3:
            M = 8;
            break;
        case 4:
            M = 10;
            break;
        case 5:
            M = 12;
            break;
        case 6:
            M = 14;
            break;
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
