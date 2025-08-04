public class Main {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) { // If on mac
            System.setProperty( "apple.laf.useScreenMenuBar", "true" ); // Intergrate GUI menu bar to mac menu
            new GUI("mac"); // Create GUI with mac shortcut keys
        } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            new GUI("windows"); // Create GUI with windows shortcut keys
        } else { // Another OS
            new GUI(null);
        }
    }
}
