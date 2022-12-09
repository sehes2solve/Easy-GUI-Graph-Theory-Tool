import java.awt.*;
import java.util.List;
public class Node implements Comparable<Node> {
    private Point p;
    private int r;
    private Color color;
    private boolean selected = false;
    private Rectangle b = new Rectangle();
    boolean isSrc, isSnk;
    int mnDist = (int) 1e9;
    Node parent = null;
    /**
     * Construct a new node.
     */
    Node(Point p, int r, Color color) {
        this.p = p;
        this.r = r;
        this.color = color;
        setBoundary(b);
    }/**
     * Collected all the selected nodes in list.
     */
    static void getSelected(java.util.List<Node> list,
                            java.util.List<Node> selected) {
        selected.clear();
        for (Node n : list) {
            if (n.isSelected()) {
                selected.add(n);
            }
        }
    }
    /**
     * Select no nodes.
     */
    static void selectNone(java.util.List<Node> list) {
        for (Node n : list) {
            n.setSelected(false);
        }
    }
    /**
     * Select a single node; return true if not already
     selected.*/
    static boolean selectOne(java.util.List<Node> list,
                             Point p) {
        for (Node n : list) {
            if (n.contains(p)) {
                if (!n.isSelected()) {
                    Node.selectNone(list);
                    n.setSelected(true);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * Select each node in r.
     */
    static void selectRect(java.util.List<Node> list,
                           Rectangle r) {
        for (Node n : list) {
            n.setSelected(r.contains(n.p));
        }
    }/**
     * Toggle selected state of each node containing p.
     */
    static void selectToggle(java.util.List<Node> list,
                             Point p) {
        for (Node n : list) {
            if (n.contains(p)) {
                n.setSelected(!n.isSelected());
            }
        }
    }
    /**
     * Update each node's position by d (delta).
     */
    static void updatePosition(List<Node> list, Point d)
    {
        for (Node n : list) {
            if (n.isSelected()) {
                n.p.x += d.x;
                n.p.y += d.y;
                n.setBoundary(n.b);
            }
        }}
    void resetForDij() {
        parent = null;
        mnDist = (int) 1e9;
    }
    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.mnDist, o.mnDist);
    }
    void setSrc(List<Node> nodes) {
        for (Node node : nodes) {
            node.undoSrc();
        }
        isSrc = true;
        this.color = Main.src;
        isSnk = false;
    }
    private void undoSrc() {
        isSrc = false;
        if (!isSnk)
            color = Main.defalt;
    }
    private void undoSnk() {isSnk = false;
        if (!isSrc)
            color = Main.defalt;
    }
    void setSnk(List<Node> nodes) {
        for (Node node : nodes) {
            node.undoSnk();
        }
        isSrc = false;
        isSnk = true;
        this.color = Main.snk;
    }
    /**
     * Calculate this node's rectangular boundary.
     */
    private void setBoundary(Rectangle b) {
        b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }
    /**
     * Draw this node.
     */
    void draw(Graphics g) {
        g.setColor(this.color);g.fillOval(b.x, b.y, b.width, b.height);
        if (selected) {
            g.setColor(Color.darkGray);
            g.drawRect(b.x, b.y, b.width, b.height);
        }
    }
    /**
     * Return this node's location.
     */
    Point getLocation() {
        return p;
    }
    /**
     * Return true if this node contains p.
     */
    private boolean contains(Point p) {
        return b.contains(p);
    }
    /**
     * Return true if this node is selected.
     */
    boolean isSelected() {
        return selected;}
    /**
     * Mark this node as selected.
     */
    void setSelected(boolean selected) {
        this.selected = selected;
    }
}