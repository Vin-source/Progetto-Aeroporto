package gui;

import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


/**
 * La gui AreaPersonale
 */
public class AreaPersonale {
    private JPanel AreaPersonale;
    private JTextField id;
    private JTextField email;
    private JTextField cercaPrenotazione;
    private JScrollPane listaPrenotazioniScroll;
    private JPanel listaPrenotazioni;
    private JButton indietroButton;

    Controller controller;
    /**
     * Il frame di AreaPersonale.java
     */
    JFrame frame;


    /**
     * Costruttore di AreaPersonale
     *
     * @param framePadre Il frame padre (Utente.java)
     * @param controller Il controller che effettua collegamenti con DB/Model
     */
    public AreaPersonale(Controller controller, JFrame framePadre){
        this.controller = controller;


        frame = new JFrame("Area Personale");
        frame.setContentPane(AreaPersonale);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        email.setText(this.controller.getEmail());
        id.setText("Pippo");
        email.setEditable(false);
        id.setEditable(false);

        listaPrenotazioni.setLayout(new BoxLayout(listaPrenotazioni, BoxLayout.Y_AXIS));


        aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni());
        initListeners(framePadre);



        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Inizializza i Listener
     *
     * @param framePadre Il frame padre
     */
    public void initListeners(JFrame framePadre) {

        cercaPrenotazione.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aggiornaPrenotazioni(controller.ricercaPrenotazioni(cercaPrenotazione.getText()));
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaPrenotazioni(controller.ricercaPrenotazioni(cercaPrenotazione.getText()));
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

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePadre.setVisible(true);
                frame.dispose();
            }
        });
    }


    /**
     * Crea i JPanel per ogni prenotazione e li popola con i dati
     * corrispondenti comunicando con il package model
     *
     * @param prenotazioni ArrayList delle prenotazioni effettuate dallo specifico utente
     */
    public void aggiornaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        listaPrenotazioni.removeAll();

        if(prenotazioni.isEmpty()){
            JPanel prenotazione = new JPanel();
            prenotazione.setLayout(new GridLayout(1,7, 10, 10));
            prenotazione.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa


            prenotazione.add(new JLabel("Non hai nessuna prenotazione effettuata!"));

            listaPrenotazioni.add(prenotazione);
            listaPrenotazioni.add(Box.createVerticalStrut(5));
        }
        else{


            for(Prenotazione p : prenotazioni){
                JPanel prenotazione = new JPanel();
                prenotazione.setLayout(new GridLayout(1,8, 10, 10));
                prenotazione.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

                prenotazione.add(new JLabel("ID: " + p.getIdPrenotazione().toUpperCase()));
                prenotazione.add(new JLabel("NOME: " + p.getNome().toUpperCase()));
                prenotazione.add(new JLabel("COGNOME: " + p.getCognome().toUpperCase()));
                prenotazione.add(new JLabel("CARTA D'IDENTITA: " + p.getCartaIdentita().toUpperCase()));
                prenotazione.add(new JLabel("POSTO ASSEGNATO: " + p.getPostoAssegnato()));
                prenotazione.add(new JLabel("STATO: " + p.getStatoPrenotazione()));
                JButton modificaPrenotazione = new JButton("MODIFICA");
                JButton cancellaPrenotazione = new JButton("CANCELLA");
                prenotazione.add(modificaPrenotazione);
                prenotazione.add(cancellaPrenotazione);

                modificaPrenotazione.addActionListener(e -> {
                    new ModificaPrenotazione(this.controller, frame, p, gui.AreaPersonale.this).frame.setVisible(true);
                });

                cancellaPrenotazione.addActionListener(e -> {
                   if(controller.cancellaPrenotazione(p.getIdPrenotazione())){
                       JOptionPane.showMessageDialog(null, "La prenotazione è stata eliminata", "Avviso", JOptionPane.ERROR_MESSAGE);
                        aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni());
                   }
                });


                listaPrenotazioni.add(prenotazione);
                listaPrenotazioni.add(Box.createVerticalStrut(5));

            }
        }

        listaPrenotazioni.revalidate();
        listaPrenotazioni.repaint();
        frame.pack();
    }
}
