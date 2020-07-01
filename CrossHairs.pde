class CrossHairs {
  CrossHairs() {
  }
  void display() {
    noFill();
    stroke(255, 0, 0, 100);
    push();
    translate(mouseX, mouseY);
    rotate(PI/4);
    triangle(0, 10, 5, 30, -5, 30);
    rotate(PI/2);
    triangle(0, 10, 5, 30, -5, 30);
    rotate(PI/2);
    triangle(0, 10, 5, 30, -5, 30);
    rotate(PI/2);
    triangle(0, 10, 5, 30, -5, 30);
    pop();
  }
}
