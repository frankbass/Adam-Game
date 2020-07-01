import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 
import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Adam extends PApplet {



ArrayList<Bomb> bombs = new ArrayList();
ArrayList<Star> stars = new ArrayList();
CrossHairs cH = new CrossHairs();
Guns guns = new Guns();
Laser laser;
ArrayList<Enemy> enemies= new ArrayList();
ArrayList<Explosion> explosions = new ArrayList();
int score = 0;
int calcScore = 0;
int shields = 5;
boolean oneUp = false;
int level = 0;
float transX;
float transY;
boolean generated = false;
Generator gen;
PImage title, lose, win1, win2;
GameOver gameOver;
MotherShip motherShip;
Happy happy;

SoundFile currMusic, gameOverMusic, level0Music, level1Music, level2Music, level3Music, level4Music, level5Music;
SoundFile bonus, directHit, end, goodJob, bd1, bd2, shieldsLow, yay1, yay2, yay3, youDidIt, youLose, youWon;
SoundFile pew, explosion1, explosion2, explosion3, explosion4, explosion5;
ArrayList<SoundFile> explosionSounds;

public void setup() {
  
  noCursor();
  loadingSounds();
  laser = new Laser();
  for (int i = 0; i < 100; i ++) {
    Star s = new Star();
    stars.add(s);
  }
  title = loadImage("data/images/Title.png");
  win1 = loadImage("data/images/Adam1.png");
  win2 = loadImage("data/images/Adam2.png");
  lose = loadImage("data/images/game over.png");
  motherShip = new MotherShip();
  gameOver = new GameOver();
  happy = new Happy(); 
}

public void draw() {
  background(0);
  levels();
  music();
  transX = map(mouseX, 0, width, 50, -50);
  transY = map(mouseY, 0, height, 50, -50);
  guns.display();
  laser.display();
  run(transX, transY);
  if (score > 0 && calcScore % 1000 == 0 && !oneUp) {
    shields += 1;
    oneUp = true;
    bonus.play();
  }
}

public void motherShip() {
  motherShip.update();
  motherShip.display();
  motherShip.hit = false;
}

public void gameOver() {
  gameOver.update();
  gameOver.display();
}

class GameOver {
  int counter = 0;
  GameOver() {
    
  }
  public void update() {
    if (frameCount % 60 == 0) counter ++; 
    if (counter > 4) level ++;
  }
  public void display() {
    imageMode(CENTER);
    image(lose, width/2, height/2, 300, 250);
  }
}

class Happy {
  int toggle = 1;
  int expInt = 10;
  Happy() {
  }
  public void update() {
  }
  public void display() {
    imageMode(CENTER);
    if (frameCount % 10 == 0) toggle *= -1;
    PImage img = (toggle == 1) ? win1 : win2;
    image(img, width/2, height/2, 500, 300);
    if (frameCount % expInt == 0) {
      float randX = random(width);
      float randY = random(height);
      int big = PApplet.parseInt(random(3, 15));
      Explosion ex = new Explosion(randX, randY, 0, big);
      explosions.add(ex);
    }
  }
}

public void happy() {
  happy.display();
  if (!end.isPlaying()) end.play();
}

public void levels() {
  switch(level) {
  case -1:
    gameOver();
    break;
  case 0:
    startScreen();
    break;
  case 1:
    genEnemies();
    break;
  case 2:
    genEnemies();
    break;
  case 3:
    genEnemies();
    break;
  case 4:
    motherShip();
    break;
  case 5:
    happy();
    break;
  }
}

public void music() {
  if (!currMusic.isPlaying()) {
    switch(level) {
    case -1:
      currMusic = gameOverMusic;
      break;
    case 0:
      currMusic = level0Music;
      break;
    case 1:
      currMusic = level1Music;
      break;
    case 2:
      currMusic = level2Music;
      break;
    case 3:
      currMusic = level3Music;
      break;
    case 4:
      currMusic = level4Music;
      break;
    case 5:
      currMusic = level5Music;    
      break;
    }
    currMusic.amp(.1f);
    currMusic.play();
  }
}

class Bomb {
  PVector currPos;
  int startFrame;
  PVector dest;
  PVector dir;
  float size = 30;
  float speed;
  int col;
  float angle = 0;
  boolean destroyed = false;

  Bomb(PVector startPos) {
    currPos = startPos;
    startFrame = frameCount;
    dest = new PVector(random(100, width - 100), random(100, height - 100), 0);
    speed = .005f;
    PVector diff;
    diff = dest.sub(startPos);
    dir = new PVector(diff.x*speed, diff.y*speed, diff.z*speed);
    
  }
  public void update() {
    currPos = currPos.add(dir);
    col = sparkle();
    angle += .01f;
  }
  public void display() {
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
  public int sparkle() {
    int _col = color(255);
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
class CrossHairs {
  CrossHairs() {
  }
  public void display() {
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
class Enemy {
  PVector pos, dest, dir, off;
  float speed = .0005f;
  int fireFreq = PApplet.parseInt(random(80, 120));
  boolean destroyed = false;
  int countDown = 70;

  Enemy() {
    int startSide = PApplet.parseInt(random(4));
    int endSide = PApplet.parseInt(random(3));
    endSide = (endSide + startSide) % 4;
    pos = randPos(startSide, -1000);
    dest = randPos(endSide, 100);
    dir = dest.copy();
    dir.sub(pos);
    dir.mult(speed);
    off = new PVector(0, 0);
  }
  public PVector randPos(int side, int _z) {
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
  public void update() {
    off.x = cos(PApplet.parseFloat(frameCount)/100);
    off.y = sin(PApplet.parseFloat(frameCount)/100);
    pos.add(off);
    pos.add(dir);
    if (frameCount % fireFreq == 0 && !destroyed) {
      PVector loc = new PVector(pos.x, pos.y, pos.z);
      Bomb b = new Bomb(loc);
      bombs.add(b);
    }
    if (destroyed) countDown --;
  }
  public void display() {
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
      rotate(PApplet.parseFloat(frameCount)/10);
      triangle(-15, 0, -20, -35, -20, 35);
      pop();
      push();
      translate(pos.x + PApplet.parseFloat(frameCount)/10, pos.y + PApplet.parseFloat(frameCount)/10, pos.z);
      rotate(-PApplet.parseFloat(frameCount)/10);
      triangle(15, 0, 20, -35, 20, 35);
      pop();
    }
  }
}
class Explosion {
  PVector pos;
  int countDown = 30;
  int start;
  int size;
  SoundFile ex;
  Explosion(float x, float y, float z, int _size) {
    pos = new PVector(x, y, z);
    start = frameCount;
    size = _size;
    int choose = PApplet.parseInt(random(5));
    ex = explosionSounds.get(choose);
    ex.amp(.1f);
    ex.play();
  }
  public void display() {
    if (countDown > 0) {
      strokeWeight(1);
      noFill();
      stroke(255, 255, 0, 100);
      int diff = start - frameCount;
      push();
      translate(pos.x, pos.y, pos.z);
      for (int i = 0; i < size; i ++) {
        float size = PApplet.parseFloat(i * diff)/3;
        ellipse(0, 0, size, size);
      }
      pop();
    }
    countDown --;
  }
}

class Guns {
  int y = 370;
  Guns() {
  }
  public void display() {
    float tX = transX * -1;
    float tY = transY * -1;
    
    push();
    translate(tX, tY);
    noFill();
    strokeWeight(1);
    stroke(0, 0, 255, 150);
    translate(width/2, height/2);
    rotate(PI/4);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    rotate(PI/2);
    triangle(0, y, 5, y+50, -5, y+50);
    beginShape();
    vertex(-3, y+50);
    vertex(3, y + 50);
    vertex(10, y + 250);
    vertex(-10, y + 250);
    endShape(CLOSE);
    pop();
  }
}
class Laser {
  int laserCount = 0;
  boolean fire = false;

  PVector curr1 = new PVector(100, 100);
  PVector curr2 = new PVector(width - 100, height - 100);
  PVector dest = new PVector(mouseX, mouseY);

  Laser() {
  }
  public void update() {
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

  public void display() {
    if (fire) {
      stroke(255, 200);
      strokeWeight(1);
      line(curr1.x, curr1.y, dest.x, dest.y);
      line(curr2.x, curr2.y, dest.x, dest.y);
      fire = false;
    }
  }
}
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

  public void update() {
    if (frameCount % 60 == 0 && destroyed) countDown --;
    cannons();
    if (cannons.size() == 0) vulnerable = true;
    if (countDown <= 0) level ++;
    if (motherShields <= 0) destroyed = true;
  }
  public void display() {

    push();
    translate(transX, transY, z);  
    if (destroyed) {
      if (frameCount % expInt == 0) {
        hit = true;
        float randX = random(-300, 200) + x;
        float randY = random(-100, 200) + y;
        int big = PApplet.parseInt(random(3, 15));
        Explosion ex = new Explosion(randX, randY, z, big);
        explosions.add(ex);
      }
      x += mOffX;
      y += mOffY;
      rotate(angle);
      mOffX += .01f;
      mOffY += .00005f;
      angle += .0008f;
      z -= .7f;
      if (!playedSound) {
        goodJob.play();
        playedSound = true;
      }
    }
    rectMode(CENTER);
    int col = (hit) ? 255 : 0;
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

  public void cannons() {
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
    int fireFreq = PApplet.parseInt(random(50, 100));
    int start = 0;
    boolean firing = false;
    boolean destroyed = false;

    Cannon(int _x, int _y) {
      xOff *= _x;
      yOff *= _y;
      xPos = x + xOff;
      yPos = y + yOff;
    }
    public void update() {
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
    public void display() {
      if (!destroyed) {
        push();
        translate(transX, transY, z);
        rectMode(CENTER);
        stroke(255, 0, 255);
        int col = (firing) ? 255 : 0;
        fill(col);
        rect(xPos, yPos, 40, 30);
        pop();
      }
    }
  }
}
class Star {
  PVector currPos = new PVector(random(width), random(height), random(-1000, 0));
  int col;
  PVector speed = new PVector(0, 0, 10);
  Star() {
    col = colorPicker();
  }
  public void update() {
    currPos.add(speed);
  }
  public void display() {
    push();
    translate(currPos.x, currPos.y, currPos.z);
    fill(col);
    noStroke();
    ellipse(0, 0, 2, 2);
    pop();
  }
  public int colorPicker() {
    int _col = color(255);
    int choice = PApplet.parseInt(random(5));
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
public void genEnemies() {
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
  public void update() {
    if (frameCount % 60 == 0) {
      counter++;
      if (fighters == 0 && enemies.size() == 0) {
        int choice = PApplet.parseInt(random(4));
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

  public void make() {
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

public void loadingSounds() {
  gameOverMusic = new SoundFile(this, "data/sounds/Level -1.wav");
  level0Music = new SoundFile(this, "data/sounds/Level 0.wav");
  level1Music = new SoundFile(this, "data/sounds/Level 1.wav");
  level2Music = new SoundFile(this, "data/sounds/Level 2.wav");
  level3Music = new SoundFile(this, "data/sounds/Level 3.wav");
  level4Music = new SoundFile(this, "data/sounds/Level 4.wav");
  level5Music = new SoundFile(this, "data/sounds/Level 5.wav");
  currMusic = level0Music;
  bonus = new SoundFile(this, "data/sounds/bonus.wav");
  directHit= new SoundFile(this, "data/sounds/direct hit.wav");
  end= new SoundFile(this, "data/sounds/end.wav");
  goodJob= new SoundFile(this, "data/sounds/good job.wav");
  bd1= new SoundFile(this, "data/sounds/happy bd1.wav");
  bd2= new SoundFile(this, "data/sounds/happy bd2.wav");
  shieldsLow= new SoundFile(this, "data/sounds/shields low.wav");
  yay1= new SoundFile(this, "data/sounds/yay1.wav");
  yay2= new SoundFile(this, "data/sounds/yay2.wav");
  yay3= new SoundFile(this, "data/sounds/yay3.wav");
  
  youDidIt= new SoundFile(this, "data/sounds/you did it.wav");
  youLose= new SoundFile(this, "data/sounds/you lose.wav");
  youWon = new SoundFile(this, "data/sounds/you won.wav");

  pew= new SoundFile(this, "data/sounds/pew.wav"); 
  explosionSounds = new ArrayList();
  explosion1= new SoundFile(this, "data/sounds/explosion1.wav");
  explosionSounds.add(explosion1);
  explosion2= new SoundFile(this, "data/sounds/explosion2.wav");
  explosionSounds.add(explosion2);
  explosion3= new SoundFile(this, "data/sounds/explosion3.wav");
  explosionSounds.add(explosion3);
  explosion4= new SoundFile(this, "data/sounds/explosion4.wav");
  explosionSounds.add(explosion4);
  explosion5 = new SoundFile(this, "data/sounds/explosion5.wav");
  explosionSounds.add(explosion5);
}

public void mousePressed() {
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
public void run(float x, float y) {
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

public void hud() {
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

public void startScreen() {
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
  int col = (frameCount/30 % 2 == 0) ? 255 : 0;
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
  public void settings() {  size(700, 700, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Adam" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
