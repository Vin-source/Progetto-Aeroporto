package gui;

import controller.Controller;
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
 * Classe che rappresenta la schermata principale dell'utente.
 * Permette di visualizzare,ricercare e prenotare i voli.
 */
public class Utente {
    /**
     * Il Frame della finestra Utente
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
     * Costruisce la finestra che si apre
     * quando l'ospite si logga come utente.
     *
     * @param frameChiamante Il frame di Ospite.java (per il logout)
     * @param controller Il controller che effettua chiamate al DB e comunica con il package Model
     */
    public Utente(Controller controller, JFrame frameChiamante) throws SQLException {
         this.controller = controller;

         frame = new JFrame("Area Utente");
         frame.setContentPane(utenteContainer);
         frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
         listaVoliPanel.setLayout(new BoxLayout(listaVoliPanel, BoxLayout.Y_AXIS));


        aggiornaListaVoli(this.controller.getTuttiVoli());
        initListeners(frameChiamante);

        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Metodo che contiene gli ActionListener per i componenti della gui
     * Gestisce la ricerca dei voli,l'accesso all'area personale e il logout
     * @param frameChiamante Il frame chiamante di Ospite.java
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
            mostraListaVuota();

        }else{
            for(Volo volo: listaVoli){
                JPanel pannelloVolo = new JPanel();
                pannelloVolo.setLayout(new GridLayout(1,9, 10, 10));
                pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

                aggiungiDatiAlVolo(pannelloVolo, volo);

                if(volo.getStatoVolo().equals(StatoVolo.CANCELLATO) || volo.getStatoVolo().equals(StatoVolo.IN_RITARDO)){
                    pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
                if(volo.getStatoVolo().equals(StatoVolo.PROGRAMMATO)){
                    aggiungiBottoniPrenotazione(pannelloVolo, volo);
                }

                listaVoliPanel.add(pannelloVolo);
                listaVoliPanel.add(Box.createVerticalStrut(5));

            }
        }

        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
    }


    public void aggiungiBottoniPrenotazione(JPanel pannelloVolo, Volo volo){
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


    public void mostraListaVuota(){
        JPanel pannelloVolo = new JPanel();
        pannelloVolo.setLayout(new GridLayout(1,9, 10, 10));
        pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

        pannelloVolo.add(new JLabel("Non ci sono voli attualmente disponibili. Ci scusiamo per il disagio!"));

        listaVoliPanel.add(pannelloVolo);
        listaVoliPanel.add(Box.createVerticalStrut(5));
    }


    public void aggiungiDatiAlVolo(JPanel pannelloVolo, Volo volo){
        pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
        pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
        if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
        if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
        pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
        if(volo.getOrigine().equalsIgnoreCase("Napoli")){
            pannelloVolo.add(new JLabel("PARTE ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
        }else{
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
        }
        pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
        pannelloVolo.add(new JLabel("STATO: " + volo.getStatoVolo()));
    }


}