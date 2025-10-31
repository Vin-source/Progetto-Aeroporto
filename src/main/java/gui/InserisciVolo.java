package gui;
//import controller.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InserisciVolo {
    private JTextField compagniaVolo;
    private JTextField origineVolo;
    private JTextField destinazioneVolo;
    private JTextField dataVolo;
    private JTextField orarioVolo;
    private JTextField ritardoVolo;
    private JComboBox<String> gateVolo;
    private JButton confermaButton;
    private JButton resetButton;
    private JPanel panelRoot;
    private JTextField codiceVolo;

    public JFrame frame;
//    private Controller controller;

    public InserisciVolo(JFrame frameChiamante) {
        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(panelRoot);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Volo inserito con successo");
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }

    /*
    public InserisciVolo(Controller controller, JFrame frameChiamante) {
        this.controller = controller;

        frame = new JFrame("Inserisci Volo");
        frame.setContentPane(panelRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String compagnia = compagniaVolo.getText();
                    String origine = origineVolo.getText();
                    String destinazione = destinazioneVolo.getText();
                    String data = dataVolo.getText();
                    String orario = orarioVolo.getText();
                    String ritardo = ritardoVolo.getText();
                    String gate = gateVolo.getSelectedItem().toString();

                    if(compagnia.isEmpty() || origine.isEmpty() || destinazione.isEmpty() || data.isEmpty() || orario.isEmpty() || gate.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Errore: Compila tutti i campi richiesti (escluso ritardo).");
                        return;
                    }

                    Boolean inserimentoAccettato = controller.inserisciVolo(compagnia, origine, destinazione, data, orario, ritardo, gate);

                    if(inserimentoAccettato){
                        JOptionPane.showMessageDialog(null, "Volo inserito con successo.");
                        resetFields();
                        frameChiamante.setVisible(true);
                        frame.setVisible(false);
                        frame.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Errore nell'inserimento del volo!");
                        resetFields();
                    }



                }catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });
    }
*/
    private void resetFields() {
        compagniaVolo.setText("");
        origineVolo.setText("");
        destinazioneVolo.setText("");
        dataVolo.setText("");
        orarioVolo.setText("");
        ritardoVolo.setText("");
        gateVolo.setSelectedIndex(-1);
    }

    public JPanel getPanel() {
        return panelRoot;
    }
}
