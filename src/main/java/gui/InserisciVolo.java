package gui;
//import controller.*;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class InserisciVolo {
    private JPanel inserisciVoloPanel;

    private JTextField codiceVolo;
    private JTextField compagniaVolo;
    private JTextField origineVolo;
    private JTextField destinazioneVolo;
    private JFormattedTextField dataVolo;
    private JFormattedTextField orarioVolo;
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


        initFormatters();
        initListeners(frameChiamante);
    }


    public void initListeners(JFrame frameChiamante) {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(compagniaVolo.getText().equals("") || origineVolo.getText().equals("") ||
                destinazioneVolo.getText().equals("") || dataVolo.getText().contains("_") || orarioVolo.getText().contains("_")) {
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


    public void initFormatters() {
        try {
            MaskFormatter formatterOra = new MaskFormatter("##:##");
            MaskFormatter formatterData = new MaskFormatter("##/##/####");
            formatterData.setPlaceholderCharacter('_');
            formatterOra.setPlaceholderCharacter('_');
            dataVolo.setFormatterFactory(new DefaultFormatterFactory(formatterData));
            orarioVolo.setFormatterFactory(new DefaultFormatterFactory(formatterOra));
            dataVolo.setColumns(10);
            orarioVolo.setColumns(2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
