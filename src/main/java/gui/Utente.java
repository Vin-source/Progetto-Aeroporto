package gui;

// import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class Utente {
    public JFrame frame;
    private JPanel utenteContainer;

    private JButton areaPersonaleButton;
    private JTextField barraDiRicerca;
    private JPanel listaVoliPanel;
    private JLabel voli;

    // private Controller controller;

    public Utente(/*Controller controller*/) {
        // this.controller = controller;
         frame = new JFrame("Area Utente");
         frame.setContentPane(utenteContainer);
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         frame.pack();
         frame.setVisible(true);


        listaVoliPanel.setLayout(new GridLayout(0, 2, 10, 10));

        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        aggiornaListaVoli(voli);
        // Listener Area Personale
        /*
        areaPersonaleButton.addActionListener(e ->{
            AreaPersonale p = new AreaPersonale(controller, frame);
            p.frame.setVisible(true);
            frame.setVisible(false);
        });
        */


        // Listener per la barra di ricerca
        barraDiRicerca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaListaVoli(/*controller.cercaVolo(barraDiRicerca.getText())*/voli);
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaListaVoli(/*controller.cercaVolo(barraDiRicerca.getText())*/voli);
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });

         

        // Caricamento iniziale dei voli
        // aggiornaListaVoli(controller.getVoli());
    }


    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {

        listaVoliPanel.removeAll();
        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
        frame.pack(); // ?? funziona ??


        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,7, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
            pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
            if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
            if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
            pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
            pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
            pannelloVolo.add(new JButton("PRENOTA"));


            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));
            listaVoliPanel.revalidate();
            listaVoliPanel.repaint();
            frame.pack();
        }
    }





}