import javax.swing.*;
public interface ShowMsg {
    default void showMsg(String msg){
        JOptionPane.showMessageDialog(null
                , msg
                , "message!",
                JOptionPane.INFORMATION_MESSAGE);
    }
}