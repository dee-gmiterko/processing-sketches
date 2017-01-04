PImage img;

void setup() {
  size(1000, 1000);
  background(0);
  colorMode(HSB, 360, 100, 100); 
  noStroke();
  
  img = loadImage("me.jpeg");
}

void draw() {
  //blendMode(SUBTRACT);
  //fill(3);
  //rect(0, 0, width, height);
  //blendMode(BLEND);
  
  float s = 8.0;
  
  float f = frameCount / 50.0;
  
  for(int x = 0; x<s; x++) {
    for(int y = 0; y<s; y++) {
      fill(noise(f + x, f + y) * 360, noise(f + y, f + x) * 100, 100);
      rect(x * width / s, y * height / s, width / s, height / s);
      
      blendMode(MULTIPLY);
      image(img, x * width / s, y * height / s, width / s, height / s);
      blendMode(BLEND);
    }
  }
  //rect(width / 2 * noise(frameCount / 80.0), height / 2 * noise(frameCount / 85.0), width * noise(frameCount / 90.0), height * noise(frameCount / 95.0));
}

void keyPressed() {
  if(key == 's') saveFrame("####.png");  
}