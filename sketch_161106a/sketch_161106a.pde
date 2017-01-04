class Cell
{
  PVector loc;
  float life;
  Cell(float x, float y)
  {
    loc = new PVector(x, y);
  }
}

int INCREASE_STEPS = 4;
float INCREASE_SPEED = -1;
float DECREASE_SPEED = -0.02;

ArrayList<Cell> cells = new ArrayList<Cell>();
Cell[][] cellCache;
boolean use_manhattan = false;
void setup()
{
  size(800, 800);
  //fullScreen();
  
  colorMode(HSB);
  
  for(int i = 0; i < 100; ++i)
    cells.add(new Cell(random(0,width), random(0,height)));
   
  cacheCells();
}

void draw()
{
  background(0);
  
  // Draw the voronoi cells
  drawVoronoi();
  
  // Draw the points
  //for(int i = 0; i < cells.size(); ++i)
  //  ellipse(cells.get(i).loc.x, cells.get(i).loc.y, 5, 5);
}

void drawVoronoi()
{
  loadPixels();
  int offset = 0;
  
  //Increase life at mouse position
  for(float i=0; i<INCREASE_STEPS; i++) {
    Cell cell = getClosest((int)lerp(mouseX, pmouseX, i/INCREASE_STEPS), (int)lerp(mouseY, pmouseY, i/INCREASE_STEPS));
    cell.life = min(1, cell.life + INCREASE_SPEED);
  }
  
  // Iterate over every pixel
  for(int y = 0; y < height; y++)
  {
    for(int x = 0; x < width; x++)
    {
      // Set the pixel to the cells color
      Cell cc = getClosest(x, y);
      float d = dist(x, y, cc.loc.x, cc.loc.y);
      pixels[offset++] = lifeColor(cc, d);
    }
  }
  
  // decrease life
  for(int i = 0; i < cells.size(); ++i) {
      Cell cc = cells.get(i);
      cc.life = max(0, cc.life - DECREASE_SPEED);
  }
  
  updatePixels();
}

Cell getClosest(int x, int y) {
  return getClosest(x, y, false);
}

Cell getClosest(int x, int y, boolean forceRecalc) {
  
  if(forceRecalc) {
    
    float shortest = 1E12;
    int index = -1;
    
    for(int i = 0; i < cells.size(); ++i)
    {
      Cell cc = cells.get(i);
      float d;
      
      
      if(use_manhattan)
      {
        // Manhattan Distance
        d = abs(x - cc.loc.x) + abs(y - cc.loc.y);
      }
      else
      {
        // Euclidean distance, dont need to sqrt it since actual distance isnt important
        d = dist(x, y, cc.loc.x, cc.loc.y);
      }
      
      if(d < shortest)
      {
        shortest = d;
        index = i;
      }
    }
    
    return cells.get(index);
    
  } else {
    return cellCache[x][y];
  }
}

void cacheCells() {
  cellCache = new Cell[width][height]; 
  // Iterate over every pixel
  for(int y = 0; y < height; y++)
  {
    for(int x = 0; x < width; x++)
    {
      cellCache[x][y] = getClosest(x, y, true);
    }
  }
}

color lifeColor(Cell cc, float d) {
  return color((cc.loc.x * cc.loc.y) % 360, 80, cc.life * 100 * (200/d));
}

void keyPressed()
{
  if(key == 's')
    saveFrame("###.png");
  if(key == 't') {
    use_manhattan = !use_manhattan;
    cacheCells();
  }
}