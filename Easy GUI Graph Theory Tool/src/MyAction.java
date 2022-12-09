import javax.swing.*;
abstract class MyAction extends AbstractAction
        implements ShowMsg {
    Main main;
    MyAction(String name, Main main){
        super(name);
        this.main=main;
    }
}