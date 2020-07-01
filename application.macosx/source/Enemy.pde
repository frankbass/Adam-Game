class Enemy {
  PVector pos, dest, dir, off;
  float speed = .0005;
  int fireFreq = int(random(80, 120));
  boolean destroyed = false;
  int countDown = 70;

  Enemy() {
    int startSide = int(random(4));
    int endSide = int(random(3));
    endSide = (endSide + startSide) % 4;
    pos = randPos(startSide, -1000);
    dest = randPos(endSide, 100);
    dir = dest.copy();
    dir.sub(pos);
    dir.mult(speed);
    off = new PVector(0, 0);
  }
  PVector randPos(int side, int _z) {
    float x = 100;
    float y = 100;
    int buf = 300;
    switch(side) {
    case 0:
      x = random(width);
      y = -buf;
      break;
    case 1:
      x = width + buf;
      y = random(height);
      break;
    case 2:
      x = random(width);
      y = buf;
      break;
    case 3:
      x = width - buf;
      y = random(height);
      break;
    }
    PVector p = new PVector(x, y, _z);
    return p;
  }
  void update() {
    off.x = cos(float(frameCount)/100);
    off.y = sin(float(frameCount)/100);
    pos.add(off);
    pos.add(dir);
    if (frameCount % fireFreq == 0 && !destroyed) {
      PVector loc = new PVector(pos.x, pos.y, pos.z);
      Bomb b = new Bomb(loc);
      bombs.add(b);
    }
    if (destroyed) countDown --;
  }
  void display() {
    noFill();
    strokeWeight(1);
    stroke(0, 255, 0, 200);
    if (!destroyed) {
      ellipseMode(CENTER);
      push();
      translate(pos.x, pos.y, pos.z);
      rotate(off.x);
      ellipse(0, 0, 30, 30);
      ellipse(0, 0, 10, 10);
      triangle(-15, 0, -20, -35, -20, 35);
      triangle(15, 0, 20, -35, 20, 35);
      pop();
    } else {
      push();
      translate(pos.x, pos.y, pos.z);
      rotate(float(frameCount)/10);
      triangle(-15, 0, -20, -35, -20, 35);
      pop();
      push();
      translate(pos.x + float(frameCount)/10, pos.y + float(frameCount)/10, pos.z);
      rotate(-float(frameCount)/10);
      triangle(15, 0, 20, -35, 20, 35);
      pop();
    }
  }
}
