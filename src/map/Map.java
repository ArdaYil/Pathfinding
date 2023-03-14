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
    private int[][] map = new int[50][50];

    private Panel panel;

    public Map(Panel panel) {
        this.initializeTiles();
        this.constructMap();
        this.panel = panel;
    }

    public void computePath(Pathfinding pathfinding) {
        pathfinding.computePath();

        System.out.println(pathfinding.open);

        for (Node node : pathfinding.open) {
            this.map[node.getX()][node.getY()] = 3;
        }

        for (Node node : pathfinding.closed) {
            this.map[node.getX()][node.getY()] = 4;
        }

        this.map[pathfinding.start.getX()][pathfinding.start.getY()] = 1;
        this.map[pathfinding.goal.getX()][pathfinding.goal.getY()] = 1;
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

                for (int col = 0; col < 50; col++) {
                    this.map[col][row] = Integer.parseInt(tiles[col]);
                }

                row++;

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
    }

    public int getTileNum(int col, int row) {
        return this.map[col][row];
    }

    public Dimension getTilePosition(int num) {
        for (int col = 0; col < 50; col++) {
            for (int row = 0; row < 50; row++) {
                int number = map[col][row];

                if (number == num) return new Dimension(col, row);
            }
        }

        return null;
    }

    public void draw(Graphics2D g2) {
        int nodeSize = this.panel.nodeSize;

        for (int col = 0; col < 50; col++) {
            for (int row = 0; row < 50; row++) {
                int number = this.map[col][row];
                Tile tile = this.tiles[number];

                g2.setColor(new Color(0, 0, 0));
                g2.drawRect(col * nodeSize, row * nodeSize, nodeSize, nodeSize);
                g2.setColor(tile.getColor());
                g2.fillRect(col * nodeSize + 1, row * nodeSize + 1, nodeSize - 1, nodeSize - 1);
            }
        }
    }
}
