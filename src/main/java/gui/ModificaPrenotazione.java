package gui;

import controller.Controller;
import model.Prenotazione;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe che rappresenta la schermata ModificaPrenotazione
 * Permette all'utente di aggiornare la prenotazione
 */
public class ModificaPrenotazione {
    private JPanel mainPanel;
    private JTextField nome;
    private JTextField cognome;
    private JTextField cartaIdentita;
    private JButton CONFERMAButton;
    private JButton CANCELLAButton;
    private JButton modificaPostoInAereo;
    private JLabel postoPrecedente;
    private JTextField nuovoNumeroBagagli;
    /**
     * Il frame della finestra ModificaPrenotazione
     */
    public JFrame frame;

    private Controller controller;
    private String codiceVolo; // necessario per modificare la prenotazione giusta


    /**
     * Costruisce la finestra per modificare una prenotazione.
     * Inizializza i campi con i dati attuali della prenotazione
     *
     * @param frameChiamante Il frame padre (AreaPersonale.java)
     * @param p              La prenotazione selezionata nel frame padre
     * @param controller     Il controller che modifica la prenotazione nel DB/Model
     */
    public ModificaPrenotazione(Controller controller, JFrame frameChiamante, Prenotazione p, AreaPersonale padre) {


        frame = new JFrame("Modifica Prenotazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;
        this.codiceVolo = p.getCodiceVolo();

        postoPrecedente.setText(p.getPostoAssegnato());
        initListeners(frameChiamante, p, padre);


    }

    /**
     * Metodo che contiene gli ActionListener per i componenti della gui
     * Gestisce la validazione, la scelta del posto e la conferma delle modifiche
     *
     *
     * @param frameChiamante il frame padre
     * @param p la prenotazione selezionata nel frame padre
     * @param padre L'oggetto padre (AreaPersonale) che aggiornerà la nuova lista prenotazioni
     */
    private void initListeners(JFrame frameChiamante, Prenotazione p,  AreaPersonale padre) {
        CONFERMAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuovoNome = nome.getText();
                    String nuovoCognome = cognome.getText();
                    String nuovaCartaIdentita = ModificaPrenotazione.this.cartaIdentita.getText();
                    String nuovoPostoScelto = postoPrecedente.getText();


                    if(nuovoNome.isEmpty() && nuovoCognome.isEmpty() && nuovaCartaIdentita.isEmpty() && nuovoPostoScelto.isEmpty()){
                        throw new IllegalArgumentException("Riempire almeno un valore!");
                    }

                    String risultato = controller.modificaPrenotazione(codiceVolo, nuovoNome, nuovoCognome, nuovaCartaIdentita, nuovoPostoScelto, p);
                    JOptionPane.showMessageDialog(null, risultato);

                    if (risultato.equals("Prenotazione modificata correttamente!")) {
                        resetFields();
                        padre.aggiornaPrenotazioni(controller.getTutteLePrenotazioni());
                        frameChiamante.setVisible(true);
                        frame.dispose();
                    }

                } catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore di Inserimento dati", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception _) {
                    JOptionPane.showMessageDialog(null, "Errore nel sistema di prenotazione", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CANCELLAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        modificaPostoInAereo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceltaPostoInAereo scelta = new SceltaPostoInAereo(controller, frame, null, p.getCodiceVolo(),ModificaPrenotazione.this,  p.getPostoAssegnato());
                scelta.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }

    /**
     * Pulisce i valori inseriti nella pagina GUI
     *
     */
    private void resetFields() {
        nome.setText("");
        cognome.setText("");
        cartaIdentita.setText("");
    }

    /**
     * Ritorna il panel di ModificaPrenotazione.java
     *
     * @return mainPanel
     */
    public JPanel getPanel() {
        return mainPanel;
    }


    /**
     * Modifica nel frame padre
     * il posto scelto dall'utente tramite il file {@link SceltaPostoInAereo}
     *
     * @param nuovoPosto il nuovo posto scelto dall'utente
     */
    public void setPostoScelto(String nuovoPosto) {
        postoPrecedente.setText(nuovoPosto);
    }
}
