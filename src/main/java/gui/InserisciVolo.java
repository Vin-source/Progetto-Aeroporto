package gui;
//import controller.*;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Classe della gui che permette di Inserire
 * un nuovo volo
 */
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


    /**
     * Il frame della pagina InserisciVolo
     */
    public JFrame frame;
//    private Controller controller;

    /**
     * Costruisce la finestra per l'insermento di un volo
     *
     * @param frameChiamante Il frame di Amministratore usato per tornare indietro
     */
    public InserisciVolo(JFrame frameChiamante) {
        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        initFormatters();
        initListeners(frameChiamante);
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
     * Metodo usato per resettare i campi del form InserisciVolo
     */
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

    /**
     * Ritorna i pannelli di InserisciVolo
     *
     * @return I pannelli di InserisciVolo
     */
    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
