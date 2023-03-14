package pathfinding;

import map.Map;
import util.Dimension;
import visualization.Panel;

import java.util.LinkedList;

public class Pathfinding {
    private int straight = 10;
    private Panel panel;
    public Node start;
    public Node goal;
    public LinkedList<Node> closed = new LinkedList<>();
    public LinkedList<Node> open = new LinkedList<>();

    public Pathfinding(Panel panel) {
        this.panel = panel;

        this.goal = new Node(this.getGoalFromMap());
        this.start = new Node(this.getStartFromMap());

        this.start.gCost = 0;
        this.goal.hCost = 0;
        this.goal.fCost = 0;
    }

    public LinkedList<Node> computePath() {

        Node currentNode = this.start;

        int count = 0;

        while (true) {
            Node[] surroundingNodes = this.getSurroundingNodes(currentNode);

            this.setFCostToNodes(surroundingNodes, currentNode);
            this.addToOpen(surroundingNodes);
            this.closed.addLast(currentNode);

            currentNode = this.getNewNode();

            count++;

            if (count == 1000) break;

            if (currentNode.position.equals(this.goal.position)) break;
        }

        return closed;
    }

    private void setFCostToNodes(Node[] nodes, Node root) {
        for (Node node : nodes) {
            node.setFCost(root);
        }
    }

    private void addToOpen(Node[] surroundingNodes) {
        for (int i = 0; i < surroundingNodes.length; i++) {
            Node surroundingNode = surroundingNodes[i];

            if (surroundingNode == null) continue;
            if (this.isClosed(surroundingNode)) continue;
            if (this.panel.map.isObstacle(surroundingNode)) continue;

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

    private boolean isClosed(Node node) {
        for (Node checkNode : this.closed) {
            if (node == checkNode) return true;
        }

        return false;
    }

    private Node getNewNode() {
        LinkedList<Node> currentNodes = new LinkedList<>();

        int lowest = this.open.getFirst().fCost;

        for (Node node : this.open) {
            if (node.fCost == lowest) {
                currentNodes.addLast(node);
            }

            else if (node.fCost < lowest) {
                currentNodes = new LinkedList<>();
                currentNodes.addLast(node);
            }
        }

        int lowestHCost = currentNodes.getFirst().hCost;
        Node currentNode = currentNodes.getFirst();

        for (Node node : currentNodes) {
            if (node.hCost < lowestHCost) currentNode = node;
        }

        return currentNode;
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

    public class Node {
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

        private int getGCost(Node root) {
            int x = Math.abs(this.getX() - root.getX());
            int y = Math.abs(this.getY() - root.getY());

            return x + y + root.gCost;
        }

        private int getHCost() {
            int x = Math.abs(goal.getX() - this.getX());
            int y = Math.abs(goal.getY() - this.getY());

            return x + y;
        }

        private void setFCost(Node root) {
            int gCost = this.getGCost(root);
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
