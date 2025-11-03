package gui;

// import controller.Controller;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Utente {
    public JFrame frame;
    private JPanel utenteContainer;

    private JTextField barraDiRicerca;
    private JPanel listaVoliPanel;
    private JLabel voli;
    private JButton areaPersonaleButton;
    private JButton logoutButton;

    // private Controller controller;

    public Utente(/*Controller controller*/JFrame frameChiamante) {
        // this.controller = controller;
         frame = new JFrame("Area Utente");
         frame.setContentPane(utenteContainer);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //listaVoliPanel.setLayout(new GridLayout(0, 2, 10, 10));
        listaVoliPanel.setLayout(new BoxLayout(listaVoliPanel, BoxLayout.Y_AXIS));

        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        aggiornaListaVoli(voli);
        // Listener Area Personale



         initListeners(frameChiamante);

        // Caricamento iniziale dei voli
        // aggiornaListaVoli(controller.getVoli());
        frame.setVisible(true);
        frame.pack();

    }

    private void initListeners(JFrame frameChiamante) {

        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));


        areaPersonaleButton.addActionListener(e ->{
             AreaPersonale p = new AreaPersonale(/*controller, */ this.frame);
             p.frame.setVisible(true);
             frame.setVisible(false);
        });



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

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }


    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {

        listaVoliPanel.removeAll();


        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,7, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

            pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
            pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
            if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
            if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
            pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
            pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
            JButton prenotazione = new JButton("PRENOTA");
            pannelloVolo.add(prenotazione);

            prenotazione.addActionListener(e -> {
                new EffettuaNuovaPrenotazione(frame, volo.getCodiceVolo()).frame.setVisible(true);
                frame.dispose();
            }); // event to add


            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));

        }

        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
        frame.pack();
    }





}