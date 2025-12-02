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
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dettagliVoloPanel.setLayout(new BoxLayout(dettagliVoloPanel, BoxLayout.Y_AXIS));

        mostraInfo(volo);


        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Mostra a schermo  informazioni su
     * prenotazioni e Bagagli per il volo corrente.
     * Itera su tutte le prenotazioni esistenti, crea un JPanel per ognuna
     * e inserisce i vari dati su Passeggeri e bagagli
     *
     *
     * @param volo l'oggetto volo che riferisce alle prenotazioni ad esso associate
     */
    private void mostraInfo(Volo volo) {
        dettagliVoloPanel.removeAll();

        ArrayList<Prenotazione> prenotazioni = controller.getPrenotazioniByIdVolo(volo.getCodiceVolo());
        if(prenotazioni != null){
            if(prenotazioni.isEmpty()){
                mostraPanelVuoto();
                return;
            }

            for(Prenotazione p : prenotazioni){
                JPanel prenotazioniPanel = new JPanel();
                prenotazioniPanel.setLayout(new BoxLayout(prenotazioniPanel, BoxLayout.Y_AXIS));
                prenotazioniPanel.add(new JLabel("UTENTE:"));
                prenotazioniPanel.add(new JLabel("Nome: " + p.getNome()));
                prenotazioniPanel.add(new JLabel("Cognome: " + p.getCognome()));
                prenotazioniPanel.add(new JLabel("Carta d'identità: " + p.getCartaIdentita()));

                mostraBagagli(prenotazioniPanel, p);

                prenotazioniPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                dettagliVoloPanel.add(Box.createVerticalStrut(5));

                dettagliVoloPanel.add(prenotazioniPanel);

            }
        }


        dettagliVoloPanel.revalidate();
        dettagliVoloPanel.repaint();

    }


    /**
     * Inserisce una stringa su un JPanel.
     * Questa indica che il volo non possiede prenotazioni effettuate.
     */
    public void mostraPanelVuoto() {
        JPanel prenotazioniVuote = new JPanel();
        prenotazioniVuote.setLayout(new BoxLayout(prenotazioniVuote, BoxLayout.Y_AXIS));
        prenotazioniVuote.add(new JLabel("Non ci sono prenotazioni per questo volo"));
        dettagliVoloPanel.add(prenotazioniVuote);
    }

    /**
     * Mostra i bagagli associati alla prenotazione passata come parametro del metodo
     * Il metodo verifica che esista un oggetto bagaglio associato alla prenotazione,
     * dopodichè itera sugli oggetti Bagaglio trovati e salva i suoi dati nel JPanel
     *
     * @param prenotazioniPanel Il panel che mostrerà i dati dei bagagli
     * @param p La prenotazione dalla quale ottenere i dati sui bagagli
     */
    public void mostraBagagli(JPanel prenotazioniPanel, Prenotazione p){
        ArrayList<Bagaglio> bagagli;
        if(p.getBagaglio() == null){
            return;
        }
        bagagli = p.getBagaglio();
        float pesoTotale = 0;
        if(!bagagli.isEmpty() && bagagli.get(0) != null) pesoTotale = bagagli.get(0).getPeso();

        prenotazioniPanel.add(new JLabel("BAGAGLI: "));

        if(bagagli.isEmpty()){
            prenotazioniPanel.add(new JLabel("Non ci sono bagagli"));
        }else{
            for(Bagaglio b : bagagli){
                prenotazioniPanel.add(new JLabel("Codice Bagaglio: " + b.getCodice()));
            }
            prenotazioniPanel.add(new JLabel("Peso totale dei bagagli: " + pesoTotale));
        }
    }

}
