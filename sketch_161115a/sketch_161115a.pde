import processing.pdf.*;
import java.util.Calendar;
import java.util.List;

class DropNode {

  float x;
  float y;
  float r;
  
  float v = 1;
  
  float red = 0;
  float green = 0;
  float blue = 255;
  
  int life = 1;
  boolean falling = true;
  
  DropNode(float x, float y, float r) {
    this.x = x;
    this.y = y;
    this.r = r;
    
    if (this.r < 6) {
      falling = true;
    }
  }
  
    void tick() {
      y += v;
      v *= (random(1, 1.05f));
  }
  
  void draw(float px, float py) {
    fill(red, green, blue);
    stroke(red, green, blue);
    strokeWeight(2);
    line(px, py, x, y);
    ellipse(x, y, r, r);
  }
  
}

boolean savePDF = false;

int maxCount = 5000; //max count of the cirlces
int currentCount = 1;
List<DropNode> dropNodes = new ArrayList<DropNode>();
boolean circle = true;

void setup() {
  size(1000, 1000);
  smooth();
  frameRate(1000);

  // first circle
  //treeNodes.add(new TreeNode(width/2, height/2, 10));
  //r[0] = 400; 
}


void draw() {
  if (savePDF) beginRecord(PDF, timestamp()+".pdf");
  //background(206, 225, 255);
  
  blendMode(ADD);
  fill(50);
  rect(0, 0, width, height);
  blendMode(BLEND);

  strokeWeight(0.5);
  //noFill();


  for(int i=0; i<10; i++) {
    dropNodes.add(new DropNode(random(0, width), -100, 4));
  }

  for (int i = 0; i < dropNodes.size(); ++i) {
    DropNode dp = dropNodes.get(i);
    
    float px = dp.x;
    float py = dp.y;
    
    float dx =dp.x - width/2;
    float dy =dp.y - height/2;
    
    if(circle) {
      if ((dx*dx+dy*dy) < 200*200) {
        dp.x += dp.x > width/2 ? 1 : -1;
        dp.v = 1;  
    } else {
        dp.tick();
      }
    } else {
      if ((Math.abs(dx)+Math.abs(dy)) < 200) {
        dp.x += dp.x > width/2 ? 1 : -1;
        dp.v = 1;
      } else {
        dp.tick();
      }
    }
    
    for (int j = 0; j < dropNodes.size(); ++j) {
      PVector vec = new PVector(dp.x-dropNodes.get(j).x, dp.y-dropNodes.get(j).y);
      if(vec.mag() < 2) {
        vec.setMag(2);
        dp.x += vec.x;
        dp.y += vec.y;
      }
    }
    
    dp.draw(px, py);
  }
  
  if(frameCount % 10 == 0) {
    for (int i = dropNodes.size()-1; i >= 0; --i) {
      if(dropNodes.get(i).y>height) {
        dropNodes.remove(i);
      }
    }
  }
  
  //filter(BLUR, 2);

  //dropNodes.removeIf(dp -> dp.y > height);

  if (savePDF) {
    savePDF = false;
    endRecord();
  }
}

void keyReleased() {
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_##.png");
  if (key == 'p' || key == 'P') savePDF = true;
  if (key == 't') circle = !circle;
}

// timestamp
String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}