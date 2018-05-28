package uav.gcs2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Jesimar S. Arantes
 */
public final class GCS2 extends JFrame {

    private final JPanel panelTop;
    private final JPanel panelMain;
    private final JPanel panelLeft;
    private final JPanel panelRight;
    
    private final JTextArea textAreaGeneral;
    private final JTextArea textAreaSITL;
    private final JTextArea textAreaMAVProxy;
    private final JTextArea textAreaSOA;
    private final JTextArea textAreaIFA;
    private final JTextArea textAreaMOSA;
    
    private final int width = 1280;
    private final int height = 740;
    
    private final StartModules start;

    public static void main(String[] args) {
        GCS2 gcs = new GCS2();
    }

    public GCS2() {
        start = new StartModules();
        
        this.setTitle("UAV-GCS");
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTop.setBackground(new Color(166, 207, 255));
        panelTop.setVisible(true);
        
        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(170, 25));
        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        panelTop.add(btnAbout);

        panelMain = new JPanel();
        panelMain.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelMain.setBackground(Color.LIGHT_GRAY);
        panelMain.setVisible(true);

        panelLeft = new JPanel();
        panelLeft.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelLeft.setBackground(new Color(95, 161, 255));
        panelLeft.setVisible(true);
        panelMain.add(panelLeft);

        panelRight = new JPanel();
        panelRight.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelRight.setBackground(new Color(95, 161, 255));
        panelRight.setVisible(true);
        panelMain.add(panelRight);
        
        JLabel labelActions = new JLabel("Actions");
        labelActions.setPreferredSize(new Dimension(160, 20));
        labelActions.setForeground(Color.BLACK);
        panelLeft.add(labelActions);
        
        JButton btnListIPs = new JButton("List IPs");
        btnListIPs.setPreferredSize(new Dimension(170, 25));
        btnListIPs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execListIPs(textAreaGeneral);
            }
        });
        panelLeft.add(btnListIPs);
        
        JButton btnClearSimulations = new JButton("Clear Simulations");
        btnClearSimulations.setPreferredSize(new Dimension(170, 25));
        btnClearSimulations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execClearSimulations(textAreaGeneral);
            }
        });
        panelLeft.add(btnClearSimulations);
        
        JButton btnCopyFilesResults = new JButton("Copy File Results");
        btnCopyFilesResults.setPreferredSize(new Dimension(170, 25));
        btnCopyFilesResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execCopyFilesResults(textAreaGeneral);
            }
        });
        panelLeft.add(btnCopyFilesResults);

        JButton btnStartSITL = new JButton("Start SILT");
        btnStartSITL.setPreferredSize(new Dimension(170, 25));
        btnStartSITL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execSITL(textAreaSITL);
                btnStartSITL.setEnabled(false);
            }
        });
        panelLeft.add(btnStartSITL);

        JButton btnStartMavproxy = new JButton("Start MAVProxy");
        btnStartMavproxy.setPreferredSize(new Dimension(170, 25));
        btnStartMavproxy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execMAVProxy(textAreaMAVProxy);
                btnStartMavproxy.setEnabled(false);
            }
        });
        panelLeft.add(btnStartMavproxy);

        JButton btnStartSOA = new JButton("Start SOA");
        btnStartSOA.setPreferredSize(new Dimension(170, 25));
        btnStartSOA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execSOAInterface(textAreaSOA);
                btnStartSOA.setEnabled(false);
            }
        });
        panelLeft.add(btnStartSOA);

        JButton btnStartIFA = new JButton("Start IFA");
        btnStartIFA.setPreferredSize(new Dimension(170, 25));
        btnStartIFA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execIFA(textAreaIFA);
                btnStartIFA.setEnabled(false);
            }
        });
        panelLeft.add(btnStartIFA);

        JButton btnStartMOSA = new JButton("Start MOSA");
        btnStartMOSA.setPreferredSize(new Dimension(170, 25));
        btnStartMOSA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.execMOSA(textAreaMOSA);
                btnStartMOSA.setEnabled(false);
            }
        });
        panelLeft.add(btnStartMOSA);
        
        JLabel labelGeneral = new JLabel("Terminal General");
        labelGeneral.setPreferredSize(new Dimension(470, 20));
        labelGeneral.setForeground(Color.WHITE);
        panelRight.add(labelGeneral);

        JLabel labelSITL = new JLabel("Terminal Dronekit-SITL");
        labelSITL.setPreferredSize(new Dimension(470, 20));
        labelSITL.setForeground(Color.WHITE);
        panelRight.add(labelSITL);
        
        JScrollPane scrollGeneral = new JScrollPane();
        textAreaGeneral = new JTextArea(11, 36);
        textAreaGeneral.setForeground(Color.BLACK);
        textAreaGeneral.setSelectedTextColor(Color.RED);
        textAreaGeneral.setBackground(Color.WHITE);
        textAreaGeneral.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaGeneral.setEditable(false);
        scrollGeneral.setViewportView(textAreaGeneral);
        panelRight.add(scrollGeneral);

        JScrollPane scrollSITL = new JScrollPane();
        textAreaSITL = new JTextArea(11, 36);
        textAreaSITL.setForeground(Color.BLACK);
        textAreaSITL.setSelectedTextColor(Color.RED);
        textAreaSITL.setBackground(Color.WHITE);
        textAreaSITL.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaSITL.setEditable(false);
        scrollSITL.setViewportView(textAreaSITL);
        panelRight.add(scrollSITL);
       
        JLabel labelMAVProxy = new JLabel("Terminal MAVProxy");
        labelMAVProxy.setPreferredSize(new Dimension(450, 20));
        labelMAVProxy.setForeground(Color.WHITE);
        panelRight.add(labelMAVProxy);
        
        JLabel labelSOA = new JLabel("Terminal SOA Interface");
        labelSOA.setPreferredSize(new Dimension(470, 20));
        labelSOA.setForeground(Color.WHITE);
        panelRight.add(labelSOA);

        JScrollPane scrollMavproxy = new JScrollPane();
        textAreaMAVProxy = new JTextArea(11, 36);
        textAreaMAVProxy.setForeground(Color.BLACK);
        textAreaMAVProxy.setSelectedTextColor(Color.RED);
        textAreaMAVProxy.setBackground(Color.WHITE);
        textAreaMAVProxy.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaMAVProxy.setEditable(false);
        scrollMavproxy.setViewportView(textAreaMAVProxy);
        panelRight.add(scrollMavproxy);

        JScrollPane scrollSOA = new JScrollPane();
        textAreaSOA = new JTextArea(11, 36);
        textAreaSOA.setForeground(Color.BLACK);
        textAreaSOA.setSelectedTextColor(Color.RED);
        textAreaSOA.setBackground(Color.WHITE);
        textAreaSOA.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaSOA.setEditable(false);
        scrollSOA.setViewportView(textAreaSOA);
        panelRight.add(scrollSOA);
        
        JLabel labelIFA = new JLabel("Terminal IFA");
        labelIFA.setPreferredSize(new Dimension(450, 20));
        labelIFA.setForeground(Color.WHITE);
        panelRight.add(labelIFA);
        
        JLabel labelMOSA = new JLabel("Terminal MOSA");
        labelMOSA.setPreferredSize(new Dimension(450, 20));
        labelMOSA.setForeground(Color.WHITE);
        panelRight.add(labelMOSA);

        JScrollPane scrollIFA = new JScrollPane();
        textAreaIFA = new JTextArea(11, 36);
        textAreaIFA.setForeground(Color.BLACK);
        textAreaIFA.setSelectedTextColor(Color.RED);
        textAreaIFA.setBackground(Color.WHITE);
        textAreaIFA.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaIFA.setEditable(false);
        scrollIFA.setViewportView(textAreaIFA);
        panelRight.add(scrollIFA);

        JScrollPane scrollMOSA = new JScrollPane();
        textAreaMOSA = new JTextArea(11, 36);
        textAreaMOSA.setForeground(Color.BLACK);
        textAreaMOSA.setSelectedTextColor(Color.RED);
        textAreaMOSA.setBackground(Color.WHITE);
        textAreaMOSA.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        textAreaMOSA.setEditable(false);
        scrollMOSA.setViewportView(textAreaMOSA);
        panelRight.add(scrollMOSA);        

        this.add(panelTop);
        this.add(panelMain);

        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                setSize(getWidth(), getHeight());
            }
        });
        this.setVisible(true);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        panelTop.setPreferredSize(new Dimension(width - 30, 90));
        panelMain.setPreferredSize(new Dimension(width - 30, height - 130));
        panelLeft.setPreferredSize(new Dimension(200, height - 140));
        panelRight.setPreferredSize(new Dimension(width - 30 - 200 - 20, height - 140));
    }

    public void showAbout(){
        String msgAbout   = "Author: Jesimar da Silva Arantes\n" + 
                            "Version: 1.0.0\n" + 
                            "Date: 09/03/2018";
        JOptionPane.showMessageDialog(null, 
                msgAbout, "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
