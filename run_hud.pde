void run(float x, float y) {
  push();
  translate(x, y);
  for (int i = 0; i < enemies.size(); i ++) {
    Enemy e = enemies.get(i);
    e.update();
    e.display();
    if (e.countDown < 0) {
      enemies.remove(i);
    }
  }
  for (int i = 0; i < explosions.size(); i ++) {
    Explosion ex = explosions.get(i);
    ex.display();
    if (ex.countDown < 0) {
      ex.ex.stop();
      explosions.remove(i);
    }
  }
  Star st = new Star();
  stars.add(st);
  for (int i = 0; i < stars.size(); i ++) {
    Star s = stars.get(i);
    s.update();
    s.display();
    if (s.currPos.z > 100) {
      stars.remove(i);
    }
  }
  for (int i = 0; i < bombs.size(); i ++) {
    Bomb b = bombs.get(i);
    b.update();
    b.display();
    if (b.destroyed) bombs.remove(i);
    if (b.currPos.z > 0) {
      bombs.remove(b);
      background(255);
      shields --;
      explosion1.play();
      if (shields < 2 && shields > -1) shieldsLow.play();
      if (shields < 0) {
        if (level != -1) youLose.play();
        level = -1;
        
      }
    }
  }
  pop();
  cH.display();
  hud();
}

void hud() {
  textAlign(CENTER);
  textSize(12);
  int x = 325;
  int y = 620;
  int size = 30;
  fill(255);
  stroke(0);
  strokeWeight(1);
  rectMode(CORNER);
  text("SHIELDS:", x + 25, y - 10);
  for (int i = 0; i < shields; i ++) {
    rect(x -55 + size * i, y + 10, size, size * 2);
  }
  text("SCORE:"+score, x+25, 20);
}
