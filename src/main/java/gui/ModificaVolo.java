package gui;
import controller.Controller;
import model.Volo;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Classe che rappresenta la schermata per modificare i voli
 *
 */
public class ModificaVolo {
    /**
     * Il frame della finestra ModificaVolo
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
    private JButton AnnullaBUTTON;

    private Controller controller;

    /**
     * Costruisce l'interfaccia che si apre quando
     * l'Amministratore vuole modificare un volo
     * @param frameChiamante Il frame della finestra Ammistratore che vuole
     *                       modificare il volo
     * @param controller il controller che effettua chiamate al model/db
     * @param volo L'oggetto Volo contenente i dati da visualizzare/modificare
     */
    public ModificaVolo(JFrame frameChiamante, Controller controller,Volo volo) {
        this.controller = controller;

        frame = new JFrame("Aggiorna Volo");
        frame.setContentPane(aggiornaVoloPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        codiceVolo.setText(volo.getCodiceVolo());
        dataAttuale.setText(volo.getData());
        orarioAttuale.setText(volo.getOrarioPrevisto());
        ritardoAttuale.setText(String.valueOf(volo.getRitardo()));

        if (volo.getGate() != null) {
            gateAttuale.setText(String.valueOf(volo.getGate().getNumero()));
        } else {
            gateAttuale.setText("Gate non assegnato");
        }

        dataAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        ritardoAttuale.setEditable(false);

        initFormatters();
        initListeners(frameChiamante, volo);

        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }


    /**
     * Metodo che contiene gli action listener della form ModificaVolo
     *
     * @param frameChiamante Il frame della finestra Amministratore
     * @param volo           L'oggetto volo da modificare
     */
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



                String result = controller.aggiornaVolo(volo.getCodiceVolo(), nuovaDataText, nuovoOrarioText, nuovoRitardoText, gateText);

                JOptionPane.showMessageDialog(frame, result);


                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        modificaGate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificaGate(frame, controller, gateAttuale.getText(),ModificaVolo.this, volo.getCodiceVolo());
                frame.setVisible(false);
            }
        });

        AnnullaBUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
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
     * @param gate Il nuovo numero del gate selezionato
     */
    public void impostaNuovoGate(String gate){
        gateAttuale.setText(gate);
    }
}
