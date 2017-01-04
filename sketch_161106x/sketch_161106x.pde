ArrayList<PVector> points = new ArrayList<PVector>();
float rotation;
float RANDOM_DIST = 300;

void setup() {
  //size(800,800,P3D);
  fullScreen(P3D);
  colorMode(HSB, 360, 100, 100);
}

void draw() {
  
  background(0);
  
  translate(width/2, height/2);
  rotateY(rotation);
  
  for(int i=0; i<points.size(); i++) {
    PVector ppoint = points.get(i);
    PVector point = points.get((i+1)%points.size());
    
    color c = color(i % 360, 100, 100);
    fill(c);
    stroke(c);
    
    line(ppoint.x, ppoint.y, ppoint.z, point.x, point.y, point.z);
    
    pushMatrix();
    translate(point.x, point.y, point.z);
    rectMode(CENTER);
    rotateY(-rotation);
    rect(0,0,10,10);
    popMatrix();
    
  }
  
  rotation += 0.01;
  
  if(mousePressed && frameCount % 2 == 0) {
    float u = mouseY - height/2 + random(RANDOM_DIST)-RANDOM_DIST/2;
    float v = mouseX - width/2 + random(RANDOM_DIST)-RANDOM_DIST/2;
    float x = cos(rotation) * v;
    float z = sin(rotation) * v;
    points.add(new PVector(x, u, z));
  }
}

void keyPressed()
{
  if(key == 's')
    saveFrame("###.png");
}