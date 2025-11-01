package gui;

// import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;



public class AreaPersonale {
    private JPanel AreaPersonale;
    private JTextField id;
    private JTextField email;
    private JTextField cercaPrenotazione;
    private JScrollPane listaPrenotazioniScroll;
    private JPanel listaPrenotazioni;

    // Controller controller;
    JFrame frame;

    public AreaPersonale(/*Controller controller,*/JFrame framePadre){
        // this.controller = controller;

        frame = new JFrame("Area Personale");
        frame.setContentPane(AreaPersonale);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        email.setText("Pippo");
        id.setText("Pippo");
        email.setEditable(false);
        id.setEditable(false);

        listaPrenotazioni.setLayout(new BoxLayout(listaPrenotazioni, BoxLayout.Y_AXIS));
        ArrayList<Prenotazione> p = new ArrayList<>();
        Prenotazione p1 = new Prenotazione("Mimmo", "Raia", "a436ffr", "6F");
        p1.setIdPrenotazione("asdhreui");
        p.add(p1);
        p.add(p1);
        p.add(p1);


        aggiornaPrenotazioni(p);
        initListeners(framePadre);



        frame.pack();
    }


    public void initListeners(JFrame framePadre) {
        ArrayList<Prenotazione> p = new ArrayList<>();
        Prenotazione p1 = new Prenotazione("Mimmo", "Raia", "a436ffr", "6F");
        p1.setIdPrenotazione("asdhreui");
        p.add(p1);
        p.add(p1);
        p.add(p1);
        cercaPrenotazione.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaPrenotazioni(/*controller.cercaPrenotazione(cercaPrenotazione.getText())*/p);
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaPrenotazioni(/*controller.cercaPrenotazione(cercaPrenotazione.getText())*/p);
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                framePadre.setVisible(true);
            }
            @Override
            public void windowClosing(WindowEvent e) {
                framePadre.setVisible(true);
            }
        });
    }


    public void aggiornaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        listaPrenotazioni.removeAll();


        for(Prenotazione p : prenotazioni){
            JPanel prenotazione = new JPanel();
            prenotazione.setLayout(new GridLayout(1,4, 10, 10));
            prenotazione.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

            prenotazione.add(new JLabel("ID: " + p.getIdPrenotazione().toUpperCase()));
            prenotazione.add(new JLabel("NOME: " + p.getNome().toUpperCase()));
            prenotazione.add(new JLabel("COGNOME: " + p.getCognome().toUpperCase()));
            prenotazione.add(new JLabel("CARTA D'IDENTITA: " + p.getCartaIdentita().toUpperCase()));
            prenotazione.add(new JLabel("POSTO ASSEGNATO: " + p.getPostoAssegnato()));




            listaPrenotazioni.add(prenotazione);
            listaPrenotazioni.add(Box.createVerticalStrut(5));

        }

        listaPrenotazioni.revalidate();
        listaPrenotazioni.repaint();
        frame.pack();
    }
}
