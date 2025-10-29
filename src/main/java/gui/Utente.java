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
    private JPanel utenteContainer;
    private JButton areaPersonaleButton;
    private JTextField barraDiRicerca;
    private JPanel listaVoliPanel;
    private JLabel voli;

    // private Controller controller;

    public Utente(/*Controller controller*/) {
        // this.controller = controller;
        // frame = new JFrame("Area Utente");
        // frame.setContentPane(utente);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        // frame.setVisible(true);

        // Listener Area Personale
        /*
        areaPersonaleButton.addActionListener(e ->{
            AreaPersonale p = new AreaPersonale(controller, frame);
            p.frame.setVisible(true);
            frame.setVisible(false);
        });

        // Listener per la barra di ricerca
        barraDiRicerca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaListaVoli(controller.cercaVolo(barraDiRicerca.getText()));
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaListaVoli(controller.cercaVolo(barraDiRicerca.getText()));
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });
        */
         

        // Caricamento iniziale dei voli
        // aggiornaListaVoli(controller.getVoli());
    }
/*
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        listaVoliPanel.removeAll();
        // ArrayList<Prenotazione> listaVoliPrenotati = controller.getVoliPrenotati();

        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(0,2));

            pannelloVolo.add(new JLabel("Codice volo: "));
            JTextField codice = new JTextField(volo.getCodiceVolo());
            codice.setEditable(false);
            pannelloVolo.add(codice);

            pannelloVolo.add(new JLabel("Compagnia aerea: "));
            JTextField compagnia = new JTextField(volo.getCompagniaAerea());
            compagnia.setEditable(false);
            pannelloVolo.add(compagnia);

            pannelloVolo.add(new JLabel("Origine: "));
            JTextField origine = new JTextField(volo.getOrigine());
            origine.setEditable(false);
            pannelloVolo.add(origine);

            pannelloVolo.add(new JLabel("Destinazione: "));
            JTextField destinazione = new JTextField(volo.getDestinazione());
            destinazione.setEditable(false);
            pannelloVolo.add(destinazione);

            pannelloVolo.add(new JLabel("Data: "));
            JTextField data = new JTextField(volo.getData());
            data.setEditable(false);
            pannelloVolo.add(data);

            pannelloVolo.add(new JLabel("Orario di arrivo: "));
            JTextField orario = new JTextField(volo.getOrarioPrevisto());
            orario.setEditable(false);
            pannelloVolo.add(orario);

            pannelloVolo.add(new JLabel("Ritardo: "));
            JTextField ritardo = new JTextField(volo.getRitardo());
            ritardo.setEditable(false);
            pannelloVolo.add(ritardo);

            JButton prenota = new JButton("Prenota");
            pannelloVolo.add(prenota);


            for(Prenotazione p : listaVoliPrenotati){
                if(p.getVolo().getCodiceVolo().equals(volo.getCodiceVolo())){
                    JButton modifica = new JButton("Modifica");
                    modifica.addActionListener(e -> {
                        ModificaPrenotazione modificaPrenotazioneGUI = new ModificaPrenotazione(controller, frame, volo.getCodiceVolo());
                        modificaPrenotazioneGUI.frame.setVisible(true);
                        frame.setVisible(false);
                    });
                    pannelloVolo.add(modifica);
                }
            }

            prenota.addActionListener(e -> {
                DatiPrenotazione dati = new DatiPrenotazione(controller, frame, volo.getCodiceVolo());
                dati.frame.setVisible(true);
                frame.setVisible(false);
            });

            listaVoliPanel.add(pannelloVolo);
        }

        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
    }

    public JPanel getPanel() {
        return utente;
    }
    */

}