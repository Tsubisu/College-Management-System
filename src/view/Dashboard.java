package view;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public abstract class Dashboard extends javax.swing.JFrame {
    protected DashboardPanel dashPanel;
    public ArrayList<JButton> menuButtonList= new ArrayList<>();

    protected void menuButtonList()
    {
        for (MenuComponents item : dashPanel.componentList  )
        {
            if(item instanceof MenuItems){
                if(((MenuItems) item).getComponent() instanceof JButton) {
                    menuButtonList.add((JButton) (((MenuItems) item).getComponent()));
                    JButton btn=((JButton) (((MenuItems) item).getComponent()));
                    btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());


                }
            }
        }

    }

    public void addMenuButtonListener(MouseListener mouseListener)
    {
        for(JButton button : menuButtonList)
        {
            button.addMouseListener(mouseListener);
        }
    }

    public DashboardPanel getDashPanel() {
        return dashPanel;
    }

}
