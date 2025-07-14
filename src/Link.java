public class Link {
    private int toNode;
    private int weight;

    public Link(int to, int weight) {
        this.toNode = to;
        this.weight = weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getToNode() {
        return this.toNode;
    }
    public void decreaseLinkToNode() {
        this.toNode--;
    }

    public int getWeight() {
        return this.weight;
    }

}
