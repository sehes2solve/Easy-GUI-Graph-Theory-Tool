import java.awt.event.ActionEvent;

public class DeleteEdge extends MyAction {
    DeleteEdge(String name, Main main) {
        super(name, main);
    }

    public void actionPerformed(ActionEvent e) {
        Node.getSelected(main.nodes, main.selected);
        if (main.selected.size() != 2) {
            showMsg("this action requires exactly 2 nodes selected");
            return;
        }
        Node n1 = main.selected.get(0);
        Node n2 = main.selected.get(1);
        Edge edge = main.isConnected(n1, n2);
        if (edge == null) {
            showMsg("there is no edge between");
            return;
        }
        main.edges.remove(edge);
        main.repaint();
    }
}