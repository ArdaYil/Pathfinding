package map;

import visualization.Panel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Map {
    private Tile[] tiles = new Tile[10];
    private int[][] map = new int[50][50];

    private Panel panel;

    public Map(Panel panel) {
        this.initializeTiles();
        this.constructMap();
        this.panel = panel;
    }

    public void constructMap() {
        try(var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/Map.txt")));) {
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
        this.tiles[1] = new Tile(0, 0, 0);
    }

    public void draw(Graphics2D g2) {
        int nodeSize = this.panel.nodeSize;
        for (int col = 0; col < 50; col++) {
            for (int row = 0; row < 50; row++) {
                int number = this.map[col][row];
                Tile tile = this.tiles[number];

                g2.setColor(tile.getColor());
                g2.drawRect(col * nodeSize, row * nodeSize, nodeSize, nodeSize);
            }
        }
    }
}
