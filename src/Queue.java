import java.util.ArrayList;

public class Queue {
    private ArrayList<Node> queueList;
    private Node frontNode;
    private Node backNode;

    public Queue() {
        queueList = new ArrayList<>();
        frontNode = null;
        backNode= null;
    }

    public void enqueue(Node addNode) {
        queueList.add(addNode);
        backNode = addNode;
    }

    public Node dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node node = queueList.get(0);
        queueList.remove(frontNode);
        frontNode = queueList.get(0);
        return node;
    }

    public boolean isEmpty() {
        return (frontNode == backNode);
    }

    public Node peek() {
        if (isEmpty()) {
            return null;
        }
        return queueList.get(0);
    }
}