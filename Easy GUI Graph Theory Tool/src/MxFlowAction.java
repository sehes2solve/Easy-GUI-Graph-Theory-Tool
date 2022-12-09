import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MxFlowAction extends MyAction {
    MxFlowAction(String name, Main main) {
        super(name, main);
    }

    static class MnVal {
        int val = (int) 1e9;
    }

    public void actionPerformed(ActionEvent e) {
        if (!main.isDirected) {
            showMsg("can't get max flow for an undirected graph");
            return;
        }
        for (Edge ed : main.edges) {
            ed.resetColor();
        }
        List<Edge> es = new ArrayList<>();
        for (Edge edge : main.edges) {
            es.add(edge.copy());
        }
        Node src = main.getSrc();
        Node snk = main.getSnk();
        if (src == null) {
            showMsg("source needs to be selected");
            main.repaint();
            return;
        }
        if (snk == null) {
            showMsg("destination needs to be selected");
            main.repaint();
            return;
        }
        int total = 0;
        while (true) {
            MnVal mnVal = new MnVal();
            List<Node> lst = new ArrayList<>();
            lst.add(src);
            List<Edge> path =
                    getPath(src, snk, null, es, mnVal, lst);
            if (path == null) break;
            for (Edge edge : path) {
                edge.val -= mnVal.val;
            }
            total += mnVal.val;
            main.nextEdges.add(new Pair<>(path, mnVal.val));
        }
        main.control.setClick(false);
        showMsg("you got a total flow of " + total + " , to show the paths click next");
    }

    private List<Edge> getPath(Node cur, Node dist, Edge
            e, List<Edge> edges, MnVal mnVal, List<Node> nodes) {
        if (cur == dist) {
            List<Edge> res = new ArrayList<>();
// this should never be false
            if (e != null) {
                mnVal.val = Math.min(mnVal.val, e.val);
                res.add(e);
            }
            return res;
        }

        List<Edge> con = main.getEdges(cur, edges);
        for (Edge edge : con) {
            if (edge.val <= 0) continue;
            Node n2 = edge.getOtherNode(cur);
            if (nodes.contains(n2)) continue;
            nodes.add(n2);
            List<Edge>
                    res = getPath(n2, dist, edge, edges, mnVal, nodes);
            if (res != null) {
                if (e != null) {
                    mnVal.val = Math.min(mnVal.val, e.val);
                    res.add(e);
                }
                return res;
            }
        }
        return null;
    }
}