void mousePressed() {
  laser.update();
  pew.play();
  //System.gc();
  float x = mouseX - (width / 2);
  //x = x * .9;
  x = mouseX + x;
  float y = mouseY - (height / 2);
  //y *= .9;
  y += mouseY;
  for (Enemy e : enemies) {
    float dist = dist(e.pos.x, e.pos.y, x, y);
    if (dist < 190 && !e.destroyed) {
      e.destroyed = true;
      score += 100;
      calcScore += 100;
      Explosion ex = new Explosion(e.pos.x, e.pos.y, e.pos.z, 20);
      explosions.add(ex);
      oneUp = false;
    }
  }
  for (Bomb b : bombs) {
    float dist = dist(b.currPos.x, b.currPos.y, x, y);
    if (dist < 100) {
      b.destroyed = true;
      score += 20;
      calcScore += 20;
      Explosion ex = new Explosion(b.currPos.x, b.currPos.y, b.currPos.z, 3);
      explosions.add(ex);
      oneUp = false;
    }
  }
  if (level == 4) {
    for (MotherShip.Cannon c : motherShip.cannons) {
      float dist = dist(c.xPos, c.yPos, x, y);
      if (dist < 100) {
        c.destroyed = true;
        score += 100;
        calcScore += 100;
        Explosion ex = new Explosion(c.xPos, c.yPos, motherShip.z, 10);
        explosions.add(ex);
        oneUp = false;
      }
    }
  }
  if (level == 4 && !motherShip.destroyed && motherShip.vulnerable) {
    float dist = dist(motherShip.x, motherShip.y, x, y);
    if (dist < 200) {
      motherShip.hit = true;
      motherShip.motherShields --;
      Explosion ex = new Explosion(x, y, motherShip.z, 10);
      explosions.add(ex);
      oneUp = false;
    }
  }
}
