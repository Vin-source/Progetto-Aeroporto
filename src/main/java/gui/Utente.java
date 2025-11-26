package gui;

import controller.Controller;
import model.StatoPrenotazione;
import model.StatoVolo;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * La Gui Utente
 */
public class Utente {
    /**
     * Frame dell'utente
     */
    public JFrame frame;
    private JPanel utenteContainer;

    private JTextField barraDiRicerca;
    private JPanel listaVoliPanel;
    private JLabel voli;
    private JButton areaPersonaleButton;
    private JButton logoutButton;

    private Controller controller;

    /**
     * Costruttore della classe Utente
     *
     * @param frameChiamante Il frame padre (Ospite.java)
     * @param controller Il controller che effettua chiamate al DB e comunica con il package Model
     */
    public Utente(Controller controller, JFrame frameChiamante) throws SQLException {
         this.controller = controller;

         frame = new JFrame("Area Utente");
         frame.setContentPane(utenteContainer);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         listaVoliPanel.setLayout(new BoxLayout(listaVoliPanel, BoxLayout.Y_AXIS));


        aggiornaListaVoli(this.controller.getTuttiVoli());
        initListeners(frameChiamante);
        frame.setVisible(true);
        frame.pack();

    }

    /**
     * Inizializza gli actionListener
     *
     * @param frameChiamante il frame Padre
     */
    private void initListeners(JFrame frameChiamante) {


        areaPersonaleButton.addActionListener(e ->{
             AreaPersonale p = new AreaPersonale(this.controller, this.frame);
             p.frame.setVisible(true);
             frame.setVisible(false);
        });



        // Listener per la barra di ricerca
        barraDiRicerca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaListaVoli(controller.cercaVoli(barraDiRicerca.getText()));
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaListaVoli(controller.cercaVoli(barraDiRicerca.getText()));
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }


    /**
     * Rappresenta tutti i voli nella gui Utente.
     * Implementato come {@link #aggiornaListaVoli(ArrayList)} aggiornaListaVoli} con l'aggiunta
     * di un bottone per la prenotazione del volo corrispondente
     * @param listaVoli ArrayList dei voli disponibili
     */
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {

        listaVoliPanel.removeAll();

        if(listaVoli.isEmpty()){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,8, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

            pannelloVolo.add(new JLabel("Non ci sono voli attualmente disponibili. Ci scusiamo per il disagio!"));

            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));

        }else{
            for(Volo volo: listaVoli){
                JPanel pannelloVolo = new JPanel();
                pannelloVolo.setLayout(new GridLayout(1,8, 10, 10));
                pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

                pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
                pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
                if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
                if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
                pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
                pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
                pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
                pannelloVolo.add(new JLabel("STATO: " + volo.getStatoVolo()));
                if(!volo.getStatoVolo().equals(StatoVolo.CANCELLATO)){
                    JButton prenotazione = new JButton("PRENOTA");
                    pannelloVolo.add(prenotazione);

                    prenotazione.addActionListener(e -> {
                        if(!volo.getStatoVolo().equals(StatoVolo.PROGRAMMATO)){
                            JOptionPane.showMessageDialog(null, "Non è possibile prenotare questo volo, solo voli programmati", "Errore", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        new EffettuaNuovaPrenotazione(controller, frame, volo.getCodiceVolo()).frame.setVisible(true);
                        frame.dispose();
                    });
                }

                listaVoliPanel.add(pannelloVolo);
                listaVoliPanel.add(Box.createVerticalStrut(5));

            }
        }

        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
        frame.pack();
    }





}