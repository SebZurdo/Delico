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


____




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
 3. `out` permite sabe si la pieza está fuera de la matriz establecida.
 4. `dificulty` establece que tan rapido caen las figuras.
 5. `v_1, v_2, v_3` son los tres valores necesarios para representar el color del fondo en formato RGB.
 6. `over, start` permiten saber si la pantalla de game over o de inicio deben de ser mostradas.    


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

Este metodo se llama dentro del metodo `completed_lines()`. Hace que todo sobre la ultima fila en eliminarse baje una cantidad `completed_lines` de filas.
<br>

________

<br>

## **Shape**
<br>

Esta se encargará de todo lo relacionado con las formas que estarán dentro del juego, bien sea movimiento, posición y características, esta tomoará como variables a los [Polyominos](https://es.wikipedia.org/wiki/Poliomin%C3%B3) que irán apareciendo en la parte superior izquierda del tablero.

<br>

## *Polyominos*
<br>

````java
class Shape{
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

````

Cada Polyomino (limitado hasta [Pentomino](https://es.wikipedia.org/wiki/Pentomin%C3%B3)) tiene su propio arreglo, denotando que cada uno es único, estas formas están representadas como coordenadas en una sub matriz, tomando como ejemplo, el punto (0,0) sería la parte superior izquierda, esto ya que en Processing, este sistema de "coordenadas" es igual, lo que facilitaba el proceso.

<br>



## **Selector de polyomino**

<br>

````java
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
                coloration = #13FF1C;
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
                coloration = #66c285;
                break;
            case 8:
                ShapeD = eleD;
                coloration = #595454;
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
            blocks = 5;    //Depending on the restricted switch and the polyominoe selected, block number is taken in order to operate with the polyominoe
        }

````

El constructor recibe un entero *lvl* el cual simboliza el nivel en el cuál el usuario está jugando, debido a que el tablero se amplía a media que aumenta el nivel, la cantidad de bloques de los polyominos también lo hace, el constructor restringe un rango para el cual un switch seleccionará un número al azar el cuál tendrá asociado un polyomino, a partir este último, se le dan atributos únicos de su tipo y únicos de su propia forma.

<br>



## **Atributos**

<br>

### *Blocks*

<br>

Este atributo, como su nombre lo indica, denota la cantidad de bloques que conforman el polyomino, este es necesario para ejecutar los ciclos `for` responsables de controlar y monitorizar a la forma, ya que, aunque se trate de un conjunto de bloques, cada uno tiene sus propias coordenadas en el eje **x** y en el eje **y**.

<br>

### *Coloration*

<br>

Este atributo se encarga de darle un color único a cada polyiomino, permitiendo que todas las formas se diferencien entre sí, el formato es de color [HTML](https://es.wikipedia.org/wiki/HTML)  ahorrando el uso innecesario de más variables como hubiese sido necesario en caso de usar el [RGB](https://es.wikipedia.org/wiki/RGB), el formato elegido complía con las expectativas y permitía un mejor manejo y entendimiento del código.

<br>



## **Forma de representación**

<br>

Antes de seguir con los métodos, es importante exponer la fórma en que se representan los polyominos, debido a que es extremadamente necesario para poder entender cómo funciona la clase ``Shape`` y el juego ``Psyco-etris`` en general

<br>

### **Independencia de bloques**

<br>

Como se mencionó anteriormente, cada bloque que conforma el Polyomino tiene sus propias coordenadas, esto es posible debido a que las formas son representadas como un arreglo, y se llaman por medio de un ciclo de la siguiente forma:

<br>
 
### *Coordenadas*

### **X**

````java
for (int i = 0; i < blocks; ++i) {
    text("Coordenada X del bloque"+ str(i) +":"+str(ShapeD[i][0]),50,50);
````
<br>

Se puede inferir que la primera fila del array contiene el número de bloques del polyomino, como se puede ver, se llama al valor ``0`` del la segunda fila, que en este caso sería llamar la coordenada x del bloque mencionado en la fila anterior, lo mismo sucede con la coordenada **y**.

<br>

### **Y**

````java
for (int i = 0; i < blocks; ++i) {
    text("Coordenada Y del bloque"+ str(i) +":"+str(ShapeD[i][1]),50,50);
````
Se observa la misma lógica en este caso, una vez entendida esta forma de represetnación, es pertinente continuar.

<br>

## **Métodos**

<br>

### ``rotate()``

<br>

````java
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

````
Las rotaciones de los Polyominos se hacen de la misma forma a como se rota una figura o un conjunto de puntos dentro del plano cartesiano. Lo cual, significa que se invertían las posiciones y signos de las coordenadas **x** (``ShapeD[i][0]``) y **y** (``ShapeD[i][1]``).

<br>

### ``Limit()``

````java
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
````

Detecta los límites dependiendo de cuantas columnas tenga la matriz, dado que se realiza el ciclo con los bloques del polyomino, es improbable que algún bloque se salga debido a sus características anteriormente descritas.

<br>

### ``MoveShape()``

````java
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
````

Esta función mueve al polyomino, no sin antes borrar su posición anterior de la matriz para después inyectarla de nuevo una vez el movimiento se haya efectuado.

### ``extraMove()``

````java
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
````

Esta función es similar a la anterior,a excepción de que esta no borra su posición actual ni tampoco inyecta su posición en la matriz luego de efectuar el movimiento, esta función es útil para tratar a las formas cuando están fuera de la matriz por causa de una rotación.

### ``collitions()``

````java
boolean colitions(String dir, Board main_board){ //Boolean that detects collitions depending on the shape's surrounding
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
````
Este booleano permite identificar bloques a los costados de la Polyforma que se está controlando, para no tomar en cuenta los bloques de la misma forma se usa el siguiente método.

### ``not_in()``

````java
boolean not_in(int x, int y){ // Method checks if the block in the parameter is a block from the fig
        for(int i = 0; i < blocks; ++i){
            if(x == ShapeD[i][0] && y == ShapeD[i][1]){
                return false;
            }
        }

        return true;
    }
````

### ``inject()``

````java
void inject(Board mini_board, int h){ // Method used to show the fig in the mini_board
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
````
Inyecta una forma no movible ni rotable en una matriz secundaria para poder mostrar el cuadro de ``Siguiente pieza``.

<br>

________

<br>

## **Screens**

````java
psyco = loadImage("Psycoetris.png");
weirdfont = loadFont("Pristina-Regular-48.vlw");

class Screens{  //Class used to show Scoreboard and final and starting screens
    public Screens(){}
````
Esta se encarga de mostrar la pantalla de inicio, el tablero de puntuación y la pantalla de game_over, utliza la variable ``weirdFont`` (fuente) y ``psyco`` (imagen PNG) que se cargan en el Setup para evitar problemas de rendimiento


<br>

## **Métodos**

<br>

### ``startScreen()``

````java
void startScreen(){ //Creates the start screen wich disappears after player presses a key
        rect(160,160,1280,640);
        image(psyco, 320, 360, 460, 200);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Welcome!",960,440);
        text("Press any key to play",960,500);
        pop();

    }
````
Muestra un cuadro con 160 pixeles de distancia de cada lado, el cual desaparece una vez se oprime una tecla y comienza el juego.

### ``showBoard()``

````java
void showBoard(){   //Shows scoreboard with level and points
        rect(1120,400,240,360);
        image(psyco, 1125, 400, 230, 100);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Score: "+str(main_board.points),1130,540);
        text("Level: "+str(dificulty),1130,680);
        pop();
    }
````
Muestra el tablero de puntuación, la cual saca a partir de un objeto de la clase ``Board``

### ``showFinalGame()``

````java
void showFinalGame(){  //Creates the final screen, wich shows final points achieved by the players
        rect(160,160,1280,640);
        image(psyco, 320, 360, 460, 200);
        push();
        fill(0);
        textFont(weirdfont, 50);
        text("Final Score: "+str(main_board.points-2000)+" !",960,440);
        text("Thanks for playing!",960,500);
        pop();
    }
````
Muestra la pantalla de game over, dando la puntuación máxima alcanzada
<br>

___

<br>

## **Funciones**
<br>

Las funciones implementadas para la implementación del programa son:

<br>

### `keyReleased()`

````java
void keyReleased() {

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
                if(fig.ShapeD[i][0] > int(rows/2)){
                    HandleSidesR();
                    break;
                }
            }
            for (int i = 0; i < fig.blocks; ++i) {
                if(fig.ShapeD[i][0] < int(rows/2)){
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
````
Permite identifical si un bloque pertenenciente a un polyomino está fuera de la matriz y gracias al ``try`` y ``catch``, se puede resolver el problema mediante tres funciones recursivas perteneneicentes al "tipo" ``HandleSides``.


### `HandleSidesR()`

````java
void HandleSidesR(){ //Recursive function to move the shape when it goes over the right limit
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
````
Permite mover un polyomino cuando se sale por el lado derecho e inyectar sus coordenadas a la matriz del tablero.

### `HandleSidesL()`

````java
void HandleSidesL(){ //Recursive function to move the shape when it goes over the left limit
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
````
Permite mover un polyomino cuando se sale por el lado izquierdo e inyectar sus coordenadas a la matriz del tablero.

### `HandleSidesD()`

````java
void HandleSidesD(){ //Recursive function to move the shape when it goes over the left limit
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
````
Permite mover un polyomino cuando se sale por abajo e inyectar sus coordenadas a la matriz del tablero.










