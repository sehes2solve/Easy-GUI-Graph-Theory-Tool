import java.awt.event.ActionEvent;
public class MakeDirBothAction extends MyAction{
    MakeDirBothAction(String name, Main main) {
        super(name,main);
    }
    public void actionPerformed(ActionEvent e) {Node.getSelected(main.nodes, main.selected);
        if(main.selected.size()!=2){
            showMsg("this action requires exactly 2 nodes selected");
            return;
        }
        Node n1=main.selected.get(0);
        Node n2=main.selected.get(1);
        boolean found = false;
        for(Edge edge:main.edges){
            if(edge.n1==n1 && edge.n2==n2){
                main.edges.add(new Edge(n2,n1,edge.val,true));
                if(!edge.isDir){
                    main.edges.remove(edge);
                    main.edges.add(new Edge(n1,n2,edge.val,true));
                }
                found=true;
                break;
            }else if(edge.n2==n1 && edge.n1==n2){
                main.edges.add(new Edge(n1,n2,edge.val,true));
                if(!edge.isDir){
                    main.edges.remove(edge);
                    main.edges.add(new Edge(n2,n1,edge.val,true));}
                found=true;
                break;
            }
        }
        if(!found){
            showMsg("this action requires 2 connected nodes");
        }
        main.repaint();
    }
}