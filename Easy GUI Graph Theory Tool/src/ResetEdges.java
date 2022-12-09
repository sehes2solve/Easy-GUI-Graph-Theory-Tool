import java.awt.event.ActionEvent;
public class ResetEdges extends MyAction {
    ResetEdges(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {for (Edge ed : main.edges) {
        ed.resetColor();
    }
        main.repaint();
    }
}