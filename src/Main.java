public class Main {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) { // If on mac
            System.setProperty( "apple.laf.useScreenMenuBar", "true" ); // Intergrate GUI menu bar to mac menu
        } 
        new GUI(); // Create GUI
    }
}
