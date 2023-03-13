package visualization;

import map.Map;

import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel {
    private static final int rows = 50;
    private static final int cols = 50;
    public static final int nodeSize = 16;
    private static final int screenWidth = cols * nodeSize;
    private static final int screenHeight = rows * nodeSize;

    private Map map;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.map = new Map(this);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D)g;

        this.map.draw(g2);
    }
}
