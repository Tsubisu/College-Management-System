package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public abstract class DashboardPanel extends javax.swing.JPanel{
    protected javax.swing.JPanel menuPanel;
    protected javax.swing.JPanel contentPanel;
    
    protected javax.swing.JLabel logo;  
    protected javax.swing.JLabel name;
    
    protected final javax.swing.JButton profile;
    protected final javax.swing.JButton module;
    protected final javax.swing.JButton attendance;
    protected final javax.swing.JButton routine;
    protected final javax.swing.JButton notice;
    protected final javax.swing.JButton logout;
    private final Font buttonFont = new Font("Century Gothic",Font.PLAIN,18);
    private final Dimension buttonDimension;
    
    
    
    protected List<MenuComponents> componentList = new ArrayList<>();
    private GroupLayout menuLayout;
    private CardLayout contentLayout;


    public abstract javax.swing.JPanel getProfilePanel();
    

    
    
    
      
    protected DashboardPanel()    
    {   
        this.buttonDimension = new Dimension(210,35);
        menuPanel= new javax.swing.JPanel();
        menuLayout = new GroupLayout(menuPanel);
        menuLayout.setAutoCreateGaps(true);
        
     
        menuPanel.setLayout(menuLayout);
        menuPanel.setMinimumSize(new Dimension(210,630));
        menuPanel.setMaximumSize(new Dimension(210,630));
        menuPanel.setPreferredSize(new Dimension(210,630));
        menuPanel.setBackground(Color.decode("#308BDE"));
        
        
        contentPanel= new javax.swing.JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);
        contentPanel.setMinimumSize(new Dimension(1120-210,630));
        contentPanel.setMaximumSize(new Dimension(1120-210,630));
        contentPanel.setPreferredSize(new Dimension(1120-210,630));
        contentPanel.setBackground(Color.white);
        
        logo= new javax.swing.JLabel();
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/collegeLogo.png")));
        //logo.setText("Logo");
        logo.setHorizontalAlignment((int)CENTER_ALIGNMENT);
        logo.setFont( new Font("Century Gothic",Font.PLAIN,30));
        logo.setForeground(Color.white);
        logo.setPreferredSize(new Dimension(210,170));
        logo.setMinimumSize(new Dimension(210,170));
        logo.setMaximumSize(new Dimension(210,170));
        
        name= new javax.swing.JLabel();
        name.setText("Hardwarica");
        name.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        name.setFont( new Font("Century Gothic",Font.PLAIN,24));
        name.setForeground(Color.white);
        name.setPreferredSize(new Dimension(210,40));
        name.setMinimumSize(new Dimension(210,40));
        name.setMaximumSize(new Dimension(210,40));
        

        profile = new javax.swing.JButton();
        module = new javax.swing.JButton();
        attendance = new javax.swing.JButton();
        routine = new javax.swing.JButton();
        notice = new javax.swing.JButton();
        logout = new javax.swing.JButton();

      
        buttonDecorator(profile, "Profile");
        buttonDecorator(module, "Module");
        buttonDecorator(attendance, "Attendance");
        buttonDecorator(routine, "Routine");
        buttonDecorator(notice, "Notice");
        buttonDecorator(logout, "Logout");
    }
    public CardLayout getContentLayout()

    {
        return contentLayout;
    }

    public javax.swing.JPanel getContentPanel(){
        return contentPanel;
    }
    protected final void menuBuilder(List<MenuComponents> menuComponents)
    {
    GroupLayout.ParallelGroup hGroup = menuLayout.createParallelGroup();
         GroupLayout.SequentialGroup vGroup = menuLayout.createSequentialGroup();

         for (MenuComponents item : menuComponents) {
             if (item instanceof MenuItems bi) {
                 hGroup.addComponent(bi.getComponent(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
                 vGroup.addComponent(bi.getComponent());
             } else if (item instanceof MenuGap gi) {
                 hGroup.addGap(gi.getSize());
                 vGroup.addGap(gi.getSize());
             }
         }

         menuLayout.setHorizontalGroup(hGroup);
         menuLayout.setVerticalGroup(vGroup);

         revalidate();
         repaint();
    }
    
    protected final void buttonDecorator(javax.swing.JButton button, String name)
    {  
       ImageIcon icon = new ImageIcon(getClass().getResource("/images/"+name.toLowerCase()+".png"));
       button.setText(name);       
       button.setFont(buttonFont);
       button.setPreferredSize(buttonDimension);
       button.setMinimumSize(buttonDimension);
       button.setMaximumSize(buttonDimension);
       button.setContentAreaFilled(false);
       button.setOpaque(false);
       button.setBorderPainted(false);
       button.setFocusPainted(false);
       button.setForeground(Color.white);
       button.setIcon(icon);
       button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
       button.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
       button.setIconTextGap(25);
       button.setVerticalAlignment(javax.swing.SwingConstants.TOP);
       button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
       button.setVerticalAlignment((int) CENTER_ALIGNMENT);

    }
}

