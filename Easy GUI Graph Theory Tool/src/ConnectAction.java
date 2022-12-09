import java.awt.event.ActionEvent;

public class ConnectAction extends MyAction {
    ConnectAction(String name, Main main) {
        super(name, main);
    }

    public void actionPerformed(ActionEvent e) {
        Node.getSelected(main.nodes, main.selected);
        if (main.selected.size() != 2) {
            showMsg("this action requires exactly 2 nodes to be selected");
            return;
        }
        int x = (int) main.js2.getValue();
        Node n1 = main.selected.get(0);
        Node n2 = main.selected.get(1);
        if (main.isDir.isSelected()) {
            main.edges.add(new Edge(n1, n2, x, true));
        } else {
            main.edges.add(new Edge(n1, n2, x, false));
        }
        main.repaint();
    }
}