Board main_board;

void setup() {
    size(1600,960);
    
    main_board = new Board(20, 20, 0);
}

void draw() {
    main_board.display();
}


class Board{

    color[][] board_matrix;
    color board_color;
    int y;
    int x;
    int block_size;

    Board(int matrix_lines, int matrix_columns, color color_b) {
        board_matrix = new color[matrix_lines][matrix_columns];
        board_color = color_b;
        block_size = 40;
        y = matrix_lines;
        x = matrix_columns;

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
                square(j * block_size + 80, i * block_size + 80, block_size);
            }
        }
    }

    



}