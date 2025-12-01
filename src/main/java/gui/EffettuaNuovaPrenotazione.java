package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe che gestisce  il processo di creazione di una nuova prenotazione.
 * Permette di inserire i dati anagrafici del passeggero.
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
    private JTextField pesoTotaleDeiBagagli;
    /**
     * Il Frame della finestra EffettuaNuovaPrenotazione
     */
    public JFrame frame;


    private Controller controller;


    /**
     * Costruisce la finestra per effettuare una nuova prenotazione
     *
     * @param frameChiamante Il frame padre (Utente.java)
     * @param codiceVolo     Il codice del volo selezionato nel frame padre
     * @param controller     Il controller che effettuerà i cambiamenti nel DB/Model
     */
    public EffettuaNuovaPrenotazione(Controller controller, JFrame frameChiamante, String codiceVolo) {

        frame = new JFrame("Dati prenotazione");
        frame.setContentPane(Prenotazione);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        this.controller = controller;

        initListeners(frameChiamante, codiceVolo);
        postoScelto.setText(" ");
    }


    /**
     * Inizializza gli actionListener della pagina
     * Gestisce i dati di input
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


                if(numeroBagagli != 0 && pesoTotaleDeiBagagli.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Errore: Inserisci anche il peso totale dei bagagli");
                    return;
                }
                if(pesoTotaleDeiBagagli.getText().isEmpty()) pesoTotaleDeiBagagli.setText("0");


                if (posizioneInAereo.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Devi selezionare un posto sull'aereo prima di prenotare.");
                    return;
                }


                String res = controller.effettuaPrenotazione(codiceVolo, nome, cognome, cid, postoScelto.getText(), numeroBagagli, pesoTotaleDeiBagagli.getText());

                JOptionPane.showMessageDialog(null, res);



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
                SceltaPostoInAereo scelta = new SceltaPostoInAereo(controller, frame, EffettuaNuovaPrenotazione.this, codiceVolo, null, null);
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
     * @return Il JPanel che contiene Prenotazione
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
