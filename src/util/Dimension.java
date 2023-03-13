package util;

public class Dimension {
    private int x;
    private int y;

    public Dimension(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Dimension: { x: " + this.x + ", y: " + this.y + " }";
    }
}
