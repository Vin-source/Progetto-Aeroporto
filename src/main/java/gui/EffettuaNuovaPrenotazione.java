package gui;
// import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffettuaNuovaPrenotazione {
    private JPanel Prenotazione;

    private JTextField nome;
    private JTextField cognome;
    private JTextField numBagagli;
    private JTextField cartaIdentita;


    private JButton prenotaButton;
    private JButton cancellaButton;
    private JButton sceltaPostoInAereo;
    private JLabel postoScelto;
    public JFrame frame;


    private String postoInAereoSelezionato;
    // private Controller controller;

    public EffettuaNuovaPrenotazione(/*Controller controllerEsterno,*/JFrame frameChiamante) {
        frame = new JFrame("Dati prenotazione");
        frame.setContentPane(Prenotazione);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        postoScelto.setText("X");
        // this.controller = controllerEsterno;

        prenotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = EffettuaNuovaPrenotazione.this.nome.getText();
                String cognome = EffettuaNuovaPrenotazione.this.cognome.getText();
                String numBagagli = EffettuaNuovaPrenotazione.this.numBagagli.getText();
                String posizioneInAereo = postoScelto.getText();
                String cid = cartaIdentita.getText();

                if (nome.isEmpty() || cognome.isEmpty() || numBagagli.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Errore: Devi compilare tutti i campi");
                    return;
                }

                int numeroBagagli;
                try {
                    numeroBagagli = Integer.parseInt(numBagagli);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Il numero di bagagli deve essere un numero intero.");
                    return;
                }

                if (posizioneInAereo == null || posizioneInAereo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Devi selezionare un posto sull'aereo prima di prenotare.");
                    return;
                }

                // controller.prenotaVolo(codiceVolo, nome, cognome, cid, postoInAereoSelezionato); // prenotazione

                JOptionPane.showMessageDialog(null, "Prenotazione effettuata");
                resetFields();

                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        cancellaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        sceltaPostoInAereo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // SceltaPostoInAereo scelta = new SceltaPostoInAereo(controller, frame, DatiPrenotazione.this, codiceVolo);
                // scelta.frame.setVisible(true);
                // frame.setVisible(false);
            }
        });
    }

    private void resetFields() {
        nome.setText("");
        cognome.setText("");
        numBagagli.setText("");
    }

    public JPanel getPanel() {
        return Prenotazione;
    }

    public void setPostoScelto(String valore) {
        this.postoInAereoSelezionato = valore;
        postoScelto.setText(this.postoInAereoSelezionato);
    }
}
