package map;

import java.awt.Color;

public class Tile {
    private Color color;

    public Tile(int r, int g, int b) {
        this.setColor(new Color(r, g, b));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
