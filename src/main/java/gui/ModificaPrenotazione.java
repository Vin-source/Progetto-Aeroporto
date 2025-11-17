package gui;
// import controller.Controller;
import controller.Controller;
import model.Prenotazione;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La gui ModificaPrenotazione
 */
public class ModificaPrenotazione {
    private JPanel mainPanel;
    private JTextField nome;
    private JTextField cognome;
    private JTextField cartaIdentita;
    private JButton CONFERMAButton;
    private JButton CANCELLAButton;
    /**
     * Il frame di ModificaPrenotazione
     */
    public JFrame frame;

    private Controller controller;
    private String codiceVolo; // necessario per modificare la prenotazione giusta


    /**
     * Costruttore di ModificaPrenotazione.java
     *
     * @param frameChiamante Il frame padre (AreaPersonale.java)
     * @param p              La prenotazione selezionata nel frame padre
     * @param controller     Il controller che modifica la prenotazione nel DB/Model
     */
    public ModificaPrenotazione(Controller controller, JFrame frameChiamante, Prenotazione p) {


        frame = new JFrame("Modifica Prenotazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;
        this.codiceVolo = p.getCodiceVolo();

        initListeners(frameChiamante, p);

    }

    /**
     * Inizializza i Listener
     *
     * @param frameChiamante il frame padre
     * @param p la prenotazione selezionata nel frame padre
     */
    private void initListeners(JFrame frameChiamante, Prenotazione p) {

        CONFERMAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuovoNome = nome.getText();
                    String nuovoCognome = cognome.getText();
                    String cartaIdentita = ModificaPrenotazione.this.cartaIdentita.getText();


                    // prima verifica se non c'è nessun nuovo valore,
                    // dopodichè sostituisco i valori vuoti con i vecchi valori
                    // così da non creare problemi nell'aggiornamento del database
                    if(nuovoNome.isEmpty() && nuovoCognome.isEmpty() && cartaIdentita.isEmpty()){
                        throw new IllegalArgumentException("Riempire almeno un valore!");
                    }
                    if(nuovoNome.isEmpty()) {
                        nuovoNome = p.getNome();
                    }
                    if(nuovoCognome.isEmpty()) {
                        nuovoCognome = p.getCognome();
                    }
                    if(cartaIdentita.isEmpty()) {
                        cartaIdentita = p.getCartaIdentita();
                    }



                    boolean risultato = controller.modificaPrenotazione(codiceVolo, nuovoNome, nuovoCognome, cartaIdentita, p.getIdPrenotazione());
                    if (risultato) {
                        JOptionPane.showMessageDialog(null, "Prenotazione modificata con successo");
                        resetFields();
                        frameChiamante.setVisible(true);
                        frame.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Modifica fallita: prenotazione non trovata");
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
    }

    /**
     * Pulisce i valori inseriti nella pagina gui
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
}
