import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends JComponent implements
        ShowMsg {
    static final int WIDE = 800;
    static final int HIGH = 500;
    static final int RADIUS = 15;
    static final Color src = Color.RED;
    static final Color snk = Color.GREEN;
    static final Color defalt = Color.BLUE;
    ControlPanel control = new ControlPanel();
    int radius = RADIUS;
    List<Node> nodes = new ArrayList<>();
    List<Node> selected = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    List<Pair<List<Edge>, Integer>> nextEdges = new
            ArrayList<>();
    Point mousePt = new Point(WIDE / 2, HIGH / 2);
    Rectangle mouseRect = new Rectangle();
    boolean selecting = false;
    boolean isDirected = false;
    JSpinner js2;
    JCheckBox isDir;

    private Main() {
        this.setOpaque(true);
        this.addMouseListener(new MouseHandler(this));
        this.addMouseMotionListener(new
                MouseMotionHandler(this));
    }

    public static void main(String[] args) {
        JFrame f2 = new JFrame("GraphPanel");
        JLabel title = new JLabel("Max-flow and Dijsktra");
        JLabel title2 = new JLabel("To D.Mostafa");
        JLabel pid = new JLabel("PID : 23875368");
        JLabel abdalla = new JLabel("Abdallah Mohamed Naguib 20170160 abdonaguib99@gmail.com 01200469482");
        JLabel saad = new JLabel("Mohamed Ahmed Saad 20170212 mohamedsaad17841@gmail.com");
        JLabel hsn = new JLabel("Hussien Tarek Ismail 20170094 sehes333@stud.fci-cu.edu.eg");
        int x=50;
        int y=50;
        int wid=600;
        title.setBounds(x,y,wid,50);
        title2.setBounds(x,y*2,wid,50);
        pid.setBounds(x,y*6,wid,50);
        abdalla.setBounds(x,y*3,wid,50);
        saad.setBounds(x,y*4,wid,50);
        hsn.setBounds(x,y*5,wid,50);
        f2.add(title);
        f2.add(title2);
        f2.add(abdalla);
        f2.add(saad);
        f2.add(hsn);
        f2.add(pid);
        f2.setSize(700,500);
        JButton button = new JButton("Start the program");
        button.setBounds(x,y*7,wid,50);
        button.addActionListener(e -> {
            f2.setVisible(false);
            EventQueue.invokeLater(() -> {
                JFrame f = new JFrame("GraphPanel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main gp = new Main();
                f.add(gp.control, BorderLayout.NORTH);
                f.add(new JScrollPane(gp), BorderLayout.CENTER);
                f.getRootPane().setDefaultButton(gp.control.defaultButton);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            });
        });
        f2.add(button);
        f2.setLayout(null);//using no layout managers
        f2.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (Node n : nodes) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }
    }

    void deleteDuplicateEdges() {
        List<Edge> toBeRemoved = new ArrayList<>();
        for (Edge edge : edges) {
            int cnt = 0;
            for (Edge edge1 : edges) {
                if ((edge.n1 == edge1.n1 && edge.n2 == edge1.n2)
                        || (edge.n1 == edge1.n2 && edge.n2 ==
                        edge1.n1)) cnt++;
                if (cnt > 1) toBeRemoved.add(edge1);
            }
        }
        edges.removeAll(toBeRemoved);
    }

    ArrayList<Edge> getEdges(Node node, List<Edge>
            edges) {
        ArrayList<Edge> res = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.isDir && edge.n1 == node) {
                res.add(edge);
            } else if (!edge.isDir && (edge.n1 == node ||
                    edge.n2 == node)) {
                res.add(edge);
            }
        }
        return res;
    }

    Node getSrc() {
        for (Node node : nodes) {
            if (node.isSrc) return node;
        }
        return null;
    }

    Node getSnk() {
        for (Node node : nodes) {
            if (node.isSnk) return node;
        }
        return null;
    }

    Edge isConnected(Node n1, Node n2) {
        for (Edge edge : edges) {
            if (edge.n1 == n1 && edge.n2 == n2) return edge;
            if (edge.n1 == n2 && edge.n2 == n1) return edge;
        }
        return null;
    }

    class ControlPanel extends JToolBar {
        Action newNode = new
                NewNodeAction("New", Main.this);
        JButton defaultButton = new JButton(newNode);
        JPopupMenu popup = new JPopupMenu();
        Action clearAll = new
                ClearAction("Clear", Main.this);
        Action dij = new
                ShortestPathAction("Dijsktra", Main.this);
        Action connect = new
                ConnectAction("Connect", Main.this);
        Action reset_edges_color = new ResetEdges("Reset edges color", Main.this);
        Action changeDir = new ChangeDirAction("Change Edge Direction", Main.this);
        Action makeDirInBoth = new MakeDirBothAction("Make Direction in both ends", Main.this);
        Action delete = new DeleteAction("Delete selected nodes", Main.this);
        Action deleteEdge = new DeleteEdge("Delete Edge", Main.this);
        Action setSrc = new SrcAction("Set as Source", Main.this);
        Action setSnk = new SnkAction("Set as destination", Main.this);
        Action mxFlow = new MxFlowAction("get max-flow",
                Main.this);
        Action nxt = new NextAction("next", Main.this);

        ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);
            this.add(defaultButton);
            this.add(new JButton(clearAll));
            isDir = new JCheckBox();
            isDir.addActionListener(e -> {
                isDirected = isDir.isSelected();
                if (!isDirected) {
                    deleteDuplicateEdges();
                    for (Edge edge : edges) {
                        edge.isDir = false;
                    }
                } else {
                    for (Edge edge : edges) {
                        edge.isDir = true;
                    }
                }
                Main.this.repaint();
            });
            js2 = new JSpinner();
            js2.setModel(new SpinnerNumberModel(1, 0,
                    10000000, 1));
            this.add(new JLabel("weight:"));
            this.add(js2);
            this.add(new JLabel("directed:"));
            this.add(isDir);
            this.add(dij);
            this.add(mxFlow);
            this.add(reset_edges_color);
            this.add(nxt);
            nxt.setEnabled(false);
//todo popup menu
            popup.add(new JMenuItem(newNode));
            popup.add(new JMenuItem(connect));
            popup.add(new JMenuItem(changeDir));
            popup.add(new JMenuItem(makeDirInBoth));
            popup.add(new JMenuItem(delete));
            popup.add(new JMenuItem(deleteEdge));
            popup.add(new JMenuItem(setSrc));
            popup.add(new JMenuItem(setSnk));
        }

        void setClick(boolean click) {
            newNode.setEnabled(click);
            defaultButton.setEnabled(click);
            clearAll.setEnabled(click);
            dij.setEnabled(click);
            connect.setEnabled(click);
            reset_edges_color.setEnabled(click);
            changeDir.setEnabled(click);
            makeDirInBoth.setEnabled(click);
            delete.setEnabled(click);
            deleteEdge.setEnabled(click);
            setSrc.setEnabled(click);
            setSnk.setEnabled(click);
            mxFlow.setEnabled(click);
            nxt.setEnabled(!click);
        }
    }
}