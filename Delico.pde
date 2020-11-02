int x = 25,y = 25;
void setup() {
    size(900,900);
}

void draw() {
    background(255);
    stroke(0);
    strokeWeight(1.2);
    fill(0);
    text("x ="+str(x),350,40);
    text("y ="+str(y),350,60);
    fill(255);
    rect(225,225,450,450);
    rect(x,y,25,25);
    line(0, 225, 225, 225);
    fill(0);
    line(600, 225, 900, 225);
    line(0, 675, 225, 675);
    line(600, 675, 900, 675);
    fill(255,0,0);
    line(225, 225, 600, 225);
    line(225, 675, 600, 675);




    line(225, 675, 600, 675);
}

void keyPressed() {
    if(key == CODED){
        if(keyCode == RIGHT){
            mover("DER");
        }
        if(keyCode == LEFT){
            mover("IZQ");
        }
        if(keyCode == DOWN){
            mover("DOWN");
        }
        if(keyCode == UP){
            mover("UP");
        }
    }
}
void mover(String dir){
    if(dir == "DER"){
        x =x+25;
    }
    if(dir == "IZQ"){
        x =x-25;
    }
    if(dir == "DOWN"){
        y =y + 25;
    }
    if(dir == "UP"){
        y =y - 25;
    }
  
}
