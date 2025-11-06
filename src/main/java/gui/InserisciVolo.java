package gui;
import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private JPanel inserisciVoloPanel;
    private JTextField codiceVolo;
    private JButton annullaButton;

    public JFrame frame;
    private Controller controller;

    public InserisciVolo(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        frame = new JFrame("Schermata InserisciVolo");
        frame.setContentPane(inserisciVoloPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codVoloInserito = codiceVolo.getText();
                    String compagniaAInserita = compagniaVolo.getText();
                    String origineInserita = origineVolo.getText();
                    String destinazioneInserita = destinazioneVolo.getText();
                    String dataInserita = dataVolo.getText();
                    String orarioInserito = orarioVolo.getText();
                    String ritardoInserito = ritardoVolo.getText();
                    String gateVoloInserito = gateVolo.getSelectedItem().toString();

                    JOptionPane.showMessageDialog(frame, "Pulsante premuto"); //solo per testing
                    boolean conferma = controller.creaNuovoVolo(codVoloInserito, compagniaAInserita, origineInserita, destinazioneInserita, dataInserita, orarioInserito, ritardoInserito, gateVoloInserito);
                    if (conferma == true) {
                        JOptionPane.showMessageDialog(frame, "Volo inserito con successo");
                        frameChiamante.setVisible(true);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Errore: Dati non validi. Controlla e riprova.", "Errore Inserimento", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(frame,"Errore: Dati non validi. Controlla e riprova.\n(Formato data d/MM/yyyy, orario HH:mm, ritardo e gate numerici)","Errore Inserimento", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
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
        codiceVolo.setText("");
        compagniaVolo.setText("");
        origineVolo.setText("");
        destinazioneVolo.setText("");
        dataVolo.setText("");
        orarioVolo.setText("");
        ritardoVolo.setText("");
        gateVolo.setSelectedIndex(-1);
    }

    public JPanel getPanel(){
        return inserisciVoloPanel;
    }
}
