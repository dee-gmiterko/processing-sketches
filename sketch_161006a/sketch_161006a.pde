PFont font;

void setup() {
  size(800, 800);
  frameRate(5);
}

void draw() {
  
  float s = 8.0;
  
  background(0);
  
  textAlign(CENTER);
  
  for(int x = 0; x<s; x++) {
    
    textFont(createFont(PFont.list()[(int)random(PFont.list().length)], 29));
    
    for(int y = 0; y<s; y++) {
      //fill(random(), random(), random());
      text("Linda", x * width / s, y * height / s, width / s, height / s);
    }
  }
  
}

void keyPressed() {
  if (key == 's' || key == 'S') saveFrame("###.png");
}