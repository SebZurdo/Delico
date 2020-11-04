Board main_board;
Board mini_board;

void setup() {
    size(1600,960);
    
    main_board = new Board(20, 20, 255, 80);
    mini_board = new Board(5, 5, 255, 1120);
}

void draw() {
    main_board.display();
    mini_board.display();
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