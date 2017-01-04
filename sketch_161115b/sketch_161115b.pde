void setup() {
  size(700, 700);
  background(0);
  colorMode(HSB, 360, 100, 100); 
  noStroke();
}

void draw() {
  blendMode(SUBTRACT);
  fill(3);
  rect(0, 0, width, height);
  blendMode(BLEND);
  
  for(int i=0; i<3; i++) {
    
    pushMatrix();
    translate(100 + f((frameCount/300.0)%1) * (width - 200), 100 + f(0.25+((frameCount/300.0)%1)) * (height - 200));
    rotate(frameCount/50.0);
    fill(frameCount%360, 80, 80);
    rect(-50,-50,100,100);
    popMatrix();
    
    frameCount -= 100;
  }
  frameCount += 3*100;
}

float f(float t) {
  if(t<0.25) {
    return 0;
  }
  if(t<0.50) {
    return ((t-0.25)*4);
  }
  if(t<0.75) {
    return 1;
  }
  if(t<1.0) {
    return 1-((t-0.75)*4);
  }
  return 0; //error
}

void keyPressed() {
  if(key == 's') saveFrame("Frame-####.png");  
}