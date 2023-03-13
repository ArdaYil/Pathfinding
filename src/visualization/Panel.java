package visualization;

import map.Map;
import pathfinding.Pathfinding;

import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    private static final int rows = 50;
    private static final int cols = 50;
    public static final int nodeSize = 16;
    private static final int screenWidth = cols * nodeSize;
    private static final int screenHeight = rows * nodeSize;

    private Thread thread;
    public Map map;
    private Pathfinding pathfinding;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.thread = new Thread(this);
        this.thread.start();
        this.map = new Map(this);
        this.pathfinding = new Pathfinding(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D)g;

        this.map.draw(g2);

        g2.dispose();
    }

    @Override
    public void run() {
        while (this.thread != null) {
            try {
                Thread.sleep(1000/60);
            }

            catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            this.repaint();
        }
    }
}
