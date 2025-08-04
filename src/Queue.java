import java.util.ArrayList;

public class Queue {
    private ArrayList<Node> queueList;

    public Queue() {
        queueList = new ArrayList<>();
    }

    public void enqueue(Node addNode) {
        int i = 0;
        // Determine the postion of the node in the queue list
        while (i < queueList.size() && (queueList.get(i).getCost() <= addNode.getCost())) {
            i++;
        }
        queueList.add(i, addNode);
    }

    public Node dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node n = queueList.get(0); // Get the bottom node of the list
        queueList.remove(n);
        return n;
    }

    public boolean isEmpty() {
        return queueList.isEmpty();
    }
}