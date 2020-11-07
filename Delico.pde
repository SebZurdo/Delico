int Size;
int n, level;
int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
Board main_board;
Board mini_board;
Shape fig;
Shape other;
void setup() {
    size(1600,960);
    n = 20;
    main_board = new Board(20, n, 255, 80);
    mini_board = new Board(5, 5, 255, 1120);
    other = new Shape(5);
    fig = new Shape(5);
    fig.Moving = true;
}

void draw() {
    background(140);
    main_board.display();
    mini_board.display();
    fig.GoDown(0);
    bottom();
}

void keyPressed() { // The fig object updates the main_board matrix within its methods
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
void keyReleased() {
    for(int i = 0; i < fig.blocks; ++i){ // Here updates its position like in the Moveshape method
        main_board.board_matrix[fig.ShapeD[i][1]][fig.ShapeD[i][0]] = 255;
    }

    if(keyCode == UP){
        fig.rotate();
        fig.rotate();
    }
    fig.rotcont++;

    for(int i = 0; i < fig.blocks; ++i){
        main_board.board_matrix[fig.ShapeD[i][1]][fig.ShapeD[i][0]] = fig.coloration;
    }
}

void bottom(){
    if (!fig.Moving) {
        fig = other;
        fig.Moving = true;
        other = new Shape(5);
    }
}
class Board{

    private color[][] board_matrix;
    private color board_color;
    private int y;
    private int x;
    private int block_size;
    private int space_x;

    Board(int matrix_lines, int matrix_columns, color color_b, int space) {
        board_matrix = new color[matrix_lines][matrix_columns];
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

    void display(){
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
    //
    private int[][] ShapeD, OS;
    private int coloration, order;
    private boolean Moving;
    private float Size;
    private int cont, rotcont;
    private int limit;
    private int blocks;
    public Shape(int level){
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
        order = (int)random(limit);
        switch(order){
            case 0:
                ShapeD = M0;
                coloration = #000000;
                break;
            case 1:
                ShapeD = MR;
                coloration = #d98cb3;
                break;
            case 2:
                ShapeD = C0;
                coloration = #d1b3ff;
                break;
            case 3:
                ShapeD = R0;
                coloration = #ffb3d9;
                break;
            case 4:
                ShapeD = drado;
                coloration = #80aaff;
                break;
            case 5:
                ShapeD = line;
                coloration = #e62e00;
                break;
            case 6:
                ShapeD = treh;
                coloration = #e67300;
                break;
            case 7:
                ShapeD = eleL;
                coloration = #999999;
                break;
            case 8:
                ShapeD = eleD;
                coloration = #e6e6e6;
                break;
            case 9:
                ShapeD = S1;
                coloration = #00cc00;
                break;
            case 10:
                ShapeD = S2;
                coloration = #b3e6ff;
                break;
            case 11:
                ShapeD = F1;
                coloration = #00cccc;
                break;
            case 12:
                ShapeD = F2;
                coloration = #ff8000;
                break;
            case 13:
                ShapeD = line2;
                coloration = #1a1aff;
                break;
            case 14:
                ShapeD = L1;
                coloration = #ffff00;
                break;
            case 15:
                ShapeD = L2;
                coloration = #80ffcc;
                break;
            case 16:
                ShapeD = N1;
                coloration = #33cccc;
                break;
            case 17:
                ShapeD = N2;
                coloration = #990099;
                break;
            case 18:
                ShapeD = P1;
                coloration = #ff99c2;
                break;
            case 19:
                ShapeD = P2;
                coloration = #804000;
                break;
            case 20:
                ShapeD = T0;
                coloration = #a3a375;
                break;
            case 21:
                ShapeD = U0;
                coloration = #80ff80;
                break;
            case 22:
                ShapeD = V0;
                coloration = #df9fdf;
                break;
            case 23:
                ShapeD = W0;
                coloration = #ff1a1a;
                break;
            case 24:
                ShapeD = X0;
                coloration = #cc66ff;
                break;
            case 25:
                ShapeD = Y1;
                coloration = #4d9900;
                break;
            case 26:
                ShapeD = Y2;
                coloration = #cc3300;
                break;
            case 27:
                ShapeD = Z1;
                coloration = #bf8040;
                break;
            case 28:
                ShapeD = Z2;
                coloration = #459eab;
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
            blocks = 5;
        }
        cont = 1;
        OS = ShapeD;
        rotcont = 0;
    }
    public void rotate(){
        if (ShapeD != drado && ShapeD != X0 && ShapeD != M0) {
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
            rect((ShapeD[i][0]*Size)+80, (ShapeD[i][1]*Size)+80,Size,Size);
        }
    }
    public void GoDown(int level){
        if(cont%(55-level) == 0){
            MoveShape("DOWN", main_board);
        }
        cont++;
    }
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
                        Moving = false;
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
    public void MoveShape(String dir, Board main_board){
        for(int i = 0; i < blocks; ++i){ // Erases its previous position in the main_board
            main_board.board_matrix[ShapeD[i][1]][ShapeD[i][0]] = 255;
        }

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

        for(int i = 0; i < blocks; ++i){ // Updates its current position in the main_board
            main_board.board_matrix[ShapeD[i][1]][ShapeD[i][0]] = coloration;
        }
    }

}