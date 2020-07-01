void startScreen() {
  ellipseMode(CENTER);
  noStroke();
  enemies.clear();
  bombs.clear();
  gen = null;
  generated = false;
  shields = 5;

  for (int i = 1; i < 21; i ++) {
    fill(255, 255, 0, 50);
    int size = i * 10;
    ellipse(width/2, height/2, size, size);
  }
  imageMode(CENTER);
  image(title, width/2, height/2);
  textAlign(CENTER);
  textSize(25);
  rectMode(CENTER);
  color col = (frameCount/30 % 2 == 0) ? 255 : 0;
  stroke(col);
  fill(255, 0, 255);
  rect(width/2, 500, 200, 30);
  fill(col);
  text("START GAME", width/2, 500, 200, 30); 

  if (mousePressed) {
    if (mouseX > 250 && mouseX < 450 && mouseY > 450 && mouseY < 550) {
      score = 0;
      level++;
      gameOver.counter = 0;
    }
  }
}
