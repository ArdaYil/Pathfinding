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
    public LinkedList<Node> path = new LinkedList<>();
    public LinkedList<Node> closed = new LinkedList<>();
    public LinkedList<Node> open = new LinkedList<>();

    public Pathfinding(Panel panel) {
        this.panel = panel;

        this.goal = new Node(this.getGoalFromMap(), null);
        this.start = new Node(this.getStartFromMap(), null);

        this.start.gCost = 0;
        this.goal.hCost = 0;
        this.goal.fCost = 0;
    }

    public LinkedList<Node> computePath() {
        Node currentNode = this.start;

        int count = 0;

        while (true) {
            /*try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/

            Node[] surroundingNodes = this.getSurroundingNodes(currentNode);

            this.setFCostToNodes(surroundingNodes, currentNode);
            this.addToOpen(surroundingNodes);
            this.closed.addLast(currentNode);

            int index = this.getIndexOfNodeInOpen(currentNode);
            if (index >= 0) this.open.remove(index);

            Node parent = currentNode;

            if (this.open.isEmpty()) return null;

            currentNode = this.getNewNode();

            count++;

            if (count == 1000) break;

            if (currentNode == null) break;
            if (currentNode.position.equals(this.goal.position)) {
                this.goal.parent = parent;
                break;
            }
        }

        this.createPath();

        return closed;
    }

    private void createPath() {
        Node currentNode = this.goal;

        while (currentNode != null) {
            this.path.addLast(currentNode);

            Node[] surroundingNodes = this.getSurroundingNodes(currentNode);

            for (Node surroundingNode : surroundingNodes) {
                if (surroundingNode == null) continue;

                Dimension goal = this.getGoalFromMap();
                if (goal == null) break;
                if (goal.equals(surroundingNode.position)) {
                    //currentNode = surroundingNode;

                    return;
                }
            }

            currentNode = currentNode.parent;
        }
    }

    public int getIndexOfNodeInOpen(Node node) {
        int counter = 0;

        for (Node openNode : this.open) {
            if (openNode.position.equals(node.position)) return counter;
            counter++;
        }

        return -1;
    }

    private void setFCostToNodes(Node[] nodes, Node root) {
        for (Node node : nodes) {
            if (node == null) continue;
            if (this.isClosed(node)) continue;

            node.setFCost(root);
        }
    }

    private void addToOpen(Node[] surroundingNodes) {
        for (int i = 0; i < surroundingNodes.length; i++) {
            Node surroundingNode = surroundingNodes[i];

            if (surroundingNode == null) continue;
            if (this.isClosed(surroundingNode)) continue;
            if (this.panel.map.isObstacle(surroundingNode)) continue;

            for (int j = 0; j < this.open.size(); j++) {
                Node openNode = this.open.get(j);

                if (!openNode.position.equals(surroundingNode.position)) continue;

                this.open.remove(j);
                break;
            }

            this.open.addLast(surroundingNode);
        }
    }

    private boolean isClosed(Node node) {
        for (Node checkNode : this.closed) {
            if (node.position.equals(checkNode.position)) return true;
        }

        return false;
    }

    private Node getNewNode() {
        LinkedList<Node> currentNodes = new LinkedList<>();

        Node first = this.open.getFirst();
        int lowest = first.fCost;
        currentNodes.addLast(first);

      /*  for (Node node : this.open) {
            if (this.isClosed(node)) continue;

            lowest = node.fCost;
            first = node;
            break;
        }*/

        System.out.println(this.open.size());

        for (Node node : this.open) {
            if (node.position.equals(first.position)) continue;
           /* if (this.isClosed(node)) continue;*/

            if (node.fCost == lowest) {
                currentNodes.addLast(node);
            }

            else if (node.fCost < lowest) {
                currentNodes = new LinkedList<>();
                currentNodes.addLast(node);
                lowest = node.fCost;
            }
        }

        if (currentNodes.size() == 0) return null;

        int lowestHCost = currentNodes.getFirst().hCost;
        Node currentNode = currentNodes.getFirst();

        for (Node node : currentNodes) {
            if (node.hCost < lowestHCost) {
                currentNode = node;
                lowestHCost = node.hCost;
            };
        }

        return currentNode;
    }

    private Node[] getSurroundingNodes(Node node) {
        Node[] array = new Node[4];
        int counter = 0;

        array[counter++] = new Node(new Dimension(node.getX(), node.getY() - 1), node);
        array[counter++] = new Node(new Dimension(node.getX() - 1, node.getY()), node);
        array[counter++] = new Node(new Dimension(node.getX() + 1, node.getY()), node);
        array[counter] = new Node(new Dimension(node.getX(), node.getY() + 1), node);

        int count = 0;

        for (Node surroundingNode : array) {
            int x = surroundingNode.getX();
            int y = surroundingNode.getY();

            if (x < 0 || x >= this.panel.cols) array[count] = null;
            else if (y < 0 || y >= this.panel.cols) array[count] = null;

            count++;
        }

        return array;
    }

    private Dimension getGoalFromMap() {
        return this.panel.map.getTilePosition(1);
    }

    private Dimension getStartFromMap() {
        return this.panel.map.getTilePosition(2);
    }

    public int getFCostFromOpen(Dimension position) {
        for (Node node : this.open) {
            if (node.position.equals(position)) return node.hCost;
        }

        return -1;
    }

    public int getFCostFromClosed(Dimension position) {
        for (Node node : this.closed) {
            if (node.position.equals(position)) return node.hCost;
        }

        return -1;
    }

    public class Node {
        private Dimension position;
        private int gCost;
        private int hCost;
        private int fCost;
        private Node parent;

        public Node(Dimension position, Node parent) {
            this.position = position;
            this.parent = parent;
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
