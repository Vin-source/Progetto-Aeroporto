package gui;
//import controller.*;

import model.Gate;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Amministratore {
    public JFrame frame;
    private JPanel AmministratorePanel;
    private JButton inserisciUnNuovoVoloButton;
    private JButton logoutButton;
    private JTextField ricercaVoli;
    private JPanel listaVoliPanel;

    //TESTING//
    public Amministratore(JFrame frameChiamante) {
        frame = new JFrame("Pannello Amministratore TEST");
        frame.setContentPane(AmministratorePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        listaVoliPanel.setLayout(new BoxLayout(listaVoliPanel, BoxLayout.Y_AXIS));

        ArrayList<Volo> voli = new ArrayList<>();
        Volo v = new Volo("a", "a", "f", "f", "23/02/1999", "23:32", 0);
        v.setGate(new Gate(23));
        voli.add(v);
        voli.add(v);

        initListeners(frameChiamante, voli);
        aggiornaListaVoli(voli);
    }

    public void initListeners(JFrame frameChiamante, ArrayList<Volo> voli) {

        inserisciUnNuovoVoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InserisciVolo(frame);
                frame.setVisible(false);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        ricercaVoli.getDocument().addDocumentListener(new DocumentListener() {
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
            JButton modifica = new JButton("MODIFICA");
            pannelloVolo.add(modifica);

            modifica.addActionListener(e -> {
                new ModificaVolo(frame, volo).frame.setVisible(true);
                frame.dispose();
            });


            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));

        }

        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
        frame.pack();
    }
}

    //private Controller controller;
