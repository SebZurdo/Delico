int M;
int n;
int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
Board main_board;
Board mini_board;
Forma fig;
void setup() {
    size(1600,960);
    n = 15;
    main_board = new Board(20, n, 255, 80);
    mini_board = new Board(5, 5, 255, 1120);
    fig = new Forma();
}

void draw() {
    background(140);
    main_board.display();
    mini_board.display();
    fig.mostrarF();
    fig.pabajo(0);
    textSize(20);
    push();
    fill(0);
    text("Bloque 1",1000,400);
    text("x"+str(fig.FormaD[0][0]),1100,400);
    text("y"+str(fig.FormaD[0][1]),1150,400);
    text("Bloque 2",1000,500);
    text("x"+str(fig.FormaD[1][0]),1100,500);
    text("y"+str(fig.FormaD[1][1]),1150,500);
    text("Bloque 3",1000,600);
    text("x"+str(fig.FormaD[2][0]),1100,600);
    text("y"+str(fig.FormaD[2][1]),1150,600);
    text("Bloque 4",1000,700);
    text("x"+str(fig.FormaD[3][0]),1100,700);
    text("y"+str(fig.FormaD[3][1]),1150,700);
    pop();
}

void keyPressed() {
    if(keyCode == RIGHT){
        fig.mover("DER");
    }
    if(keyCode == LEFT){
        fig.mover("IZQ");
    }
    if(keyCode == DOWN){
        fig.mover("ABAJO");
    }
}
void keyReleased() {
    if(keyCode == UP){
        fig.rota();
        fig.rota();
    }
    fig.rotcont++;
}

mousePressed


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
class Forma{
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
    private int[][] FormaD, OF;
    private int coloration, orden,blocks;
    private boolean EnMovi;
    private float M;
    private int cont, rotcont;
    public Forma(){
        M = 40;
        orden = (int)random(6);
        switch(orden){
            case 0:
                FormaD = drado;
                coloration = #80aaff;
                blocks = 4;
                break;
            case 1:
                FormaD = line;
                coloration = #e62e00;
                blocks = 4;
                break;
            case 2:
                FormaD = treh;
                coloration = #e67300;
                blocks = 4;
                break;
            case 3:
                FormaD = eleL;
                coloration = #999999;
                blocks = 4;
                break;
            case 4:
                FormaD = eleD;
                coloration = #e6e6e6;
                blocks = 4;
                break;
            case 5:
                FormaD = S1;
                coloration = #00cc00;
                blocks = 4;
                break;
            case 6:
                FormaD = S2;
                coloration = #b3e6ff;
                blocks = 4;
                break;
        }
        cont = 1;
        OF = FormaD;
        rotcont = 0;
    }
    public void rota(){
        if (FormaD != drado) {
            int[][] rotated = new int[blocks][2];
            if (rotcont % 4 == 0) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OF[i][1] - FormaD[1][0];
                    rotated[i][1] = OF[i][0] - FormaD[1][1];
                }
            }else if (rotcont % 4 == 1) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = -OF[i][0] - FormaD[1][0];
                    rotated[i][1] = -OF[i][1] - FormaD[1][1];
                }
            }else if (rotcont % 4 == 2) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OF[i][1] - FormaD[1][0];
                    rotated[i][1] = -OF[i][0] - FormaD[1][1];
                }
            
            }else if (rotcont % 4 == 3) {
                for (int i = 0; i < blocks; ++i) {
                    rotated[i][0] = OF[i][0] - FormaD[1][0];
                    rotated[i][1] = OF[i][1] - FormaD[1][1];
                }
            }
            FormaD = rotated;
        }
    }
    public void mostrarF(){
        fill(coloration);
        for (int i = 0; i < blocks; i++) {
            rect((FormaD[i][0]*M)+80, (FormaD[i][1]*M)+80,M,M);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //FUNCIÓN PARA IR HACIA ABAJO CONSTANTEMENTE
    //-----------------------------------------------------------------------------------------------------------------------------
    public void pabajo(int nivel){
        if(cont%(55-nivel) == 0){
            mover("ABAJO");
        }
        cont++;
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //BOOLEANO PARA CONTROLAR LOS LÍMITES DE LA CUADRÍCULA
    //-----------------------------------------------------------------------------------------------------------------------------
    public boolean limite(String dir){
        switch(dir){
            case "DER":
                for (int i = 0; i < blocks; ++i) {
                    if(FormaD[i][0]>(n-2)){
                        return false;
                    }
                }
                break;
            case "IZQ":
                for (int i = 0; i < blocks; ++i) {
                    if(FormaD[i][0]<1){
                        return false;
                    }
                }
                break;
            case "ABAJO":
                for (int i = 0; i < blocks; ++i) {
                    if(FormaD[i][1]>18){
                        return false;
                    }
                }
                break;
        }
        return true;
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //MOVIMIENTOS LATERALES Y HACIA ABAJO CONTROLADOS
    //-----------------------------------------------------------------------------------------------------------------------------
    public void mover(String dir){
        if(limite(dir)){
            if(dir == "DER"){
                for (int i = 0; i < blocks; ++i) {
                    FormaD[i][0]++;  //Dereiaaaaa
                }
            }
            if(dir == "IZQ"){
                for (int i = 0; i < blocks; ++i) {
                    FormaD[i][0]--;  //Izquierdaaaaaa
                }
            }
            if(dir == "ABAJO"){
                for (int i = 0; i < blocks; ++i) {
                    FormaD[i][1]++;  //Abajoooooo
                }
            }
        }
    }

}