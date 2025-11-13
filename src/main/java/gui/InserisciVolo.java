package gui;
import controller.Controller;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

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
    private Controller controller;

    public InserisciVolo(JFrame frameChiamante, Controller controller) {
        this.controller = controller;
        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initFormatters();
        popolaGateDisponibili();
        initListeners(frameChiamante);

        frame.pack();
        frame.setVisible(true);
    }


    public void initListeners(JFrame frameChiamante) {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codVolo = codiceVolo.getText();
                String compagnia = compagniaVolo.getText();
                String origine = origineVolo.getText();
                String destinazione = destinazioneVolo.getText();
                String data = dataVolo.getText();
                String orario = orarioVolo.getText();
                String ritardo = ritardoVolo.getText();
                String gate = (String) gateVolo.getSelectedItem();

                if (codVolo.isEmpty() || compagnia.isEmpty() || origine.isEmpty() || destinazione.isEmpty()
                        || data.contains("_") || orario.contains("_") || ritardo.isEmpty() || gate == null) {
                    JOptionPane.showMessageDialog(frame, "Errore: Popolare tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                Boolean result = controller.creaNuovoVolo(
                        codVolo, compagnia, origine, destinazione,
                        data, orario, ritardo, gate
                );

                if (result) {
                    JOptionPane.showMessageDialog(frame, "Volo inserito con successo");
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore: Dati non validi.Controlla formato data/ora e che i numeri siano corretti.", "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
                }
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



    private void popolaGateDisponibili() {
        ArrayList<String> listaGate = controller.getGateDisponibili();

        gateVolo.removeAllItems(); // Svuota

        gateVolo.addItem(null);

        for (String gateNum : listaGate) {
            gateVolo.addItem(gateNum);
        }
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
        gateVolo.setSelectedIndex(0);
    }

    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
