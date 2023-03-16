package map;

import pathfinding.Pathfinding.Node;
import pathfinding.Pathfinding;
import util.Dimension;
import visualization.Panel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

public class Map {
    private Tile[] tiles = new Tile[10];
    private int[][] map = new int[this.panel.cols][this.panel.rows];

    private Panel panel;

    public Map(Panel panel) {
        this.initializeTiles();
        this.constructMap();
        this.panel = panel;
    }

    public void computePath(Pathfinding pathfinding) {
        LinkedList<Node> value = pathfinding.computePath();

        if (value == null) System.out.println("Path Not Found");
    }

    public boolean isObstacle(Node node) {
        return this.map[node.getX()][node.getY()] == 5;
    }

    public void constructMap() {
        try(var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mapFolder/map.txt")));) {
            String line = reader.readLine();

            int row = 0;

            while (line != null) {
                String[] tiles = line.split(" ");

                for (int col = 0; col < this.panel.cols; col++) {
                    this.map[col][row] = Integer.parseInt(tiles[col]);
                }

                row++;

                if (row == this.panel.rows) break;

                line = reader.readLine();
            }
        }

        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initializeTiles() {
        this.tiles[0] = new Tile(255, 255, 255);
        this.tiles[1] = new Tile(0, 0, 255);
        this.tiles[2] = new Tile(0, 0, 255);
        this.tiles[3] = new Tile(0, 255, 0);
        this.tiles[4] = new Tile(255, 0, 0);
        this.tiles[5] = new Tile(0, 0, 0);
        this.tiles[6] = new Tile(255, 255, 0);
    }

    public int getTileNum(int col, int row) {
        return this.map[col][row];
    }

    public Dimension getTilePosition(int num) {
        for (int col = 0; col < this.panel.cols; col++) {
            for (int row = 0; row < this.panel.rows; row++) {
                int number = map[col][row];

                if (number == num) return new Dimension(col, row);
            }
        }

        return null;
    }

    public void draw(Graphics2D g2) {
        int nodeSize = this.panel.nodeSize;

        /*for (Node node : this.panel.pathfinding.closed) {
            this.map[node.getX()][node.getY()] = 4;
        }

        for (Node node : this.panel.pathfinding.open) {
            this.map[node.getX()][node.getY()] = 3;
        }*/

        for (Node node : this.panel.pathfinding.path) {
            this.map[node.getX()][node.getY()] = 6;
        }

        this.map[this.panel.pathfinding.start.getX()][this.panel.pathfinding.start.getY()] = 1;
        this.map[this.panel.pathfinding.goal.getX()][this.panel.pathfinding.goal.getY()] = 1;

        for (int col = 0; col < this.panel.cols; col++) {
            for (int row = 0; row < this.panel.rows; row++) {
                int number = this.map[col][row];
                Tile tile = this.tiles[number];

                g2.setColor(new Color(0, 0, 0));
                g2.drawRect(col * nodeSize, row * nodeSize, nodeSize, nodeSize);
                g2.setColor(tile.getColor());
                g2.fillRect(col * nodeSize + 1, row * nodeSize + 1, nodeSize - 1, nodeSize - 1);

                /*int fCost1 = this.panel.pathfinding.getFCostFromOpen(new Dimension(col, row));
                int fCost2 = this.panel.pathfinding.getFCostFromClosed(new Dimension(col, row));*/

                g2.setColor(new Color(0, 0, 0));

                /*if (fCost2 != -1)
                    g2.drawString(Integer.toString(fCost2), (col * nodeSize + nodeSize/2), (row * nodeSize + nodeSize/2));

                else if (fCost1 != -1)
                    g2.drawString(Integer.toString(fCost1), (col * nodeSize + nodeSize/2), (row * nodeSize + nodeSize/2));*/
            }
        }
    }
}
