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

    public String getToNodeString() {
        String[] nodeNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        return nodeNames[this.toNode];
    }

    public void decreaseLinkToNode() {
        this.toNode--;
    }

    public int getWeight() {
        return this.weight;
    }

}
