import java.awt.event.ActionEvent;
import java.util.ListIterator;
public class DeleteAction extends MyAction {
    DeleteAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {
        ListIterator<Node> iter =
                main.nodes.listIterator();
        while (iter.hasNext()) {
            Node n = iter.next();if (n.isSelected()) {
                deleteEdges(n);
                iter.remove();
            }
        }
        main.repaint();
    }
    void deleteEdges(Node n) {
        main.edges.removeIf(e -> e.n1 == n || e.n2 == n);
    }
}