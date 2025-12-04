package gui;
import controller.Controller;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Classe della gui che permette di inserire
 * un nuovo volo
 */
public class InserisciVolo {
    private JPanel inserisciVoloPanel;

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


    /**
     * Il Frame della finestra InserisciVolo
     */
    public JFrame frame;
    private Controller controller;


    /**
     * Costruisce la finestra per l'insermento di un volo
     * Configura il layout, applica i formatter per i campi data/ora,
     * carica la lista dei gate disponibili dal controller e imposta i listener
     *
     * @param frameChiamante Il frame di Amministratore usato per tornare indietro
     * @param controller     Il controller che effettua le chiamate al model/db
     */
    public InserisciVolo(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initFormatters();
        popolaGateDisponibili();
        initListeners(frameChiamante);

        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }


    /**
     * Metodo che contiene gli ActionListener dei vari button
     * Permettono di confermare, annullare o cancellare i campi inseriti.
     *
     * @param frameChiamante Il frame di Amministratore usato per tornare indietro
     */
    public void initListeners(JFrame frameChiamante) {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compagnia = compagniaVolo.getText();
                String origine = origineVolo.getText();
                String destinazione = destinazioneVolo.getText();
                String data = dataVolo.getText().trim();
                String orario = orarioVolo.getText().trim();
                String ritardo = ritardoVolo.getText();
                String gate = (String) gateVolo.getSelectedItem();

                String titoloErrore = "Errore";

                if (compagnia.isEmpty() || origine.isEmpty() || destinazione.isEmpty()
                        || data.contains("_") || orario.contains("_") || ritardo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Errore: Popolare tutti i campi.", titoloErrore, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!origine.equalsIgnoreCase("Napoli") && !destinazione.equalsIgnoreCase("Napoli")) {
                    JOptionPane.showMessageDialog(frame, "Errore: Un volo deve avere come partenza o destinazione Napoli!", titoloErrore, JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String result = controller.creaNuovoVolo(
                         compagnia, origine, destinazione,
                        data, orario, ritardo, gate
                );

                if (result.equals("Volo inserito con successo!")) {
                    JOptionPane.showMessageDialog(frame, result);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, result, titoloErrore, JOptionPane.ERROR_MESSAGE);
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


    /**
     * Configura i MaskFormatter per i campi di input formattati.
     */
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
        } catch (ParseException _) {
            JOptionPane.showMessageDialog(frame, "Errore nella conversione di data e ora");
        }
    }

    /**
     * Mostra a schermo i gate non occupati da altri voli
     */
    private void popolaGateDisponibili() {
        ArrayList<String> listaGate = controller.getGateDisponibili();
        gateVolo.removeAllItems(); // Svuota
        gateVolo.addItem(null);

        if(listaGate == null) return;

        for (String gateNum : listaGate) {
            gateVolo.addItem(gateNum);
        }
    }


    /**
     * Metodo usato per cancellare i campi del form InserisciVolo
     */
    private void resetFields() {
        compagniaVolo.setText("");
        origineVolo.setText("");
        destinazioneVolo.setText("");
        dataVolo.setText("");
        orarioVolo.setText("");
        ritardoVolo.setText("");
        gateVolo.setSelectedIndex(0);
    }

    /**
     * Ritorna i pannelli di InserisciVolo
     *
     * @return L'oggetto JPanel inserisciVoloPanel
     */
    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
