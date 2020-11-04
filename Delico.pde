int M;
int n;
int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
Board main_board;
Board mini_board;
Forma fig;
void setup() {
    size(1600,960);
    
    main_board = new Board(20, 20, 255, 80);
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
    private int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
    private int[][] line = {{0,0},{1,0},{2,0},{3,0},};
    private int[][] treh = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] eleL = {{0,0},{0,1},{1,0},{2,0}};
    private int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
    private int[][] S1 = {{0,0},{1,0},{1,1},{2,1}};
    private int[][] S2 = {{0,1},{1,1},{1,0},{2,0}};
    private int[][] FormaD, OF;
    private int r,g,b, orden;
    private boolean EnMovi;
    private float M;
    private int cont, rotcont;
    public Forma(){
        M = 40;
        orden = (int)random(6);
        switch(orden){
            case 0:
                FormaD = drado;
                r = 0;
                g = 0;
                b = 139;
                break;
            case 1:
                FormaD = line;
                r = 220;
                g = 20;
                b = 60;
                break;
            case 2:
                FormaD = treh;
                r = 139;
                g = 69;
                b = 19;
                break;
            case 3:
                FormaD = eleL;
                r = 186;
                g = 85;
                b = 211;
                break;
            case 4:
                FormaD = eleD;
                r = 255;
                g = 255;
                b = 0;
                break;
            case 5:
                FormaD = S1;
                r = 30;
                g = 144;
                b = 255;
                break;
            case 6:
                FormaD = S2;
                r = 34;
                g = 139;
                b = 34;
                break;
        }
        cont = 1;
        OF = FormaD;
        rotcont = 0;
    }
    public void rota(){
        if (FormaD != drado) {
            int[][] rotated = new int[4][2];
            if (rotcont % 4 == 0) {
                for (int i = 0; i < 4; ++i) {
                    rotated[i][0] = -OF[i][1] - FormaD[1][0];
                    rotated[i][1] = OF[i][0] - FormaD[1][1];
                }
            }else if (rotcont % 4 == 1) {
                for (int i = 0; i < 4; ++i) {
                    rotated[i][0] = -OF[i][0] - FormaD[1][0];
                    rotated[i][1] = -OF[i][1] - FormaD[1][1];
                }
            }else if (rotcont % 4 == 2) {
                for (int i = 0; i < 4; ++i) {
                    rotated[i][0] = OF[i][1] - FormaD[1][0];
                    rotated[i][1] = -OF[i][0] - FormaD[1][1];
                }
            
            }else if (rotcont % 4 == 3) {
                for (int i = 0; i < 4; ++i) {
                    rotated[i][0] = OF[i][0] - FormaD[1][0];
                    rotated[i][1] = OF[i][1] - FormaD[1][1];
                }
            }
            FormaD = rotated;
        }
    }
    public void mostrarF(){
        fill(r,g,b);
        for (int i = 0; i < 4; i++) {
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
                for (int i = 0; i < 4; ++i) {
                    if(FormaD[i][0]>18){
                        return false;
                    }
                }
                break;
            case "IZQ":
                for (int i = 0; i < 4; ++i) {
                    if(FormaD[i][0]<1){
                        return false;
                    }
                }
                break;
            case "ABAJO":
                for (int i = 0; i < 4; ++i) {
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
                for (int i = 0; i < 4; ++i) {
                    FormaD[i][0]++;  //Dereiaaaaa
                }
            }
            if(dir == "IZQ"){
                for (int i = 0; i < 4; ++i) {
                    FormaD[i][0]--;  //Izquierdaaaaaa
                }
            }
            if(dir == "ABAJO"){
                for (int i = 0; i < 4; ++i) {
                    FormaD[i][1]++;  //Abajoooooo
                }
            }
        }
    }

}