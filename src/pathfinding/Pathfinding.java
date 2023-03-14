package pathfinding;

import map.Map;
import util.Dimension;
import visualization.Panel;

import java.util.LinkedList;

public class Pathfinding {
    private int straight = 10;
    private Panel panel;
    private Node start;
    private Node goal;
    private LinkedList<Node> closed = new LinkedList<>();
    private LinkedList<Node> open = new LinkedList<>();

    public Pathfinding(Panel panel) {
        this.panel = panel;

        this.goal = new Node(this.getGoalFromMap());
        this.start = new Node(this.getStartFromMap());

        System.out.println(goal);
        System.out.println(start);
    }

    public LinkedList<Node> computePath() {

        Node currentNode = this.start;

        while (true) {
            Node[] surroundingNodes = this.getSurroundingNodes(currentNode);

            this.addToOpen(surroundingNodes);

            break;
        }

        System.out.println(currentNode);

        return closed;
    }

    public void setFCostToNodes(Node[] nodes) {

    }

    public void addToOpen(Node[] surroundingNodes) {
        for (int i = 0; i < surroundingNodes.length; i++) {
            Node surroundingNode = surroundingNodes[i];

            if (surroundingNode == null) continue;

            if (this.open.size() == 0) {
                for (int j = 0; j < this.open.size(); j++) {
                    Node openNode = this.open.get(j);

                    if (!openNode.position.equals(surroundingNode.position)) continue;

                    this.open.remove(j);
                }
            }

            this.open.addLast(surroundingNode);
        }
    }

    private Node[] getSurroundingNodes(Node node) {
        Node[] array = new Node[8];
        int counter = 0;

        array[counter++] = new Node(new Dimension(node.getX() - 1, node.getY() - 1));
        array[counter++] = new Node(new Dimension(node.getX(), node.getY() - 1));
        array[counter++] = new Node(new Dimension(node.getX() + 1, node.getY() - 1));
        array[counter++] = new Node(new Dimension(node.getX() - 1, node.getY()));
        array[counter++] = new Node(new Dimension(node.getX() + 1, node.getY()));
        array[counter++] = new Node(new Dimension(node.getX() - 1, node.getY() + 1));
        array[counter++] = new Node(new Dimension(node.getX(), node.getY() + 1));
        array[counter] = new Node(new Dimension(node.getX() + 1, node.getY() + 1));

        return array;
    }

    private Dimension getGoalFromMap() {
        return this.panel.map.getTilePosition(1);
    }

    private Dimension getStartFromMap() {
        return this.panel.map.getTilePosition(2);
    }

    private class Node {
        private Dimension position;
        private int gCost;
        private int hCost;
        private int fCost;

        public Node(Dimension position) {
            this.position = position;
        }

        public int getX() {
            return this.position.getX();
        }

        public int getY() {
            return this.position.getY();
        }

        private int getGCost() {
            return 1;
        }

        private int getHCost() {
            return 1;
        }

        private void setFCost() {
            int gCost = this.getGCost();
            int hCost = this.getHCost();

            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }

        @Override
        public String toString() {
            return "Node: { gCost: " +
                    this.gCost + ", hCost: " +
                    this.hCost + ", fCost: " +
                    this.fCost + ", " +
                    this.position;
        }
    }
}
