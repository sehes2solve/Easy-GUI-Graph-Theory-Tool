import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
public class NextAction extends MyAction {
    NextAction(String name, Main main) {
        super(name,main);
    }public void actionPerformed(ActionEvent e) {
        for(Edge ed:main.edges){
            ed.resetColor();
        }
        if(main.nextEdges.size()==0){
            showMsg("there is no more flows");
            main.control.setClick(true);
            return;
        }
        List<Edge> curEdges = main.nextEdges.get(0).first;
        int mn = main.nextEdges.get(0).second;
        for(Edge edge:curEdges){
            for(Edge edge1:main.edges){
                if(edge.equals(edge1)){
                    edge1.setColor(Color.RED);
                    break;
                }
            }
        }
        showMsg("flow with "+mn+" , you can see the path in the graph");
                main.nextEdges.remove(0);
        main.repaint();}
}