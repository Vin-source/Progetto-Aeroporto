package gui;

import controller.Controller;
import model.Bagaglio;
import model.Prenotazione;
import model.StatoPrenotazione;

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
    private JCheckBox cercaBagagliCheckBox;

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


        email.setText(this.controller.getEmail());
        id.setText("Pippo");
        email.setEditable(false);
        id.setEditable(false);

        listaPrenotazioni.setLayout(new BoxLayout(listaPrenotazioni, BoxLayout.Y_AXIS));


        aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni());
        initListeners(framePadre);



        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }


    /**
     * Inizializza i Listener
     *
     * @param framePadre Il frame padre
     */
    public void initListeners(JFrame framePadre) {

        cercaPrenotazione.getDocument().addDocumentListener(new DocumentListener() {
            private void aggiornaP() {
                SwingUtilities.invokeLater(() -> {
                    String testo = cercaPrenotazione.getText();

                    if(cercaBagagliCheckBox.isSelected()){
                        aggiornaPrenotazioni(controller.ricercaPrenotazioni(testo, true));
                    }else{
                        aggiornaPrenotazioni(controller.ricercaPrenotazioni(testo, false));
                    }

                    cercaPrenotazione.requestFocusInWindow();
                });
            }

            public void insertUpdate(DocumentEvent e) {
                aggiornaP();
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaP();
            }
            public void changedUpdate(DocumentEvent e){
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

        cercaBagagliCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testo = cercaPrenotazione.getText();
                if(cercaBagagliCheckBox.isSelected()){
                    aggiornaPrenotazioni(controller.ricercaPrenotazioni(testo, true));
                }else{
                    aggiornaPrenotazioni(controller.ricercaPrenotazioni(testo, false));
                }

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
                prenotazione.setLayout(new GridLayout(0,6, 10, 10));
                prenotazione.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // altezza fissa

                prenotazione.add(new JLabel("ID: " + p.getIdPrenotazione().toUpperCase()));
                prenotazione.add(new JLabel("NOME: " + p.getNome().toUpperCase()));
                prenotazione.add(new JLabel("COGNOME: " + p.getCognome().toUpperCase()));
                prenotazione.add(new JLabel("CARTA D'IDENTITA: " + p.getCartaIdentita().toUpperCase()));
                prenotazione.add(new JLabel("POSTO ASSEGNATO: " + p.getPostoAssegnato()));
                prenotazione.add(new JLabel("STATO: " + p.getStatoPrenotazione()));


                if(!p.getStatoPrenotazione().equals(StatoPrenotazione.CANCELLATA)){
                    prenotazione.setLayout(new GridLayout(0,8, 10, 10));
                    JButton modificaPrenotazione = new JButton("MODIFICA");
                    JButton cancellaPrenotazione = new JButton("CANCELLA");
                    prenotazione.add(modificaPrenotazione);
                    prenotazione.add(cancellaPrenotazione);


                    modificaPrenotazione.addActionListener(e -> {
                        new ModificaPrenotazione(this.controller, frame, p, gui.AreaPersonale.this).frame.setVisible(true);
                    });

                    cancellaPrenotazione.addActionListener(e -> {
                        String res = controller.cancellaPrenotazione(p.getIdPrenotazione());
                        JOptionPane.showMessageDialog(null, res, "Avviso", JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(() ->
                                aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni())
                        );
                    });
                }

                if(cercaBagagliCheckBox.isSelected()){
                    ArrayList<Bagaglio> g = p.getBagaglio();
                    if(g != null){
                        JPanel pannelloListaBagagli = new JPanel();
                        pannelloListaBagagli.setLayout(new BoxLayout(pannelloListaBagagli, BoxLayout.Y_AXIS));
                        pannelloListaBagagli.add(new JLabel("BAGAGLI"));
                        for(int i = 0; i < g.size(); i++) {
                            JPanel singoloBagaglio = new JPanel();
                            singoloBagaglio.setLayout( new GridLayout(2, 1));
                            singoloBagaglio.add(new JLabel("BAGAGLIO " + i + ": " + g.get(i).getCodice()));
                            singoloBagaglio.add(new JLabel("PESO: " + g.get(i).getPeso()));
                            singoloBagaglio.setBorder(BorderFactory.createLineBorder(Color.black));
                            pannelloListaBagagli.add(singoloBagaglio);
                        }
                        prenotazione.add(pannelloListaBagagli);
                    }
                }


                listaPrenotazioni.add(prenotazione);
                listaPrenotazioni.add(Box.createVerticalStrut(5));

            }
        }

        listaPrenotazioni.revalidate();
        listaPrenotazioni.repaint();
    }
}
