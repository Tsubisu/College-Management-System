package view;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
                }
            }
        }

    }

    public void addMenuButtonListener(ActionListener actionListener)
    {
        for(JButton button : menuButtonList)
        {
            button.addActionListener(actionListener);
        }
    }

    public DashboardPanel getDashPanel() {
        return dashPanel;
    }

}
