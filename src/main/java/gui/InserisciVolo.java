package gui;
//import controller.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InserisciVolo {
    private JPanel inserisciVoloPanel;

    private JTextField codiceVolo;
    private JTextField compagniaVolo;
    private JTextField origineVolo;
    private JTextField destinazioneVolo;
    private JTextField dataVolo;
    private JTextField orarioVolo;
    private JTextField ritardoVolo;
    private JComboBox<String> gateVolo;

    private JButton confermaButton;
    private JButton resetButton;
    private JButton annullaButton;



    public JFrame frame;
//    private Controller controller;

    public InserisciVolo(JFrame frameChiamante) {
        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

      initListeners(frameChiamante);
    }


    public void initListeners(JFrame frameChiamante) {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compagniaVolo.getText().equals("") || origineVolo.getText().equals("") ||
                destinazioneVolo.getText().equals("") || dataVolo.getText().equals("") || orarioVolo.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame,"Errore: Popolare tutti i campi necessari");
                    return;
                }
                JOptionPane.showMessageDialog(frame,"Volo inserito con successo");
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }


    private void resetFields() {
        codiceVolo.setText("");
        compagniaVolo.setText("");
        origineVolo.setText("");
        destinazioneVolo.setText("");
        dataVolo.setText("");
        orarioVolo.setText("");
        ritardoVolo.setText("");
        gateVolo.setSelectedIndex(-1);
    }

    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
