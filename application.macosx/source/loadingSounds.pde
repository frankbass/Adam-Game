void loadingSounds() {
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
