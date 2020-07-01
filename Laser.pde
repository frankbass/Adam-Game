class Laser {
  int laserCount = 0;
  boolean fire = false;

  PVector curr1 = new PVector(100, 100);
  PVector curr2 = new PVector(width - 100, height - 100);
  PVector dest = new PVector(mouseX, mouseY);

  Laser() {
  }
  void update() {
    fire = true;
    
    dest.x = mouseX;
    dest.y = mouseY;

    if (laserCount % 2 == 0) {
      curr1.y = 100;
      curr2.y = height - 100;
    } else {
      curr1.y = height -100;
      curr2.y = 100;
    }
    laserCount ++;
  }

  void display() {
    if (fire) {
      stroke(255, 200);
      strokeWeight(1);
      line(curr1.x, curr1.y, dest.x, dest.y);
      line(curr2.x, curr2.y, dest.x, dest.y);
      fire = false;
    }
  }
}
