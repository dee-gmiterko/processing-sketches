

void setup() {
  size(800, 800);
  colorMode(HSB, 360, 100, 100);
}

void draw() {

  background(0);
  
  translate(width/2, height/2);
  
  float r = 300;
  float a = 0;
  float size = 0.1;
  
  while (r > 0) {
    
    fill((frameCount+random(-8, 8))%360, 100, r);
    
    float x = cos(a) * r;
    float y = sin(a) * r;
    ellipse(x,y, 20+((PI*r*2)/PI*size), 20+((PI*r*2)/PI*size));
    
    a += 2.3 * sin(frameCount / 1000.0);
    r -= 0.5;
  }
  
  //noLoop();
  
}

void keyPressed() {
  if (key == 's' || key == 'S') saveFrame("###.png");
}