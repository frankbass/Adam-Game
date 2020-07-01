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
    int choose = int(random(5));
    ex = explosionSounds.get(choose);
    ex.amp(.1);
    ex.play();
  }
  void display() {
    if (countDown > 0) {
      strokeWeight(1);
      noFill();
      stroke(255, 255, 0, 100);
      int diff = start - frameCount;
      push();
      translate(pos.x, pos.y, pos.z);
      for (int i = 0; i < size; i ++) {
        float size = float(i * diff)/3;
        ellipse(0, 0, size, size);
      }
      pop();
    }
    countDown --;
  }
}
