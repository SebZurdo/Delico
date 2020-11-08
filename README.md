# **Psyco-Etris**
## **Información General**
<br>

Este proyecto tiene como finalidad poner en practica los conocimientos y herramientas aportados por el paradigma de programación orientada a objetos (POO) a traves de la programación de una versión modificada del juego [Tetris](https://en.wikipedia.org/wiki/Tetris).

<br>

El nombre atribuido a este juego se debe a que en cierto punto del gameplay multiples colores aparecerán rapidamente en la pantalla, esto para afectar la concentración del jugador y aumentar la dificultad, sin embargo, debido a las implicaciones naturales de este tipo de "detalles", no se recomienda jugarlo por un tiempo prolongado y está de más decir que si padece de algun signo de epilepsia no debería probarlo, o al menos evitar llegar a los 1800 puntos.

<br>

La pista de musica utilizada es: [Astrix - Deep Jungle Walk](https://www.youtube.com/watch?v=lIuEuJvKos4). Todos los derechos reservados a su autor. Para poder escucharla durante la ejecución del programa se debe de instalar la librería de sonido de Processing, [Aquí un tutorial basico de como descargar y usar esta librería](https://poanchen.github.io/blog/2016/11/15/how-to-add-background-music-in-processing-3.0).

<br>

____

## **Descripción del Juego e Instrucciones**
<br>

Esta versión modificada del Tetris comenzará con monominos y la cantidad de bloques que conforman las figuras así como el tamaño del tablero se irán incrementando hasta llegar a los pentominos, incluyendo tambien todas las figuras anteriores. En general las reglas del juego son las mismas que las del Tetris original, solo que con esta "mecanica" adicional.

<br>

Use las teclas de flechas para (Derecha, Izquierda, Abajo) para mover la figura que cae, y use la tecla de flecha (Arriba) para rotarla. 

<br>

___

<br>


## **Revisión General del codigo**
<br>

Primero que todo, el programa está escrito en Processing 3.0 y tiene unas 802 lineas. En el codigo se definen las siguientes variables globales importantes para su funcionamiento: 
<br>
```java
int level, rows, out, dificulty, v_1, v_2, v_3;
boolean over, start;
```
<br>

 1. `level` establece el nivel actual del juego.
 2. `rows` establece el limite de columnas para el movimiento de las figuras y mostrar el tablero de juego.
 3. `out` ////
 4. `dificulty` establece que tan rapido caen las figuras.
 5. `v_1, v_2, v_3` son los tres valores necesarios para representar el color del fondo en formato RGB.
 6. `over, start` ////    


<br>

El flujo general del codigo es el siguiente: //////

<br>

____

<br>

## **Clases** 
<br>

Son en total 3 las clases diseñadas para implementar el juego.

<br>

### `Board`
<br>

La clase `Board` abstrae caracteristicas en común de los tableros de juego de Tetris, fue ideada de esta forma para poder representar ambos tableros (El tablero principal y el tablero más pequeño en donde se muestra la proxima figura en caer) con la misma clase. Sus función constructora es la siguiente:

```java

    Board(int matrix_lines, int matrix_columns, color color_b, int space) {
        board_matrix = new color[matrix_lines][matrix_columns];
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
```
<br>

La cual define los siguientes atributos:

1. `color[][] board_matrix`, la matriz de colores del tablero creado. Los parametros `matrix_columns` y `matrix_lines` establecen sus dimensiones.
2. `color board_color`, el color inicial u original del tablero mediante el parametro `color_b` en formato alfanumérico.
3. `int block_size`, el tamaño del bloque unidad para teselar la ventana y mostrar el tablero, el cual es un valor constante.
4. `int y, x`, las filas y columnas de la matriz respectivamente.
5. `int space_x`, la distancia desde el borde izquierdo de la ventana.
6. `int points`, el puntaje de juego en el tablero.
<br><br>

Los metodos de esta clase son:
<br>

### `display()`
<br>

```java
void display(int rows, int v_1, int v_2, int v_3){ // Displays the board according to the limit of what we want to display
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
```
<br>

Muestra el tablero según el limite aplicado a la matriz (Recordando que el tablero dependiendo del nivel se expande). Lo que hace es dibujar la matriz dependiendo del limite, y lo que sobre, lo pinta del mismo color que el fondo.
<br>

### `clean()`
<br>

```java
void clean(){ // Cleans the board
        for(int i = 0; i < y; ++i){
            for(int j = 0; j < x; ++j){
                board_matrix[i][j] = board_color;
            }
        }
    }
```
<br>

Limpia el tablero retornando el color de la matriz a su color inicial `board_color`.
<br>

### `completed_lines()`
<br>

```java
void completed_lines(int limit, int level){ // Checks and erases the completed lines and also updates the score
        color block_color; // Variable that stores the color of the initial block of a line
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
```
<br>

Busca y borra las líneas completadas (coloreando esas lineas de `board_color` en la matriz ) reconociendo una línea completa cuando no hay un solo bloque de color `board_color` en ella, también almacena el índice de las líneas, el total de líneas completadas y cambia el puntaje dependiendo de la cantidad de lineas completadas.
<br>

### `after_line_complete()`

<br>

```java
void after_line_complete(int[] lines, int completed_lines, int limit){ // Makes that everything falls again after a line or lines disappear
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
```
<br>

Este metodo se llama dentro del metodo `completed_lines()`. Hace que todo sobre la primera linea (de abajo hacia arriba) baje una cantidad `completed_lines` de filas.
<br>









