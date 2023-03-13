import visualization.Frame;
import visualization.Panel;

public class Main {
    public static void main(String[] args) {
        var window = new Frame();
        var panel = new Panel();

        window.add(panel);
        window.pack();
    }
}