void genEnemies() {
  if (!generated) {
    gen = new Generator();
    generated = true;
  }

  gen.update();
  gen.make();
}

class Generator {
  int fighters = 6 * level;
  int counter = 0;
  Generator() {
  }
  void update() {
    if (frameCount % 60 == 0) {
      counter++;
      if (fighters == 0 && enemies.size() == 0) {
        int choice = int(random(4));
        switch(choice) {
        case 0:
          directHit.play();
          break;
        case 1:
          yay1.play();
          break;
        case 2:
          yay2.play();
          break;
        case 3:
          yay3.play();
          break;
        }
        level ++;
        generated = false;
      }
    }
  }

  void make() {
    if (counter > 2) {
      if (enemies.size() == 0 && fighters > 0) {
        for (int i =0; i < level; i ++) {
          Enemy e = new Enemy();
          enemies.add(e);
          fighters --;
        }
      }
    }
    if (counter < 2) {
      textSize(20);
      textAlign(CENTER);
      text("NEXT WAVE!!", width/2, height/2);
    }
  }
}
