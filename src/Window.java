import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window extends JFrame {

    private ArrayList<Particle> particles = new ArrayList<Particle>(50);

    private int x = 0;
    private int y = 0;
    private float h = 0f;
    private float s = 0.7f;
    private float b = 0.8f;
    private float alpha = 1f;
    private BufferStrategy bufferstrat = null;
    private Canvas render;

    public static void main(String[] args) {
        Window window = new Window(600, 400, "Color Streaks");
        window.pollInput();
        window.loop();
    }

    public void pollInput() {
        // Adds particles on mouse drag.
        render.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                // Adding more particles will slow down performance.
                // Better optimization is required.
                // Lowering the value of life helps but is not a fix.
                addParticle();
                addParticle();
            }

            public void addParticle() {
                int dx, dy;
                // Controls the hue for the HSB color method.
                h += 0.001f;
                if (h > 1f)
                    h = 0f;
                // Controls direction of particle flight.
                dx = (int) (Math.random() * 5 - 2.5);
                dy = (int) (Math.random() * 5 - 2.5);
                // Size and life duration of particles.
                int size = (int) (Math.random() * 12);
                int life = (int) (Math.random() * (20));
                // Color cycling.
                Color rainbow = Color.getHSBColor(h, s, b);
                // Adds the new particle to the particle array list.
                particles.add(new Particle(x, y, dx, dy, size, life, rainbow));
            }

            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    public Window(int width, int height, String title) {
        super();
        setTitle(title);
        setIgnoreRepaint(true); 
        setResizable(false);

        render = new Canvas();
        render.setIgnoreRepaint(true); 
        // Used to position the window on the center of the screen
        int nHeight = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getHeight();
        int nWidth = (int) Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth();
        nHeight /= 2;
        nWidth /= 2;

        setBounds(nWidth - (width / 2), nHeight - (height / 2), width, height);
        render.setBounds(nWidth - (width / 2), nHeight - (height / 2), width,
                height);

        add(render);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Buffer strategy for the rendering.
        render.createBufferStrategy(2);
        bufferstrat = render.getBufferStrategy();
    }

    public void loop() {
        while (true) {
            update();
            render();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update() {
        Point p = render.getMousePosition();
        if (p != null) {
            x = p.x;
            y = p.y;
        }
        if (particles != null) {
            for (int i = 0; i <= particles.size() - 1; i++) {
                if (particles.get(i) != null) {
                    if (particles.get(i).update())
                        particles.remove(i);
                }
            }
        }
    }

    public void render() {
        do {
            do {
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                // Paints background of window.
                g2d.fillRect(0, 0, render.getWidth()/2, render.getHeight());
                // Controls opacity of the graphics.
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, alpha));

                renderParticles(g2d);
                

                g2d.dispose();
            } while (bufferstrat.contentsRestored());
            bufferstrat.show();
        } while (bufferstrat.contentsLost());
    }

    public void renderParticles(Graphics2D g2d) {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i) != null) {
                System.out.println(particles.get(i));
                System.out.println(particles.size());
            particles.get(i).render(g2d);
            }
        }
    }
}
