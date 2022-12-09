import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.PriorityQueue;
public class ShortestPathAction extends MyAction {
    ShortestPathAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {
        if (!main.isDirected) {
            main.deleteDuplicateEdges();
        }
        for (Edge ed : main.edges) {
            ed.resetColor();
        }
        Node src = main.getSrc();
        Node snk = main.getSnk();
        if (src == null) {showMsg("source needs to be selected");
            main.repaint();
            return;
        }
        if (snk == null) {
            showMsg("destination needs to be selected");
            main.repaint();
            return;
        }
        for (Node node : main.nodes) {
            node.resetForDij();
        }
        src.mnDist = 0;
        PriorityQueue<Node> priorityQueue = new
                PriorityQueue<>();
        priorityQueue.add(src);
        while (!priorityQueue.isEmpty()) {
            Node u = priorityQueue.poll();
            for (Edge edge : main.getEdges(u, main.edges)) {
                Node v = edge.n2;
                if (v == u) v = edge.n1;
                int weight = edge.val;
                int minDistance = u.mnDist + weight;if (minDistance < v.mnDist) {
                    priorityQueue.remove(u);
                    v.parent = u;
                    v.mnDist = minDistance;
                    priorityQueue.add(v);
                }
            }
        }
        if (snk.parent == null) {
            showMsg("there is no path between the source and the snk");
                    main.repaint();
            return;
        }
        Node cur = snk;
        while (cur != src) {
            Edge edge = main.isConnected(cur, cur.parent);
// this if should never be true
            if (edge == null) {
                showMsg("there is no path between the source and the snk");
                return;
            }edge.setColor(Color.RED);
            cur = cur.parent;
        }
        main.repaint();
    }
}