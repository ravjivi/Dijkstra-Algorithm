import java.util.ArrayList;

public class Queue {
    private ArrayList<Node> queueList;

    public Queue() {
        queueList = new ArrayList<>();
    }

    public void enqueue(Node addNode) {
        int i = 0;
        while (i < queueList.size() && (queueList.get(i).getCost() <= addNode.getCost())) {
            i++;
        }
        queueList.add(i, addNode);
    }

    public Node dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node node = queueList.get(0);
        queueList.remove(node);
        return node;
    }

    public boolean isEmpty() {
        return queueList.isEmpty();
    }

    public Node peek() {
        if (isEmpty()) {
            return null;
        }
        return queueList.get(0);
    }
}