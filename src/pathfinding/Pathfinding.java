package pathfinding;

import map.Map;
import util.Dimension;
import visualization.Panel;

public class Pathfinding {
    private int straight = 10;
    private Panel panel;
    private Dimension[] path;
    private Dimension start;
    private Dimension goal;

    public Pathfinding(Panel panel) {
        this.panel = panel;

        this.goal = this.getGoalFromMap();
        this.start = this.getStartFromMap();

        System.out.println(goal);
        System.out.println(start);
    }

    private Dimension[] getSurroundingNodes(Dimension dimension) {
        Dimension[] array = new Dimension[8];
        int counter = 0;

        array[counter++] = new Dimension(dimension.getX() - 1, dimension.getY() - 1);
        array[counter++] = new Dimension(dimension.getX(), dimension.getY() - 1);
        array[counter++] = new Dimension(dimension.getX() + 1, dimension.getY() - 1);
        array[counter++] = new Dimension(dimension.getX() - 1, dimension.getY());
        array[counter++] = new Dimension(dimension.getX() + 2, dimension.getY());
        array[counter++] = new Dimension(dimension.getX() - 1, dimension.getY() + 1);
        array[counter++] = new Dimension(dimension.getX(), dimension.getY() + 1);
        array[counter] = new Dimension(dimension.getX() + 1, dimension.getY() + 1);

        return array;
    }

    private int getGCost() {

    }

    private int getHCost() {

    }

    private int getFCost() {

    }

    private Dimension getGoalFromMap() {
        return this.panel.map.getTilePosition(1);
    }

    private Dimension getStartFromMap() {
        return this.panel.map.getTilePosition(2);
    }
}
