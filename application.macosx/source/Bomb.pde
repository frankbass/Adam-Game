class Bomb {
  PVector currPos;
  int startFrame;
  PVector dest;
  PVector dir;
  float size = 30;
  float speed;
  color col;
  float angle = 0;
  boolean destroyed = false;

  Bomb(PVector startPos) {
    currPos = startPos;
    startFrame = frameCount;
    dest = new PVector(random(100, width - 100), random(100, height - 100), 0);
    speed = .005;
    PVector diff;
    diff = dest.sub(startPos);
    dir = new PVector(diff.x*speed, diff.y*speed, diff.z*speed);
    
  }
  void update() {
    currPos = currPos.add(dir);
    col = sparkle();
    angle += .01;
  }
  void display() {
    if (!destroyed) {
    push();
    translate(currPos.x, currPos.y, currPos.z);
    rotate(angle);
    if (currPos.z <= 0) {
      stroke(col);
    } else {
      stroke(0);
    }
    strokeWeight(1);
    line(-size/2, -size/2, size/2, size/2);
    line(-size/2, size/2, size/2, -size/2);
    rotate(PI/4);
    line(-size/2, -size/2, size/2, size/2);
    line(-size/2, size/2, size/2, -size/2);
    pop();
    }
  }
  color sparkle() {
    color _col = color(255);
    int life = frameCount - startFrame;
    int inc = life % 8;
    if (inc % 2 == 0) {
      _col = color(255);
    } else {
      switch(inc) {
      case 1:
        _col = color(255, 0, 0);
        break;
      case 3:
        _col = color(0, 255, 0);
        break;
      case 5:
        _col = color(0, 0, 255);
        break;
      case 7:
        _col = color(255, 255, 0);
        break;
      }
    }
    return _col;
  }
}
