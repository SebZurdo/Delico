import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Delico extends PApplet {


SoundFile file;
String audioname = "Astrix.mp3"; //Music setup
String path;
int Size;
int level,rows,out,dificulty;
PImage psyco; // Psyco-etris Image loaded as the font wasn't loadable from Processing
PFont weirdfont; // Processing-loadable font used for score and level
Screens multiboard;
Board main_board; //Creation of two boards, main is the playable one, while mini is the one where the other/next shape is shown
Board mini_board;
Shape fig;  //Creation of two shape classes to show next piece and print the shape into the board
Shape other;

int v_1 = 0;
int v_2 = 0; //Variables to make the background change into different colors when player reaches level 5
int v_3 = 0;

boolean over, start; //Boolean used to stop the game once the player has lost

public void setup() {
    start = true;
    path = sketchPath(audioname);
    file = new SoundFile(this, path); // Music setup
    file.play();
    psyco = loadImage("Psycoetris.png");
    weirdfont = loadFont("Pristina-Regular-48.vlw");
    strokeWeight(3);
    
    level = 1;
    main_board = new Board(20, 20, 255, 80);
    mini_board = new Board(6, 6, 255, 1120);
    other = new Shape(level);        
    fig = new Shape(level);
    multiboard = new Screens();
    fig.Moving = true;
    other.inject(mini_board, level); //Injects the "other" shape into the miniboard
}

public void draw() {
    if(level == 5 && PApplet.parseInt(random(1, 3)) % 2 == 0){  //Background changes at high rate when player reaches level 5
        v_1 = PApplet.parseInt(random(255));
        v_2 = PApplet.parseInt(random(255));
        v_3 = PApplet.parseInt(random(255));

        background(v_1 * 1, v_2 * 1, v_3 *1); 
    }
    else{
    background(v_1 * 1, v_2 * 1, v_3 *1);

    }  
    if(!start){
        main_board.display(rows, v_1, v_2, v_3);
        mini_board.display(6, 0, 0, 0);
        fig.GoDown(0);
        bottom();
        LevelToLimits(level);
        multiboard.showBoard();
        ScoreToLevels(main_board.points);
    }else{
        multiboard.startScreen();
    }
    if (over) {
        multiboard.showFinalGame();
    }
    

    

}


public void ScoreToLevels(int score){  //Converts the Board system points into Game system points
    if(score ==200){
        level = 2;
    }else if (score == 500) {
        level = 3;
    }else if (score ==800) {
        level = 4;
    }else if (score >= 1800) {
        level = 5;
    }
    if(level < 5){
        dificulty = level;
    }
}

public void keyPressed() { // The fig object updates the main_board matrix within its methods
    if(keyCode == RIGHT){
        fig.MoveShape("RIGHT", main_board);
    }
    if(keyCode == LEFT){
        fig.MoveShape("LEFT", main_board);
    }
    if(keyCode == DOWN){
        fig.MoveShape("DOWN", main_board);
    }
}
public void keyReleased() {

    if(start){
        start = false;
    }
    for(int i = 0; i < fig.blocks; ++i){ // Here updates its position like in the Moveshape method
        main_board.board_matrix[fig.ShapeD[i][1]][fig.ShapeD[i][0]] = 255;
    }
    
    if(keyCode == UP){
        try{    //Use of Try and Catch with some recursive functions to avoid the shape from going outside of the board and crashing the program
            fig.rotate();
            fig.rotate();
            for(int i = 0; i < fig.blocks; ++i){ // Here updates its position like in the Moveshape method
                main_board.board_matrix[fig.ShapeD[i][1]][fig.ShapeD[i][0]] = 255;
            }
        } catch (Exception e) {
            for (int i = 0; i < fig.blocks; ++i) {
                if(fig.ShapeD[i][1] > 15){
                    HandleSidesD();
                    break;
                }
            }
            for (int i = 0; i < fig.blocks; ++i) {
                if(fig.ShapeD[i][0] > PApplet.parseInt(rows/2)){
                    HandleSidesR();
                    break;
                }
            }
            for (int i = 0; i < fig.blocks; ++i) {
                if(fig.ShapeD[i][0] < PApplet.parseInt(rows/2)){
                    HandleSidesL();
                    break;
                }
            }
        }
        for (int i = 0; i < fig.blocks; ++i) {
            if(fig.ShapeD[i][0] > (rows-2)){
                    out++;
                }
        }
        for (int j = 0; j < out; ++j) {
            fig.extraMove("LEFT");
        }
        fig.rotcont++;
        out = 0;
    }

    
    for(int i = 0; i < fig.blocks; ++i){ //Updates the shape's position and injects it into the main board
        main_board.board_matrix[fig.ShapeD[i][1]][fig.ShapeD[i][0]] = fig.coloration;
    }

}
public void HandleSidesR(){ //Recursive function to move the shape when it goes over the right limit
    try{
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = 255;
        }
        fig.rotate();
        fig.rotate();
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = fig.coloration;
        }
    } catch (Exception e) {
        fig.extraMove("LEFT");
        HandleSidesR();
    }
}

public void HandleSidesL(){ //Recursive function to move the shape when it goes over the left limit
    try{
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = 255;
        }
        fig.rotate();
        fig.rotate();
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = fig.coloration;
        }
    } catch (Exception e) {
        fig.extraMove("RIGHT");
        HandleSidesL();
    }
}

public void HandleSidesD(){ //Recursive function to move the shape upwards when it rotates close to the bottom
    try{
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = 255;
        }
        fig.rotate();
        fig.rotate();
        for(int k = 0; k < fig.blocks; ++k){
            main_board.board_matrix[fig.ShapeD[k][1]][fig.ShapeD[k][0]] = fig.coloration;
        }
    } catch (Exception e) {
        fig.extraMove("UP");
        HandleSidesD();
    }
}

public void bottom(){
    if (!fig.Moving) {
        v_1 = PApplet.parseInt(random(255));
        v_2 = PApplet.parseInt(random(255));
        v_3 = PApplet.parseInt(random(255));

        gameover(fig, main_board, mini_board);
        fig = other;
        fig.Moving = true; //Shape stops moving downwards, so it turns into "other"/next piece and other recieves a new shape (and atattributes)
        other = new Shape(level);
        main_board.completed_lines(rows, level);
        mini_board.clean();
        other.inject(mini_board, level);

        
    }
}

public void LevelToLimits(int level){
    switch(level){
        case 1:
            rows = 4;
            break;
        case 2:
            rows = 6;
            break;
        case 3:
            rows = 8;
            break;
        case 4:
            rows = 10;
            break;
        case 5:
            rows = 20;
            break; 
    }
}

public void gameover(Shape fig, Board main_board, Board mini_board){
    for(int i = 0; i < fig.blocks; ++i){
        if(fig.ShapeD[i][1] == 0){
            background(0);

            for(int y_1 = 0; y_1 < main_board.y; ++y_1){
                for(int x_1 = 0; x_1 < main_board.x; ++x_1){
                    main_board.board_matrix[y_1][x_1] = 0;
                }
            }

            for(int y_2 = 0; y_2 < mini_board.y; ++y_2){
                for(int x_2 = 0; x_2 < main_board.x; ++x_2){
                    main_board.board_matrix[y_2][x_2] = 0;
                }
            }
            over = true;
            noLoop();

            // Aqui un ciclo infinito, si se presiona cierta tecla no se reiniciar el juego si quiere, si no pues solo tendría que borrar el rectangulo de puntaje y poner un letrero de puntaje final y sha
        }
    }
}


class Board{

    private int[][] board_matrix;
    private int board_color;
    private int y;
    private int x;
    private int block_size;
    private int space_x;
    private int points;

    Board(int matrix_lines, int matrix_columns, int color_b, int space) {
        board_matrix = new int[matrix_lines][matrix_columns];
        board_color = color_b;
        block_size = 40;
        y = matrix_lines;
        x = matrix_columns;
        space_x = space;
        points = 0;

        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                board_matrix[i][j] = board_color;   
            }
        }
    }

    public void display(int rows, int v_1, int v_2, int v_3){
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < rows; ++j){
                fill(board_matrix[i][j]);
                square(j * block_size + space_x, i * block_size + 80, block_size);
            }
        }

        for(int i = 0; i < y; ++i){
            for(int j = rows; j < x; ++j){
                push();
                noStroke();
                fill(v_1 * 1, v_2 * 1, v_3 * 1);
                square(j * block_size + space_x, i * block_size + 80, block_size);
                pop();
            }
        }
    }

    public void clean(){
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                board_matrix[i][j] = board_color;
            }
        }
    }

    public void completed_lines(int limit, int level){
        int block_color; // Variable that stores the color of the initial block of a line
        int completed_lines = 0;
        boolean completed_line = true;

        int[] lines = new int[20]; // Array that stores the completed lines
        int auxiliar_index = 0;

        for(int i = 0; i < 20; ++i){
            block_color = board_matrix[i][0];

            if(block_color != board_color)
            {
                for(int j = 1; j < limit; ++j){
                    if(board_matrix[i][j] == board_color)
                    {
                        completed_line = false;
                        break;
                    }
                }

                if(completed_line){
                    lines[auxiliar_index] = i;
                    ++completed_lines;
                    ++auxiliar_index; 
                }
            }

            completed_line = true;

        }

        auxiliar_index = 0;

        for(int i = 0; i < completed_lines; ++i){
            for(int j = 0; j < limit; ++j){
                board_matrix[lines[i]][j] = board_color;
            }
        }

        points += 100 * completed_lines;
        after_line_complete(lines, completed_lines, limit); 

        if(level == 5 && completed_lines > 0){
            dificulty += 1;
        }
    }

    public void after_line_complete(int[] lines, int completed_lines, int limit){ // Makes that everything falls again after a line or lines disappear
        int first_line = lines[0];

        for(int i = 1; i < completed_lines; ++i){
             if(lines[i] < first_line){
                first_line = lines[i];
            }
        }

        for(int i = first_line - 1; i >= 0; --i){
            for(int j = 0; j < limit; ++j){
                board_matrix[i + completed_lines][j] = board_matrix[i][j];
                board_matrix[i][j] = board_color;
            }
        }

    }

}
class Shape{   //Poly
    //Monominoe//
    private int[][] M0 = {{0,0}};
    //Binominoe//
    private int[][] MR = {{0,0},{0,1}};
    //Triminoes//
    private int[][] C0 = {{0,0},{1,0},{0,1}};
    private int[][] R0 = {{0,0},{0,1},{0,2}};
    //Tetronimoes//
    private int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
    private int[][] line = {{0,0},{1,0},{2,0},{3,0},};
    private int[][] treh = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] eleL = {{0,0},{0,1},{1,0},{2,0}};
    private int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] S1 = {{0,0},{1,0},{1,1},{2,1}};
    private int[][] S2 = {{0,1},{1,1},{1,0},{2,0}};
    //Pentominoes//
    private int[][] F1 = {{1,0},{2,0},{0,1},{1,1},{1,2}};
    private int[][] F2 = {{0,0},{1,0},{1,1},{2,1},{1,2}};
    private int[][] line2 = {{0,0},{1,0},{2,0},{3,0},{4,0}};
    private int[][] L1 = {{0,0},{0,1},{0,2},{0,3},{1,3}};
    private int[][] L2 = {{0,3},{1,3},{1,2},{1,1},{1,0}};
    private int[][] N1 = {{1,0},{1,1},{0,1},{0,2},{0,3}};
    private int[][] N2 = {{0,0},{0,1},{1,1},{1,2},{1,3}};
    private int[][] P1 = {{1,0},{0,1},{1,1},{0,2},{1,2}};
    private int[][] P2 = {{0,0},{0,1},{1,1},{0,2},{1,2}};
    private int[][] T0 = {{0,0},{1,0},{2,0},{1,1},{1,2}};
    private int[][] U0 = {{0,0},{2,0},{0,1},{1,1},{2,1}};
    private int[][] V0 = {{0,0},{0,1},{0,2},{1,2},{2,2}};
    private int[][] W0 = {{0,0},{0,1},{1,1},{1,2},{2,2}};
    private int[][] X0 = {{1,0},{0,1},{1,1},{2,1},{1,2}};
    private int[][] Y1 = {{1,0},{0,1},{1,1},{1,2},{1,3}};
    private int[][] Y2 = {{0,0},{0,1},{1,1},{0,2},{0,3}};
    private int[][] Z1 = {{0,0},{1,0},{1,1},{1,2},{2,2}};
    private int[][] Z2 = {{1,0},{2,0},{1,1},{1,2},{0,2}};
    
    private int[][] ShapeD, OS;
    private int coloration, order;
    private boolean Moving;
    private float Size;
    private int cont, rotcont;
    private int limit;
    private int blocks;
    public Shape(int lvl){ //Constructor recieves level, putting a limit on wich kind of Polyominoes can spawn
        switch(level){
            case 1:
                limit = 1;
                break;
            case 2:
                limit = 2;
                break;
            case 3:
                limit = 4;
                break;
            case 4:
                limit = 11;
                break;
            case 5:
                limit = 28;
                break;
        }
        Size = 40;
        order = (int)random(0,limit);
        switch(order){  //Based on the level, this switch is restricted so board dimensions are proportional to polyominoes' blocks
            case 0:
                ShapeD = M0;
                coloration = 0xff13FF1C;
                break;
            case 1:
                ShapeD = MR;
                coloration = 0xffd98cb3;
                break;
            case 2:
                ShapeD = C0;
                coloration = 0xffd1b3ff;
                break;
            case 3:
                ShapeD = R0;
                coloration = 0xffffb3d9;
                break;
            case 4:
                ShapeD = drado;
                coloration = 0xff80aaff;
                break;
            case 5:
                ShapeD = line;
                coloration = 0xffe62e00;
                break;
            case 6:
                ShapeD = treh;
                coloration = 0xffe67300;
                break;
            case 7:
                ShapeD = eleL;
                coloration = 0xff66c285;
                break;
            case 8:
                ShapeD = eleD;
                coloration = 0xff595454;
                break;
            case 9:
                ShapeD = S1;
                coloration = 0xff00cc00;
                break;
            case 10:
                ShapeD = S2;
                coloration = 0xffb3e6ff;
                break;
            case 11:
                ShapeD = F1;
                coloration = 0xff00cccc;
                break;
            case 12:
                ShapeD = F2;
                coloration = 0xffff8000;
                break;
            case 13:
                ShapeD = line2;
                coloration = 0xff1a1aff;
                break;
            case 14:
                ShapeD = L1;
                coloration = 0xffffff00;
                break;
            case 15:
                ShapeD = L2;
                coloration = 0xff80ffcc;
                break;
            case 16:
                ShapeD = N1;
                coloration = 0xff33cccc;
                break;
            case 17:
                ShapeD = N2;
                coloration = 0xff990099;
                break;
            case 18:
                ShapeD = P1;
                coloration = 0xffff99c2;
                break;
            case 19:
                ShapeD = P2;
                coloration = 0xff804000;
                break;
            case 20:
                ShapeD = T0;
                coloration = 0xffa3a375;
                break;
            case 21:
                ShapeD = U0;
                coloration = 0xff80ff80;
                break;
            case 22:
                ShapeD = V0;
                coloration = 0xffdf9fdf;
                break;
            case 23:
                ShapeD = W0;
                coloration = 0xffff1a1a;
                break;
            case 24:
                ShapeD = X0;
                coloration = 0xffcc66ff;
                break;
            case 25:
                ShapeD = Y1;
                coloration = 0xff4d9900;
                break;
            case 26:
                ShapeD = Y2;
                coloration = 0xffcc3300;
                break;
            case 27:
                ShapeD = Z1;
                coloration = 0xffbf8040;
                break;
            case 28:
                ShapeD = Z2;
                coloration = 0xff459eab;
                break;
        }
        if(order == 0){
            blocks = 1;;
        }else if (order ==1 ) {
            blocks = 2;
        }else if (order > 1 && order <= 3) {
            blocks = 3;
        }else if (order > 3 && order <= 10) {
            blocks = 4;
        }else{
            blocks = 5;    //Depending on the restricted switch and the polyominoe selected, block number is taken in order to operate with the polyominoe
        }
        cont = 1;
        OS = ShapeD;
        rotcont = 1;
    }

    public void rotate(){
        if (ShapeD != drado && ShapeD != X0 && ShapeD != M0) {  //Rotation works in the same way as rotating a figure in the Cartesian coordinate system, (changing axises from their place and changing their signs)
            int[][] rotated = new int[blocks][2];
            if (rotcont % 4 == 0) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OS[i][0] - ShapeD[1][0];
                    rotated[i][1] = OS[i][1] - ShapeD[1][1];
                }
            }else if (rotcont % 4 == 1) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OS[i][1] - ShapeD[1][0];
                    rotated[i][1] = OS[i][0] - ShapeD[1][1];
                }
            }else if (rotcont % 4 == 2) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OS[i][0] - ShapeD[1][0];
                    rotated[i][1] = -OS[i][1] - ShapeD[1][1];
                }
            
            }else if (rotcont % 4 == 3) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OS[i][1] - ShapeD[1][0];
                    rotated[i][1] = -OS[i][0] - ShapeD[1][1];
                }
            }
            ShapeD = rotated;
        }
    }

    public void GoDown(int level){  //Function that makes the shape go down constantly, it depends on the variable dificulty wich grows depending on the lines that are completed
        if(cont%(55-dificulty) == 0){ //This is used instead of a "delay" function to avoid timing conflicts
            MoveShape("DOWN", main_board);
        }
        cont++;
    }

    public boolean Limit(String dir){   //Function that detects the limits on where the shape can move, left and down are always the same while right one changes depending on the main board's row number
        switch(dir){
            case "RIGHT":
                for (int i = 0; i < blocks; ++i) {
                    if(ShapeD[i][0]> rows-2){
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
                        Moving = false;
                        return false;
                    }
                }
                break;
        }
        return true;
    }
    public void MoveShape(String dir, Board main_board){  //Function that moves the Shape and instantly injects it's position on the main board
        for(int i = 0; i < blocks; ++i){ // Erases its previous position in the main_board
            main_board.board_matrix[ShapeD[i][1]][ShapeD[i][0]] = 255;
        }

        if(Limit(dir) && colitions(dir, main_board)){
            if(dir == "RIGHT"){  //Right
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]++;  
                }
            }
            if(dir == "LEFT"){ //Left
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]--;  
                }
            }
            if(dir == "DOWN"){ //Down
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][1]++;  
                }
            }
        }

        for(int i = 0; i < blocks; ++i){ // Updates its current position in the main_board
            main_board.board_matrix[ShapeD[i][1]][ShapeD[i][0]] = coloration;
        }
    }
    public void extraMove(String dir){ //Function that moves the shape but doesn't injects it's position on the main board
        if(dir == "UP"){               //This is used to move the shape while it's outside of the matrix limits after a rotation
            for (int i = 0; i < blocks; ++i) {
                ShapeD[i][1]--;  //UP
            }
        }
        if(Limit(dir)){
            if(dir == "RIGHT"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]++;  //Right
                }
            }
            if(dir == "LEFT"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][0]--;  //Left
                }
            }
            if(dir == "DOWN"){
                for (int i = 0; i < blocks; ++i) {
                    ShapeD[i][1]++;  //Down
                }
            }
        }
    }

    public boolean colitions(String dir, Board main_board){ //Function that detects collitions depending on the shape's surrounding
        int x_1; // Auxiliar variables               //Works thanks to the shape's positioning being constantly injected on the matrix
        int y_1;

        if(dir == "DOWN"){
            for(int i = 0; i < blocks; ++i){
                x_1 = ShapeD[i][0];
                y_1 = ShapeD[i][1];

                if(main_board.board_matrix[y_1 + 1][x_1] != 255 && not_in(x_1, y_1 + 1)){
                    Moving = false;
                    return false;
                }
            }
        }

        if(dir == "RIGHT"){
            for(int i = 0; i < blocks; ++i){
                x_1 = ShapeD[i][0];
                y_1 = ShapeD[i][1];

                if(main_board.board_matrix[y_1][x_1 + 1] != 255 && not_in(x_1 + 1, y_1)){
                    return false;
                }
            }
        }

        if(dir == "LEFT"){
            for(int i = 0; i < blocks; ++i){
                x_1 = ShapeD[i][0];
                y_1 = ShapeD[i][1];

                if(main_board.board_matrix[y_1][x_1 - 1] != 255 && not_in(x_1 - 1, y_1)){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean not_in(int x, int y){ // Method checks if the block in the parameter is a block from the fig
        for(int i = 0; i < blocks; ++i){
            if(x == ShapeD[i][0] && y == ShapeD[i][1]){
                return false;
            }
        }

        return true;
    }

    public void inject(Board mini_board, int h){ // Method used to show the fig in the mini_board
        if(h < 4){
            for(int i = 0; i < blocks; ++i){
                mini_board.board_matrix[ShapeD[i][1] + 2][ShapeD[i][0] + 2] = coloration;
            }
        }
        else{
            for(int i = 0; i < blocks; ++i){
                mini_board.board_matrix[ShapeD[i][1] + 1][ShapeD[i][0] + 1] = coloration;
            }
        }   
        
        
    }

}

class Screens{
    private int bigfont, smallfont;
    private int score;

    public Screens(){}

    public void startScreen(){
        rect(160,160,1280,640);
        image(psyco, 320, 360, 460, 200);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Welcome!",960,440);
        text("Press any key to play",960,500);
        pop();

    }

    public void showBoard(){
        rect(1120,400,240,360);
        image(psyco, 1125, 400, 230, 100);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Score: "+str(main_board.points),1130,540);
        text("Level: "+str(dificulty),1130,680);
        pop();
    }

    public void showFinalGame(){
        rect(160,160,1280,640);
        image(psyco, 320, 360, 460, 200);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Final Score: "+str(main_board.points-2000)+" !",960,440);
        text("Thanks for playing!",960,500);
        pop();
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
