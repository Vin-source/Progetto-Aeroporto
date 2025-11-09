package gui;
import controller.*;
import model.Volo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiornaVolo {
    private JTextField dataAttuale;
    private JTextField nuovaData;
    private JTextField orarioAttuale;
    private JTextField nuovoOrario;
    private JTextField ritardoAttuale;
    private JTextField nuovoRitardo;
    private JButton confermaCambiamentiVolo;
    private JPanel aggiornaVoloPanel;
    private JLabel codiceVoloField;
    private JLabel gateAttuale;
    private JButton modificaGate;
    public JFrame frame;

    public AggiornaVolo(Controller controller, JFrame frameChiamante, Volo volo) {
        frame = new JFrame("Aggiorna Volo");
        frame.setContentPane(aggiornaVoloPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        codiceVoloField.setText(volo.getCodiceVolo());
        dataAttuale.setText(volo.getData());
        orarioAttuale.setText(volo.getOrarioPrevisto());
        ritardoAttuale.setText(String.valueOf(volo.getRitardo()));
        gateAttuale.setText(String.valueOf(volo.getGate().getNumero())); // inserire gate
/*
        confermaCambiamentiVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovaDataText = nuovaData.getText();
                String nuovoOrarioText = nuovoOrario.getText();
                String nuovoRitardoText = nuovoRitardo.getText();
                int gate = Integer.parseInt(gateAttuale.getText());

                if(nuovaDataText.isEmpty() && nuovoOrarioText.isEmpty() && nuovoRitardoText.isEmpty()){
                    codiceVoloField.setText("Non hai inserito nuovi dati!");
                } else {
                    Boolean result = controller.aggiornaVolo(volo.getCodiceVolo(), nuovaDataText, nuovoOrarioText, nuovoRitardoText, gate);
                    if(result){
                        JOptionPane.showMessageDialog(null, "Volo aggiornato con successo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore! Volo non aggiornato");
                    }
                }

                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        modificaGate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaGate nuovoGate = new ModificaGate(controller, AggiornaVolo.this, gateAttuale.getText());
                nuovoGate.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }

    public void impostaNuovoGate(int gate){
        gateAttuale.setText(String.valueOf(gate));
    }
    */
 }
}
