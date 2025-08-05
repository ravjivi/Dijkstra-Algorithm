import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.io.*;
public class CSVEditor {
    File myCSV;
    public CSVEditor() {
        myCSV = new File("src/GraphData.csv"); // Using GraphData.csv file
    }
    /*
     * Reads node and link data from a CSV file in a defined format.
     * The first three lines specify:
     *  1. The number of nodes
     *  2. The name of the start node
     *  3. The name of the end node
     * The next 'n' lines define each node's name and position (x, y).
     * The following line defines the number of links, then the link data is read and links are created.
     */
    public void readCSV(Graph g) {
        int nNodes = 0; // Number of nodes
        int nLinks = 0; // Number of links
        try {
            Scanner myReader = new Scanner(myCSV); // Use scanner to read CSV
            ArrayList<Node> nodesList = g.getNodesList();

            while (myReader.hasNextLine()) { 
                // First 3 lines are always # of nodes, start node, and end node
                nNodes = Integer.parseInt(myReader.nextLine());
                String startNodeChar = myReader.nextLine();
                String endNodeChar = myReader.nextLine();
                
                for (int i=0; i<nNodes; i++) {
                    String line = myReader.nextLine(); // Read the entire line
                    String[] tempLine = line.split(","); // Split the line by the commas
                    g.createNodeGraph(tempLine[0], Integer.parseInt(tempLine[1]), Integer.parseInt(tempLine[2])); // Add node to graph
                    if (tempLine[0].equals(startNodeChar)) { // If node is start node
                        nodesList.getLast().setColor(Color.GREEN);
                        g.setStartNode(nodesList.getLast());
                    } else if (tempLine[0].equals(endNodeChar)) { // If node is end node
                        nodesList.getLast().setColor(Color.RED);
                        g.setEndNode(nodesList.getLast());
                    }
                }
                nLinks = Integer.parseInt(myReader.nextLine());

                for (int i=0; i<nLinks; i++) {
                    String line = myReader.nextLine(); // Read the entire line
                    String[] tempLine = line.split(","); // Split the line by the commas
                    Node fromN = null;
                    Node toN = null;
                    for (int n=0; n<nNodes; n++) {
                        // Find the node object given the name
                        if (nodesList.get(n).getName().equals(tempLine[0])) {
                            fromN = nodesList.get(n);
                        } else if (nodesList.get(n).getName().equals(tempLine[1])) {
                            toN = nodesList.get(n);
                        }
                    }
                    fromN.createLink(toN, Integer.parseInt(tempLine[2])); // Create the link
                }
            }
            myReader.close(); // Close reader to file be written later
        } catch(IOException e) { // The scanner cannot read the file
            System.out.println("Error: "+e);
        }
    }

    /*
     * Write node and link data to a CSV file in a defined format.
     * The first three lines specify:
     *  1. The number of nodes
     *  2. The name of the start node
     *  3. The name of the end node
     * The next 'n' lines define each node's name and position (x, y).
     * The following line defines the number of links, then the link data is read and links are created.
     * 
     */
    public void writeCSV(Graph g) {
        try {
            FileWriter myWriter = new FileWriter(myCSV); // Define the writer to write to the CSV
            StringBuilder tempLine =new StringBuilder(); // New StringBuilder to put together strings
            ArrayList<Node> nodesList = g.getNodesList(); // Get node list
            int nLinks = 0;

            //Write the first 3 lines, # of nodes, start node, end node
            myWriter.write(nodesList.size()+"\n");
            myWriter.write(g.getStartNode().getName()+"\n");
            myWriter.write(g.getEndNode().getName()+"\n");
            for (int n=0; n<nodesList.size(); n++) {
                myWriter.write(nodesList.get(n).getName()+","+nodesList.get(n).getX()+","+nodesList.get(n).getY()+"\n"); // Name, x-pos, ypos
                for (int i=0; i<nodesList.get(n).getLinkSize(); i++) {
                    nLinks++;
                    tempLine.append(nodesList.get(n).getName()+","+nodesList.get(n).getLinkTo(i).getName()+","+nodesList.get(n).getWeight(i)+"\n"); // Add link line to String builder
                }   
            }
            // Write # of links first then each link
            myWriter.write(nLinks+"\n");
            myWriter.write(tempLine.toString());
            

            // Close the writer
            myWriter.flush();
            myWriter.close();
        } catch (IOException e) { // If the writer cannot write
            System.out.println("Error: "+e);
        }
        
    }
}
