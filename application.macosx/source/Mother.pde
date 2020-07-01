class MotherShip {
  int countDown = 10;
  float x = width/2;
  float y = height/2;
  float z = -200;
  ArrayList<Cannon> cannons = new ArrayList();
  int motherShields = 10;
  boolean vulnerable = false;
  boolean hit = false;
  boolean destroyed = false;
  float mOffX = 0;
  float mOffY = 0;
  float angle = 0;
  int expInt = 10;
  boolean playedSound = false;

  MotherShip() {
    Cannon c1 = new Cannon(-1, -1);
    cannons.add(c1);
    Cannon c2 = new Cannon(-1, 1);
    cannons.add(c2);
    Cannon c3 = new Cannon(1, 1);
    cannons.add(c3);
    Cannon c4 = new Cannon(1, -1);
    cannons.add(c4);
  }

  void update() {
    if (frameCount % 60 == 0 && destroyed) countDown --;
    cannons();
    if (cannons.size() == 0) vulnerable = true;
    if (countDown <= 0) level ++;
    if (motherShields <= 0) destroyed = true;
  }
  void display() {

    push();
    translate(transX, transY, z);  
    if (destroyed) {
      if (frameCount % expInt == 0) {
        hit = true;
        float randX = random(-300, 200) + x;
        float randY = random(-100, 200) + y;
        int big = int(random(3, 15));
        Explosion ex = new Explosion(randX, randY, z, big);
        explosions.add(ex);
      }
      x += mOffX;
      y += mOffY;
      rotate(angle);
      mOffX += .01;
      mOffY += .00005;
      angle += .0008;
      z -= .7;
      if (!playedSound) {
        goodJob.play();
        playedSound = true;
      }
    }
    rectMode(CENTER);
    color col = (hit) ? 255 : 0;
    fill(col);
    strokeWeight(1);
    stroke(0, 0, 255);
    rect(x, y - 115, 300, 30);
    rect(x, y + 115, 300, 30);
    stroke(255, 255, 0);
    rect(x, y, 500, 200);
    stroke(0, 255, 0);
    rect(x, y, 180, 80);
    pop();
  }

  void cannons() {
    for (int i = 0; i < cannons.size(); i ++) {
      Cannon c = cannons.get(i);
      c.update();
      c.display();
      if (c.destroyed) {
        cannons.remove(i);
      }
    }
  }

  class Cannon {
    float xOff = 220;
    float yOff = 115;
    float xPos = 220;
    float yPos = 115;
    int fireFreq = int(random(50, 100));
    int start = 0;
    boolean firing = false;
    boolean destroyed = false;

    Cannon(int _x, int _y) {
      xOff *= _x;
      yOff *= _y;
      xPos = x + xOff;
      yPos = y + yOff;
    }
    void update() {
      if (!destroyed) {
        if (frameCount  % fireFreq == 0) {
          firing = true;
          PVector loc = new PVector(xPos, yPos, z);
          Bomb b = new Bomb(loc);
          bombs.add(b);
        } else {
          firing = false;
        }
      }
    }
    void display() {
      if (!destroyed) {
        push();
        translate(transX, transY, z);
        rectMode(CENTER);
        stroke(255, 0, 255);
        color col = (firing) ? 255 : 0;
        fill(col);
        rect(xPos, yPos, 40, 30);
        pop();
      }
    }
  }
}
