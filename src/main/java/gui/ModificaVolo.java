package gui;
// import controller.*;
import model.Volo;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * La classe ModificaVolo della gui
 */
public class ModificaVolo {
    /**
     * Il frame della classe ModificaVolo
     */
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


    /**
     * Costruisce l'interfaccia che si apre quando
     * l'Amministratore vuole modificare un volo
     * @param frameChiamante Il frame dell'Ammistratore che vuole
     *                       modificare il volo
     * @param volo Il volo da modificare
     */
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


    /**
     * Metodo che contiene gli action listener della form ModificaVolo
     *
     * @param frameChiamante Il frame di Amministratore
     * @param volo           Il volo da modificare
     */
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

    /**
     * Metodo che contiene i formatter della data e dell'ora
     */
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

    /**
     * Metodo che setta il nuovo gate associato al volo
     *
     * @param gate Il gate da modificare
     */
    public void impostaNuovoGate(Integer gate){
        gateAttuale.setText(String.valueOf(gate));
    }
}
