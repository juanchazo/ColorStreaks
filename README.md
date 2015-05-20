# ColorStreaks
A simple particle system.

The dark side shows disappearing particles, the white side is a paintable canvas.
This can be altered by editing the width and height of g2d.fillRect() under public void render();

Setting the width/height to render.getWidth()/render.getHeight() will make the whole window dark. 
Commenting out g2d.fillRect() will leave the window as a blank canvas.

Adding more particles under the mouseDragged method under pollInput() causes slow downs over time.
Currently, my hypothesis is that the garbage collection system is sub-optimal. 
Further testing needs to be done.
