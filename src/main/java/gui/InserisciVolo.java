package gui;
import controller.Controller;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Classe della gui che permette di Inserire
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
     * Il frame della pagina InserisciVolo
     */
    public JFrame frame;
    private Controller controller;


    /**
     * Costruisce la finestra per l'insermento di un volo
     *
     * @param frameChiamante Il frame di Amministratore usato per tornare indietro
     * @param controller     Il controller che effettua le chiamate al model/db
     */
    public InserisciVolo(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initFormatters();
        popolaGateDisponibili();
        initListeners(frameChiamante);

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }


    /**
     * Metodo che contiene gli ActionListener dei vari button
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

                if (compagnia.isEmpty() || origine.isEmpty() || destinazione.isEmpty()
                        || data.contains("_") || orario.contains("_") || ritardo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Errore: Popolare tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!origine.equalsIgnoreCase("Napoli") && !destinazione.equalsIgnoreCase("Napoli")) {
                    JOptionPane.showMessageDialog(frame, "Errore: Un volo deve avere come partenza o destinazione Napoli!", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String result = controller.creaNuovoVolo(
                         compagnia, origine, destinazione,
                        data, orario, ritardo, gate
                );

                if (result.equals("Volo inserito con successo!") || result.equals("Volo inserito con successo (senza inserire il gate)")) {
                    JOptionPane.showMessageDialog(frame, result);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, result, "Errore", JOptionPane.ERROR_MESSAGE);
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
     * Init formatters.
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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mostra a schermo quelli che sono i gate non occupati da altri voli
     */
    private void popolaGateDisponibili() {
        ArrayList<String> listaGate = controller.getGateDisponibili();

        gateVolo.removeAllItems(); // Svuota

        gateVolo.addItem(null);

        for (String gateNum : listaGate) {
            gateVolo.addItem(gateNum);
        }
    }


    /**
     * Metodo usato per resettare i campi del form InserisciVolo
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
     * @return I pannelli di InserisciVolo
     */
    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
