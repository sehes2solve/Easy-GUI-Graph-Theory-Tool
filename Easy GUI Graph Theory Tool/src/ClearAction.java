import java.awt.event.ActionEvent;
public class ClearAction extends MyAction {
    ClearAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {
        main.nodes.clear();
        main.edges.clear();
        main.repaint();
    }
}