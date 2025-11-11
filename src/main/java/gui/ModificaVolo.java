package gui;
import controller.Controller;
import model.Volo;
import model.Gate;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ModificaVolo {
    public JFrame frame;

    private JTextField dataAttuale;
    private JFormattedTextField nuovaData;
    private JTextField orarioAttuale;
    private JFormattedTextField nuovoOrario;
    private JTextField ritardoAttuale;
    private JTextField nuovoRitardo;


    private JButton confermaCambiamentiVolo;
    private JPanel aggiornaVoloPanel;
    private JLabel codiceVolo;

    private JLabel gateAttuale;
    private JButton modificaGate;

    private Controller controller;

    public ModificaVolo(JFrame frameChiamante, Controller controller,Volo volo) {
        this.controller = controller;
        frame = new JFrame("Aggiorna Volo");
        frame.setContentPane(aggiornaVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        codiceVolo.setText(volo.getCodiceVolo());
        dataAttuale.setText(volo.getData());
        orarioAttuale.setText(volo.getOrarioPrevisto());
        ritardoAttuale.setText(String.valueOf(volo.getRitardo()));
        gateAttuale.setText(String.valueOf(volo.getGate().getNumero()));

        dataAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        ritardoAttuale.setEditable(false);

        initFormatters();
        initListeners(frameChiamante, volo);

        frame.pack();
        frame.setVisible(true);
    }


    public void initListeners(JFrame frameChiamante, Volo volo) {

        confermaCambiamentiVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovaDataText = nuovaData.getText();
                String nuovoOrarioText = nuovoOrario.getText();
                String nuovoRitardoText = nuovoRitardo.getText();
                String gateText = gateAttuale.getText();

                if(nuovaDataText.contains("_")){
                    nuovaDataText = volo.getData();
                }
                if(nuovoOrarioText.contains("_")){
                    nuovoOrarioText = volo.getOrarioPrevisto();
                }
                if(nuovoRitardoText.isEmpty()){
                    nuovoRitardoText = String.valueOf(volo.getRitardo());
                }





                    Boolean result = controller.aggiornaVolo(volo.getCodiceVolo(), nuovaDataText, nuovoOrarioText, nuovoRitardoText, gateText);
                    if(result){
                        JOptionPane.showMessageDialog(frame, "Volo aggiornato con successo.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Errore! Volo non aggiornato");
                    }


                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        modificaGate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificaGate(frame, controller, volo.getGate()),ModificaVolo.this);
                frame.setVisible(false);
            }
        });
    }

    public void initFormatters() {
        try {
            MaskFormatter formatterOra = new MaskFormatter("##:##");
            MaskFormatter formatterData = new MaskFormatter("##/##/####");
            formatterData.setPlaceholderCharacter('_');
            formatterOra.setPlaceholderCharacter('_');
            nuovaData.setFormatterFactory(new DefaultFormatterFactory(formatterData));
            nuovoOrario.setFormatterFactory(new DefaultFormatterFactory(formatterOra));
            nuovaData.setColumns(10);
            nuovoOrario.setColumns(2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void impostaNuovoGate(Integer gate){
        gateAttuale.setText(String.valueOf(gate));
    }
}
