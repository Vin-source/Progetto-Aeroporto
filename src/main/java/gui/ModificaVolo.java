package gui;
// import controller.*;
import model.Volo;

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


    public ModificaVolo(/*Controller controller,*/ JFrame frameChiamante, Volo volo) {
        frame = new JFrame("Aggiorna Volo");
        frame.setContentPane(aggiornaVoloPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        codiceVolo.setText(volo.getCodiceVolo());
        dataAttuale.setText(volo.getData());
        orarioAttuale.setText(volo.getOrarioPrevisto());
        ritardoAttuale.setText(String.valueOf(volo.getRitardo()));
        gateAttuale.setText(String.valueOf(volo.getGate().getNumero()));

        dataAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        ritardoAttuale.setEditable(false);

        initFormatters();
        initListeners(frameChiamante, volo);
    }


    public void initListeners(JFrame frameChiamante, Volo volo) {

        confermaCambiamentiVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovaDataText = nuovaData.getText();
                String nuovoOrarioText = nuovoOrario.getText();
                String nuovoRitardoText = nuovoRitardo.getText();
                int gate = Integer.parseInt(gateAttuale.getText());

                if(nuovaDataText.contains("_")){
                    nuovaDataText = volo.getData();
                }
                if(nuovoOrarioText.contains("_")){
                    nuovoOrarioText = volo.getOrarioPrevisto();
                }
                if(nuovoRitardoText.isEmpty()){
                    nuovoRitardoText = String.valueOf(volo.getRitardo());
                }

                /*



                    Boolean result = controller.aggiornaVolo(volo.getCodiceVolo(), nuovaDataText, nuovoOrarioText, nuovoRitardoText, gate);
                    if(result){
                        JOptionPane.showMessageDialog(null, "Volo aggiornato con successo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore! Volo non aggiornato");
                    }

                */
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        modificaGate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaGate nuovoGate = new ModificaGate(/*controller, AggiornaVolo.this, gateAttuale.getText()*/frame, gateAttuale.getText(), ModificaVolo.this);
                nuovoGate.frame.setVisible(true);
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
