import java.awt.*;
import java.awt.event.ActionEvent;
public class NewNodeAction extends MyAction {
    private boolean shown=false;
    NewNodeAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {
        if(!shown) {
            showMsg("some operations can be done using click- right after selecting required nodes (like adding edge)");
            shown=true;
        }
        Node.selectNone(main.nodes);
        Point p = new Point((int) (Math.random() *
                Math.min(Main.WIDE, Main.HIGH)),
                (int) (Math.random() * Math.min(Main.WIDE,
                        Main.HIGH)));
        Node n = new Node(p, main.radius, Main.defalt);
        n.setSelected(true);
        main.nodes.add(n);
        main.repaint();
    }
}