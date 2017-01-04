// P_2_2_4_01.pde
// 
// Generative Gestaltung, ISBN: 978-3-87439-759-9
// First Edition, Hermann Schmidt, Mainz, 2009
// Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
// Copyright 2009 Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
//
// http://www.generative-gestaltung.de
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * limited diffusion aggregation 
 * 
 * KEYS
 * s                   : save png
 * p                   : save pdf
 */

import processing.pdf.*;
import java.util.Calendar;

boolean savePDF = false;

int maxCount = 400; //max count of the cirlces
int currentCount = 1;
float[] x = new float[maxCount];
float[] y = new float[maxCount];
float[] r = new float[maxCount]; // angle
PShape shape = null;

void setup() {
  size(800,800);
  //fullScreen();
  smooth();
  //frameRate(15);
  
  noStroke();
  
  colorMode(HSB, 360, 100, 100);

  shape = createShape();
  shape.beginShape();
  shape.vertex(20, 0);
  shape.vertex(-20, 10);
  shape.vertex(-20, -10);
  shape.endShape(CLOSE);

  // first circle
  x[0] = width/2;
  y[0] = height;
  r[0] = PI/2; 
}


void draw() {
  if (savePDF) beginRecord(PDF, timestamp()+".pdf");
  background(0, 0, 0);

  strokeWeight(0.5);
  float dist = 20;

  // create a radom set of parameters
  float newX = random(0+10, width-10);
  float newY = random(0+10, height-10);

  float closestDist = 100000000;
  int closestIndex = 0;
  // which circle is the closest?
  for(int i=0; i < currentCount; i++) {
    float newDist = dist(newX,newY, x[i],y[i]);
    if (newDist < closestDist) {
      closestDist = newDist;
      closestIndex = i; 
    } 
  }

  // show random position and line
  // fill(230);
  // ellipse(newX,newY,newR*2,newR*2); 
  // line(newX,newY,x[closestIndex],y[closestIndex]);

  // aline it to the closest circle outline
  float angle = atan2(newY-y[closestIndex], newX-x[closestIndex]);

  x[currentCount] = x[closestIndex] + cos(angle) * dist;
  y[currentCount] = y[closestIndex] + sin(angle) * dist;
  r[currentCount] = angle;
  currentCount++;

  // draw them
  for (int i=0 ; i < currentCount; i++) {
    pushMatrix();
    
    translate(x[i], y[i]);
    rotate(r[i]);
    
    float it = ((float)i/maxCount);
    
    shape.resetMatrix();
    shape.setFill(color(350 - 60*it, 100, 60 + 40 * it));
    shape.scale(1-it);
    
    shape(shape);
    
    popMatrix();
  }

  if (currentCount >= maxCount) noLoop();

  if (savePDF) {
    savePDF = false;
    endRecord();
  }
}

void keyReleased() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_##.png");
  if (key == 'p' || key == 'P') savePDF = true;
}

// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}