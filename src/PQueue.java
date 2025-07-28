public class PQueue {
    private Queue highP;
    private Queue lowP;

    public PQueue() {
        highP = new Queue();
        lowP = new Queue();
    }

    public void enqueue (Node addNode, boolean priority) {
        if (priority) {
            highP.enqueue(addNode);
        } else  {
            lowP.enqueue(addNode);
        }
    }

    public Node dequeue() {
        if (highP.isEmpty()) {
            return lowP.dequeue();
        } else {
            return highP.dequeue();
        }
    }

    public boolean isEmpty() {
        System.out.println("High"+highP.isEmpty());
        System.out.println("Low"+lowP.isEmpty());
        if (highP.isEmpty() && lowP.isEmpty()) {
            return true;
        } else {
            return false;
        }
    } 
}
