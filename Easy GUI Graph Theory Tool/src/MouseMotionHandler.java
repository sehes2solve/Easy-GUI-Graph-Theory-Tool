import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
public class MouseMotionHandler extends
        MouseMotionAdapter {
    Main main;
    MouseMotionHandler(Main main){
        this.main=main;
    }
    Point delta = new Point();
    @Override
    public void mouseDragged(MouseEvent e) {
        if (main.selecting) {
            main.mouseRect.setBounds(
                    Math.min(main.mousePt.x, e.getX()),
                    Math.min(main.mousePt.y, e.getY()),
                    Math.abs(main.mousePt.x - e.getX()),
                    Math.abs(main.mousePt.y - e.getY()));Node.selectRect(main.nodes, main.mouseRect);
        } else {
            delta.setLocation(
                    e.getX() - main.mousePt.x,
                    e.getY() - main.mousePt.y);
            Node.updatePosition(main.nodes, delta);
            main.mousePt = e.getPoint();
        }
        e.getComponent().repaint();
    }
}