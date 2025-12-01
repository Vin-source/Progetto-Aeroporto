package gui;

import controller.Controller;
import model.Bagaglio;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe che permette di mostrare tutte le prenotazioni e i bagagli associati ad un volo
 */
public class DettagliVolo {
    private JPanel dettagliVoloPanel;
    private JScrollPane dettagliVoloScrollPane;
    private JPanel dettagliVoloContainer;
    private Controller controller;

    /**
     * Costruttore di DettagliVolo.java
     *
     * @param framePadre Il frame padre (Amministratore.java)
     * @param controller Il controller che recupera i dati dal DB
     * @param volo       Il volo dal quale ottenere le prenotazioni associate
     */
    public DettagliVolo(JFrame framePadre, Controller controller, Volo volo) {
        this.controller = controller;
        JFrame frame = new JFrame("Dettagli Volo");
        frame.setContentPane(dettagliVoloContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dettagliVoloPanel.setLayout(new BoxLayout(dettagliVoloPanel, BoxLayout.Y_AXIS));

        mostraInfo(volo);


        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Mostra a schermo le prenotazioni e i Bagagli per il volo in considerazione
     *
     * @param volo l'oggetto volo che riferisce alle prenotazioni ad esso associate
     */
    private void mostraInfo(Volo volo) {
        dettagliVoloPanel.removeAll();

        ArrayList<Prenotazione> prenotazioni = controller.getPrenotazioniByIdVolo(volo.getCodiceVolo());
        if(prenotazioni != null){
            if(prenotazioni.isEmpty()){
                JPanel prenotazioniVuote = new JPanel();
                prenotazioniVuote.setLayout(new BoxLayout(prenotazioniVuote, BoxLayout.Y_AXIS));
                prenotazioniVuote.add(new JLabel("Non ci sono prenotazioni per questo volo"));
                dettagliVoloPanel.add(prenotazioniVuote);
                return;
            }

            for(Prenotazione p : prenotazioni){
                JPanel prenotazioniPanel = new JPanel();
                prenotazioniPanel.setLayout(new BoxLayout(prenotazioniPanel, BoxLayout.Y_AXIS));
                prenotazioniPanel.add(new JLabel("UTENTE:"));
                prenotazioniPanel.add(new JLabel("Nome: " + p.getNome()));
                prenotazioniPanel.add(new JLabel("Cognome: " + p.getCognome()));
                prenotazioniPanel.add(new JLabel("Carta d'identità: " + p.getCartaIdentita()));

                ArrayList<Bagaglio> bagagli;
                bagagli = p.getBagaglio();
                float pesoTotale = 0;
                if(bagagli != null && !bagagli.isEmpty() && bagagli.get(0) != null) pesoTotale = bagagli.get(0).getPeso();

                prenotazioniPanel.add(new JLabel("BAGAGLI: "));

                if(bagagli.isEmpty()){
                    prenotazioniPanel.add(new JLabel("Non ci sono bagagli"));
                }else if(bagagli != null){
                    for(Bagaglio b : bagagli){
                        prenotazioniPanel.add(new JLabel("Codice Bagaglio: " + b.getCodice()));
                    }
                    prenotazioniPanel.add(new JLabel("Peso totale dei bagagli: " + pesoTotale));
                }

                prenotazioniPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                dettagliVoloPanel.add(Box.createVerticalStrut(5));

                dettagliVoloPanel.add(prenotazioniPanel);

            }
        }


        dettagliVoloPanel.revalidate();
        dettagliVoloPanel.repaint();

    }

}
