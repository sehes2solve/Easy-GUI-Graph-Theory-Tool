import java.awt.*;
class Edge {
    Node n1;
    Node n2;
    int val;
    boolean isDir;
    private Color color = Color.darkGray;
    Edge(Node n1, Node n2, int val, boolean isDir) {
        this.n1 = n1;
        this.n2 = n2;this.val = val;
        this.isDir = isDir;
    }
    // this is just to be used with max flow
// it doesn't compare weights
    boolean equals(Edge e2) {
        return this.n1 == e2.n1 && this.n2 == e2.n2 &&
                this.isDir == e2.isDir;
    }
    Node getOtherNode(Node n) {
        if (n == n1) return n2;
        return n1;
    }
    void resetColor() {
        color = Color.darkGray;
    }
    void setColor(Color color) {
        this.color = color;
    }
    void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(color);g.drawLine(p1.x, p1.y, p2.x, p2.y);
        int xMid = (p1.x + p2.x) / 2;
        int yMid = (p1.y + p2.y) / 2;
        g.drawString(Integer.toString(val), xMid, yMid);
        if (isDir) {
            int d = 30;
            int h = 10;
            int dx = p2.x - p1.x, dy = p2.y - p1.y;
            double D = Math.sqrt(dx * dx + dy * dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;
            x = xm * cos - ym * sin + p1.x;
            ym = xm * sin + ym * cos + p1.y;
            xm = x;
            x = xn * cos - yn * sin + p1.x;
            yn = xn * sin + yn * cos + p1.y;
            xn = x;
            int[] xpoints = {p2.x, (int) xm, (int) xn};
            int[] ypoints = {p2.y, (int) ym, (int) yn};
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }
    Edge copy() {Edge e = new Edge(n1, n2, val, isDir);
        e.setColor(color);
        return e;
    }
}