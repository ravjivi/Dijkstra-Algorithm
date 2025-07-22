import java.util.Scanner;
import java.io.*;
public class CSVEditor {
    File myCSV;
    public CSVEditor() {
        myCSV = new File("GraphData.csv"); // Using GraphData.csv file
    }
    
    public void readCSV(Graph g) {
        int nNodes = 0;
        int nLinks = 0;
        try {
            Scanner myReader = new Scanner(myCSV);
            while (myReader.hasNextLine()) {
                nNodes = Integer.parseInt(myReader.nextLine());
                
                for (int i=0; i<nNodes; i++) {
                    String line = myReader.nextLine(); // Read the entire line
                    String[] tempLine = line.split(","); // Split the line by the commas
                    g.createNodeGraph(tempLine[0], Integer.parseInt(tempLine[1]), Integer.parseInt(tempLine[2]));
                }
                nLinks = Integer.parseInt(myReader.nextLine());
            }
            

        } catch(IOException e) { // The scanner cannot read the file
            System.out.println("Error: "+e);
        }
    }
}
