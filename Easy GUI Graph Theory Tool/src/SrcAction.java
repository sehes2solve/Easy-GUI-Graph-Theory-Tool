import java.awt.event.ActionEvent;
public class SrcAction extends MyAction {
    SrcAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {
        Node.getSelected(main.nodes, main.selected);
        if (main.selected.size() < 1) return;
        if (main.selected.size() > 1) {
            showMsg("you can't set more than one source");
        } else {main.selected.get(0).setSrc(main.nodes);
        }
        main.repaint();
    }
}