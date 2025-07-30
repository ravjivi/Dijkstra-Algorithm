import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.io.*;
public class CSVEditor {
    File myCSV;
    public CSVEditor() {
        myCSV = new File("src/GraphData.csv"); // Using GraphData.csv file
    }
    
    public void readCSV(Graph g) {
        int nNodes = 0;
        int nLinks = 0;
        try {
            Scanner myReader = new Scanner(myCSV);
            ArrayList<Node> nodesList = g.getNodesList();

            while (myReader.hasNextLine()) {
                nNodes = Integer.parseInt(myReader.nextLine());
                String startNodeChar = myReader.nextLine();
                String endNodeChar = myReader.nextLine();
                
                for (int i=0; i<nNodes; i++) {
                    String line = myReader.nextLine(); // Read the entire line
                    String[] tempLine = line.split(","); // Split the line by the commas
                    g.createNodeGraph(tempLine[0], Integer.parseInt(tempLine[1]), Integer.parseInt(tempLine[2]));
                    if (tempLine[0].equals(startNodeChar)) {
                        nodesList.getLast().setColor(Color.GREEN);
                        g.setStartNode(nodesList.getLast());
                    } else if (tempLine[0].equals(endNodeChar)) {
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
                        if (nodesList.get(n).getName().equals(tempLine[0])) {
                            fromN = nodesList.get(n);
                        } else if (nodesList.get(n).getName().equals(tempLine[1])) {
                            toN = nodesList.get(n);
                        }
                    }
                    fromN.createLink(toN, Integer.parseInt(tempLine[2]));
                }
            }
            

        } catch(IOException e) { // The scanner cannot read the file
            System.out.println("Error: "+e);
        }
    }

    public void writeCSV(Graph g) {
        try {
            FileWriter myWriter = new FileWriter(myCSV); // Define the writer to write to the CSV
            StringBuilder tempLine =new StringBuilder(); // New StringBuilder to put together strings
            ArrayList<Node> nodesList = g.getNodesList();
            int nLinks = 0;

            myWriter.write(nodesList.size()+"\n");
            for (int n=0; n<nodesList.size(); n++) {
                myWriter.write(nodesList.get(n).getName()+","+nodesList.get(n).getX()+","+nodesList.get(n).getY()+",\n");
                for (int i=0; i<nodesList.get(n).getLinkSize(); i++) {
                    nLinks++;
                    tempLine.append(nodesList.get(n).getName()+","+nodesList.get(n).getLinkTo(i).getName()+","+nodesList.get(n).getWeight(i)+"\n");
                }   
            }
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
