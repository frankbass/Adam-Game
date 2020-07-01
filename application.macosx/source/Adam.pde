import processing.sound.*;

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
import processing.sound.*;
SoundFile currMusic, gameOverMusic, level0Music, level1Music, level2Music, level3Music, level4Music, level5Music;
SoundFile bonus, directHit, end, goodJob, bd1, bd2, shieldsLow, yay1, yay2, yay3, youDidIt, youLose, youWon;
SoundFile pew, explosion1, explosion2, explosion3, explosion4, explosion5;
ArrayList<SoundFile> explosionSounds;

void setup() {
  size(700, 700, P3D);
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

void draw() {
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

void motherShip() {
  motherShip.update();
  motherShip.display();
  motherShip.hit = false;
}

void gameOver() {
  gameOver.update();
  gameOver.display();
}

class GameOver {
  int counter = 0;
  GameOver() {
    
  }
  void update() {
    if (frameCount % 60 == 0) counter ++; 
    if (counter > 4) level ++;
  }
  void display() {
    imageMode(CENTER);
    image(lose, width/2, height/2, 300, 250);
  }
}

class Happy {
  int toggle = 1;
  int expInt = 10;
  Happy() {
  }
  void update() {
  }
  void display() {
    imageMode(CENTER);
    if (frameCount % 10 == 0) toggle *= -1;
    PImage img = (toggle == 1) ? win1 : win2;
    image(img, width/2, height/2, 500, 300);
    if (frameCount % expInt == 0) {
      float randX = random(width);
      float randY = random(height);
      int big = int(random(3, 15));
      Explosion ex = new Explosion(randX, randY, 0, big);
      explosions.add(ex);
    }
  }
}

void happy() {
  happy.display();
  if (!end.isPlaying()) end.play();
}

void levels() {
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

void music() {
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
    currMusic.amp(.1);
    currMusic.play();
  }
}
