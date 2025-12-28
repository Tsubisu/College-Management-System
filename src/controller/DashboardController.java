package controller;

import dao.Notice;
import model.Module;
import model.User;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class DashboardController {
    protected Dashboard dashboard;
    dao.Module moduleDao = new dao.Module();
    private final JPanel emptyPanel = new JPanel();

    protected void buttonListener(){
        dashboard.addMenuButtonListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                if (!btn.getText().equalsIgnoreCase("logout")) {
                    dashboard.getDashPanel().getContentLayout().show(dashboard.getDashPanel().getContentPanel(), btn.getText());
                }
                else
                {
                    JLabel message= new JLabel("Do you want to logout?");
                    int result = JOptionPane.showConfirmDialog(dashboard,message,"Logout",JOptionPane.YES_NO_OPTION);
                    if(result==JOptionPane.YES_OPTION)
                    {
                    dashboard.dispose();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);


                btn.setBackground(new Color(3, 106, 201,255));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);
                Point mousePos = e.getPoint();
                if (btn.contains(mousePos)) {
                    mouseEntered(new MouseEvent(   btn,
                            MouseEvent.MOUSE_ENTERED,
                            System.currentTimeMillis(),
                            0,
                            e.getX(),
                            e.getY(),
                            0,
                            false));
                }


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);
                btn.setBackground(new Color(148, 189, 250,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);

            }
        });
    }


    protected void moduleActionListener(ModuleDisplay moduleDisplay,model.Module module)
    {
        dashboard.getDashPanel().getContentLayout().show(dashboard.getDashPanel().getContentPanel(),"ModuleDisplay");
        moduleDisplaySetter(moduleDisplay,module);




    }

    private void moduleDisplaySetter(ModuleDisplay moduleDisplay ,model.Module module)
    {


        ArrayList<model.Chapter> chapters= moduleDao.getChaptersByModule(module.getModuleId());
        javax.swing.JComboBox<String> chapterComboBox= moduleDisplay.getChapterComboBox();
        String[] chapterNames= new String[chapters.size()];
        String[] chapterPath= new String[chapters.size()];
        for(int i=0;i< chapters.size();i++)
        {
           chapterNames[i]=chapters.get(i).getChapterName();
           chapterPath[i]=chapters.get(i).getPdfPath();
        }


        chapterComboBox.setModel(new DefaultComboBoxModel<>(chapterNames));

        moduleDisplay.setModuleName(module.getModuleName());
        moduleDisplay.setChapters(chapters.size());
        moduleDisplay.setYear(module.getCourseYear());
        moduleDisplay.setDuration(module.getModuleDuration());




        // Clear previous listeners
        for (ActionListener al : chapterComboBox.getActionListeners()) {
            chapterComboBox.removeActionListener(al);
        }

        // Clear PDF if no chapters
        if (chapters.isEmpty()) {
            moduleDisplay.getModuleDisplayPort().setViewportView(emptyPanel);
        }




        moduleDisplay.addChapterComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chapters.isEmpty())
                {int i =chapterComboBox.getSelectedIndex();
                System.out.print(chapterPath[i]);
               // loadPdfIntoScrollPane(chapterPath[i],moduleDisplay);
                    openPdf(chapterPath[i],moduleDisplay);
                }
                else
                {
                    moduleDisplay.getModuleDisplayPort().setViewportView(new JPanel());
                }
            }
        });
    }


    private void openPdf(String file, ModuleDisplay moduleDisplay){

//        moduleDisplay.getModuleDisplayPort().removeAll();
//        moduleDisplay.getModuleDisplayPort().revalidate();
//        moduleDisplay.getModuleDisplayPort().repaint();


        try {
            SwingController control=new SwingController();
            SwingViewBuilder factry=new SwingViewBuilder(control);
            JPanel veiwerCompntpnl=factry.buildViewerPanel();
            ComponentKeyBinding.install(control, veiwerCompntpnl);
            control.getDocumentViewController().setAnnotationCallback(
                    new org.icepdf.ri.common.MyAnnotationCallback(
                            control.getDocumentViewController()));
            control.openDocument(file);
            moduleDisplay.getModuleDisplayPort().setViewportView(veiwerCompntpnl);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(moduleDisplay,"Cannot Load Pdf");
        }
    }





}