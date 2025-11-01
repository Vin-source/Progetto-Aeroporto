package gui;

// import controller.Controller;
import model.Prenotazione;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;


public class AreaPersonale {
    private JPanel AreaPersonale;
    private JTextField cercaPrenotazione;

    // Controller controller;
    JFrame framePrecedente;
    JFrame frame;

    public AreaPersonale(/*Controller controller, */JFrame framePadre){
        this.framePrecedente = framePadre;
        // this.controller = controller;

        frame = new JFrame("Area Personale");
        frame.setContentPane(AreaPersonale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);



        ArrayList<Prenotazione> p = new ArrayList<>();
        p.add(new Prenotazione("Mimmo", "Raia", "a436ffr", "6F"));
        p.add(new Prenotazione("Mimmo", "Raia", "a436ffr", "2F"));
        p.add(new Prenotazione("Mimmo", "Raia", "a436ffr", "4F"));

        initListeners();
        aggiornaPrenotazioni(p);


        frame.pack();
    }


    public void initListeners() {
        cercaPrenotazione.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaPrenotazioni(/*controller.cercaPrenotazione(cercaPrenotazione.getText())*/);
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaPrenotazioni(/*controller.cercaPrenotazione(cercaPrenotazione.getText())*/);
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });
    }


    public void aggiornaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {

    }
}
