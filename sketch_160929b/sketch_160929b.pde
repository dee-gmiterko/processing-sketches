
void setup() {
  size(800, 800);
  //noStroke();
  background(0);
  frameRate(5);
  colorMode(RGB);
}

int px, py;

void draw() {
  fill(#532D64);
  rect(0,0,800,800);
  
  px = 700;
  py = 0;
  
  fill(#B4AA18);

  float size = 100;
  float count = width / size;

  while(py < 800) {

    if(py < 200) {
      px += random(-100, 10);
    } else {
      if(py > 200 && py < 500) {
        px += random(-100, 200);
      } else {
        px += random(-100, 100);
      }
    }
    
    py += random(0, 60);
    
    for (int x=0; x<count; x++) {
      for (int y=0; y<count; y++) {
        quad(modx(size, x, y), mody(size, x, y), modx(size, x+1, y), mody(size, x+1, y), modx(size, x+1, y+1), mody(size, x+1, y+1), modx(size, x, y+1), mody(size, x, y+1));
      }
    }
    
  }
}

float modx(float size, float x, float y) {
  float xm = x * size;
  float ym = y * size;
  float d = dist(xm, ym, px, py);
  return xm + d;// + 1/((xm - mouseX));
}

float mody(float size, float x, float y) {
  float xm = x * size;
  float ym = y * size;
  float d = dist(xm, ym, px, py);
  return ym + d;
}

void keyPressed() {
  if(key == 's') {
    saveFrame("###.png");
  }
}