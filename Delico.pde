int M;
int n;
int[][] drado = {{0,0},{1,0},{0,1},{1,1}};
int[][] eleD = {{0,0},{1,0},{2,0},{2,1}};
void setup() {
    size(1600,960);
}

void draw() {
    n = 4;
    nivel(n);
    matrx();
    for (int i = 0; i < 4; ++i) {
        square((FormaD[i][0]*40)+80,(FormaD[i][1]*40)+80,40);
    }
}
void matrx(){
    for (int i = 0; i < 21; ++i) {
        line(80, 80+(40*i), 80+(40*M), 80+(40*i));
    }
    for (int i = 0; i < (M+1); ++i) {
        line(80+(40*i), 80, 80+(40*i), 880);
    }
}
void nivel(int n){
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
