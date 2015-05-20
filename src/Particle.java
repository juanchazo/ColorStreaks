import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Particle {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private Color color;
    private float alpha = 1f;
    
    public Particle(int d, int e, int dx, int dy, int size, int life, Color c) {
        this.x = d;
        this.y = e;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.color = c;
    }
    
    public boolean update() {
        x += dx;
        y += dy;
        life--;
        alpha += -0.05f;
        if (alpha <= 0) alpha = 0;
        if(life <= 0) 
            return true;
        return false;
    }
    
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha));
        g2d.setColor(color);
       
        g2d.fillOval(x-(size/2), y-(size/2), size, size); 
        
        g2d.dispose();
    }
}
