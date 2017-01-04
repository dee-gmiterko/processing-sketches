

import processing.pdf.*;
import java.util.Calendar;
//import controlP5.*;

boolean savePDF = false;
//ControlP5 controlP5;

int nagents = 6;
float[] agents = new float[nagents];
color[] agents_color = new color[nagents];
float[] agents_vel = new float[nagents];

void setup() {
  size(800, 800);
  //fullScreen();
  background(255);
  frameRate(120);

  //if (frame != null) {
  // frame.setResizable(true);
  //}
  
  //colorMode(HSB, 360, 100, 100);
  noStroke();
  
  setGUI();
  
  setupAgents();
  
  //noCursor();
}

void setupAgents() {
  colorMode(HSB, 360, 100, 100);
  float h = random(260);
  float d = random(5);
  
  for(int i=0; i<nagents; i++) {
    agents[i] = random(width);
    agents_color[i] = color((h + i*d)%360, random(20, 80), random(40, 90));
    agents_vel[i] = random(-5, 5);
  }
  
  //for(int i=0; i<nagents; i++) {
  //  agents[i] = random(width);
  //  agents_color[i] = color(random(255), random(255), random(255));
  //  agents_vel[i] = random(-5, 5);
  //}
}

/**
  *  mouseX, mouseY      current position of the mouse
  *  pmouseX, pmouseY    previous position of the mouse
  *
  *  width, height       width and hight of the window
  *
  *  line(x1, y1, x2, y2)              line from (x1, y1) to (x2, y2)
  *  rect(x, y, width, height)         rectangle
  *  ellipse(x_c, y_c, width, height)  ellipse
  *  triangle(x1, y1, x2, y2, x3, y3)  triangle
  *  point(x, y)                       point of size 1px
  *
  *  stroke(35)          color of stroke in grayscale
  *  stroke(25, 25, 25)  color of stroke in given color scale (RGB/HSB)
  *  noStroke()
  *  strokeWeight(3)     width of stroke in px
  *  fill(35) / fill(35, 35, 35)   fill color in grayscale / color scale
  *  noStroke()
  *
  */
void draw() {
  
  image(new PImage(this.g.getImage()), 0, -1);
  
  for(int i=0; i<nagents; i++) {
    
    agents[i] += agents_vel[i];
    if(agents[i] < 0) {
      agents[i] = width + agents[i];
    }
    if(agents[i] > width) {
      agents[i] = agents[i] - width;
    }
    
    fill(agents_color[i]);
    rect(agents[i]-20, height-1, 40, 1);
  
  }
}



// ----- User interactivity ----- 

void keyPressed() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_##.png");
  if (key == 'p' || key == 'P') savePDF = true; 
}

void mousePressed() {
  setupAgents();
}



// ----- ControlP5 GUI -----
void setGUI() {
    //controlP5 = new ControlP5(this);
    //controlP5.addButton("Save",1,70,10,60,20);
  }

//void controlEvent(ControlEvent theEvent) {
//  if(theEvent.isController()) { 
////   if(theEvent.controller().name()=="Save") {
//  if(theEvent.controller().getName()=="Save") {
//      savePDF = true;
//    }
//  }  
//}

// ----- Help methods -----
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}