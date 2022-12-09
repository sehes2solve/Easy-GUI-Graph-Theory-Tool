import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class MouseHandler extends MouseAdapter {
    Main main;
    MouseHandler(Main main){
        this.main=main;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        main.selecting = false;
        main.mouseRect.setBounds(0, 0, 0, 0);if (e.isPopupTrigger()) {
            showPopup(e);
        }
        e.getComponent().repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        main.mousePt = e.getPoint();
        if (e.isShiftDown()) {
            Node.selectToggle(main.nodes, main.mousePt);
        } else if (e.isPopupTrigger()) {
            Node.selectOne(main.nodes, main.mousePt);
            showPopup(e);
        } else if (Node.selectOne(main.nodes,
                main.mousePt)) {
            main.selecting = false;
        } else {
            Node.selectNone(main.nodes);
            main.selecting = true;
        }
        e.getComponent().repaint();
    }
    void showPopup(MouseEvent e) {main.control.popup.show(e.getComponent(), e.getX(),
            e.getY());
    }
}