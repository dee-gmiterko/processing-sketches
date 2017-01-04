PImage img;

ArrayList<PVector> curve = new ArrayList<PVector>();

float r = 50.0f;
float s = 10.0f;
int CURVE_COUNT = 14;

void setup() {
  size(1000, 1000);
  //fullScreen();
  colorMode(HSB, 100, 100, 100); 
  background(58, 98, 16);
  //noStroke();
  noFill();
  //stroke(255);
  
  frameRate(15);
}

void draw() {
  
  background(58, 98, 16);
  
  if(mousePressed) {
    //translate(mouseX, mouseY);
    //rotate(frameCount / 21.0);
    
    PVector mv = new PVector(mouseX, mouseY);
    
    if(curve.size() == 0) {
      curve.add(mv);
    } else if(curve.get(curve.size()-1).dist(mv) > 100) {
      curve.add(mv);
    }
  }
  
  for(float j=0; j<CURVE_COUNT; j++) {
    stroke(58, lerp(98, 0, j/CURVE_COUNT), lerp(16, 100, j/CURVE_COUNT));
    beginShape();
    int i=0;
    for(PVector v : curve) {
      float l = 1 + (curve.size() - i) / 10.0;
      curveVertex(lerp(v.x, v.x + (noise(v.x) - 0.5) * r * l, j/CURVE_COUNT), lerp(v.y, v.y + (noise(v.y) - 0.5) * r * l, j/CURVE_COUNT));
      i++;
    }
    endShape();
  }
}

void keyPressed() {
  if(key == 's') saveFrame("####.png");  
}