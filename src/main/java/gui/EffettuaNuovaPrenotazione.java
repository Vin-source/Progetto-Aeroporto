package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La gui EffettuaNuovaPrenotazione
 */
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
    /**
     * Il frame per EffettuaNuovaPrenotazione
     */
    public JFrame frame;


    private String postoInAereoSelezionato;
    private Controller controller;


    /**
     * Costruttore di EffettuaNuovaPrenotazione.java
     *
     * @param frameChiamante Il frame padre (Utente.java)
     * @param codiceVolo     Il codice del volo selezionato nel frame padre
     * @param controller     Il controller che effettuerà i cambiamenti nel DB/Model
     */
    public EffettuaNuovaPrenotazione(Controller controller, JFrame frameChiamante, String codiceVolo) {

        frame = new JFrame("Dati prenotazione");
        frame.setContentPane(Prenotazione);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.controller = controller;

        initListeners(frameChiamante, codiceVolo);
        postoScelto.setText(" ");
    }


    /**
     * Inizializza gli actionListener della pagina
     *
     * @param frameChiamante il frame padre
     * @param codiceVolo il codice del volo selezionato
     */
    private void initListeners(JFrame frameChiamante, String codiceVolo) {
        prenotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = EffettuaNuovaPrenotazione.this.nome.getText();
                String cognome = EffettuaNuovaPrenotazione.this.cognome.getText();
                String numBagagli = EffettuaNuovaPrenotazione.this.numBagagli.getText();
                String cid = cartaIdentita.getText();
                String posizioneInAereo = postoScelto.getText();


                if (nome.isEmpty() || cognome.isEmpty() || numBagagli.isEmpty() || cid.isEmpty() || posizioneInAereo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Errore: Devi compilare tutti i campi");
                    return;
                }


                int numeroBagagli = Integer.parseInt(numBagagli);
                if(numeroBagagli < 0 || numeroBagagli > 5) {
                    JOptionPane.showMessageDialog(null, "Errore: Inserisci un numero valido di bagagli");
                    return;
                }


                if (posizioneInAereo.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Devi selezionare un posto sull'aereo prima di prenotare.");
                    return;
                }

                controller.effettuaPrenotazione(codiceVolo, nome, cognome, cid, postoInAereoSelezionato); // prenotazione

                JOptionPane.showMessageDialog(null, "Prenotazione effettuata");

                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        cancellaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancellaOperazione(frameChiamante);
            }
        });

        sceltaPostoInAereo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceltaPostoInAereo scelta = new SceltaPostoInAereo(controller, frame, EffettuaNuovaPrenotazione.this, codiceVolo);
                scelta.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }


    /**
     * Effettua una cancellazione della prenotazione di un volo
     * Pulisce tutti i campi nella gui e ritorna alla schermata precedente
     *
     * @param frameChiamante il frame padre che ha generato la schermata attuale
     */
    private void cancellaOperazione(JFrame frameChiamante) {
        nome.setText("");
        cognome.setText("");
        numBagagli.setText("");
        frame.dispose();
        frameChiamante.setVisible(true);
    }

    /**
     * Ritorna il panel della pagina
     *
     * @return prenotazione
     */
    public JPanel getPanel() {
        return Prenotazione;
    }

    /**
     * Salva il posto in aereo selezionato dall'utente
     *
     * @param valore Il posto selezionato
     */
    public void setPostoScelto(String valore) {
        postoScelto.setText(valore);
    }
}
