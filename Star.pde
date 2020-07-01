class Star {
  PVector currPos = new PVector(random(width), random(height), random(-1000, 0));
  color col;
  PVector speed = new PVector(0, 0, 10);
  Star() {
    col = colorPicker();
  }
  void update() {
    currPos.add(speed);
  }
  void display() {
    push();
    translate(currPos.x, currPos.y, currPos.z);
    fill(col);
    noStroke();
    ellipse(0, 0, 2, 2);
    pop();
  }
  color colorPicker() {
    color _col = color(255);
    int choice = int(random(5));
    switch(choice) {
    case 0:
      _col = color(255, 0, 0);
      break;
    case 1:
      _col = color(0, 255, 0);
      break;
    case 2:
      _col = color(0, 0, 255);
      break;
    case 3:
      _col = color(255, 255, 0);
      break;
    case 4:
      _col = color(255);
      break;
    }
    return _col;
  }
}
