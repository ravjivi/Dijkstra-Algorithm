public class Main {
    public static void main(String[] args) {
        GUI gui;
        CSVEditor csv = new CSVEditor();
        if (System.getProperty("os.name").toLowerCase().contains("mac")) { // If on mac
            System.setProperty( "apple.laf.useScreenMenuBar", "true" ); // Intergrate GUI menu bar to mac menu
            gui = new GUI("mac"); // Create GUI with mac shortcut keys
        } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            gui =new GUI("windows"); // Create GUI with windows shortcut keys
        } else {
            gui = new GUI(null);
        }
        csv.readCSV(gui.returnGraph());
    }
}
