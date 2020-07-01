class Guns {
  int y = 370;
  Guns() {
  }
  void display() {
    float tX = transX * -1;
    float tY = transY * -1;
    
    push();
    translate(tX, tY);
    noFill();
    strokeWeight(1);
    stroke(0, 0, 255, 150);
    translate(width/2, height/2);
    rotate(PI/4);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    pop();
  }
}
