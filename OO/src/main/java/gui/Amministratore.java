package gui;
import controller.Controller;

import model.StatoVolo;
import model.Volo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Classe che rappresenta la schermata principale dell'Amministratore
 * Permette di visualizzare, inserire, modificare ed eliminare i voli
 */
public class Amministratore {
    /**
     * Il Frame della finestra Amministratore
     */
    public JFrame frame;
    private JPanel AmministratorePanel;
    private JButton inserisciUnNuovoVoloButton;
    private JButton logoutButton;
    private JTextField ricercaVoli;
    private JPanel listaVoliPanel;
    private JScrollPane listaVoliScroll;

    private Controller controller;


    /**
     * Costruisce la finestra che si apre
     * quando l'ospite si logga come amministratore.
     * @param frameChiamante Il frame di Ospite.java (per il logout)
     * @param controller Il controller che effettua chiamate model/db
     */
    public Amministratore(Controller controller, JFrame frameChiamante) {

        this.controller = controller;
        frame = new JFrame("Pannello Amministratore");
        frame.setContentPane(AmministratorePanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        listaVoliPanel.setLayout(new GridLayout(0, 4, 5, 5));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(listaVoliPanel, BorderLayout.NORTH);
        listaVoliScroll.setViewportView(wrapperPanel);

        initListeners(frameChiamante);
        frame.pack();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                creaPannelli();
            }
        });


        frame.setVisible(true);
    }

    /**
     * Metodo che contiene gli ActionListener per i componenti della gui
     * Gestisce la ricerca dei voli e i pulsanti della schermata
     * @param frameChiamante Il frame chiamante di Ospite.java
     */
    public void initListeners(JFrame frameChiamante) {

        inserisciUnNuovoVoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InserisciVolo(frame, controller);
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
                aggiornaListaVoli(controller.cercaVoliAmministratore(ricercaVoli.getText()));
            }
            public void removeUpdate(DocumentEvent e){
                aggiornaListaVoli(controller.cercaVoliAmministratore(ricercaVoli.getText()));
            }
            public void changedUpdate(DocumentEvent e){
                // ignorato per campi plain text
            }
        });
    }


    /**
     * Permette di creare la lista dei voli
     * Recupera l'elenco aggiornato dei voli dal Controller
     */
    private void creaPannelli() {
        ArrayList<Volo> listaVoli = controller.getTuttiVoli();
        aggiornaListaVoli(listaVoli);
    }


    /**
     * Aggiorna la lista dei voli
     * Crea per ogni volo della lista una riga in cui ci sono i dettagli
     *
     * @param listaVoli ArrayList contenente la lista dei voli
     */
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {

        listaVoliPanel.removeAll();

        if(listaVoli.isEmpty()){
            pannelloVuoto();
        }else{
            for(Volo volo: listaVoli){
                JPanel pannelloVolo = new JPanel();
                pannelloVolo.setPreferredSize(new Dimension(300, 300));
                pannelloVolo.setLayout(new GridLayout(0, 1));

                Border lineaNera = BorderFactory.createLineBorder(Color.BLACK, 3);
                Border paddingInterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
                pannelloVolo.setBorder(BorderFactory.createCompoundBorder(lineaNera, paddingInterno));

                popolaDati(pannelloVolo, volo);

                if(volo.getStatoVolo().equals(StatoVolo.CANCELLATO) || volo.getStatoVolo().equals(StatoVolo.IN_RITARDO)){
                    Border lineaRossa = BorderFactory.createLineBorder(Color.RED, 3);
                    pannelloVolo.setBorder(BorderFactory.createCompoundBorder(lineaRossa, paddingInterno));
                }

                if(!volo.getStatoVolo().equals(StatoVolo.CANCELLATO)){
                    aggiungiBottoni(pannelloVolo, volo);
                }

                JButton dettagliVolo = new JButton("DETTAGLI VOLO");
                pannelloVolo.add(dettagliVolo);

                dettagliVolo.addActionListener(e ->
                    new DettagliVolo(frame, controller, volo));



                listaVoliPanel.add(pannelloVolo);

            }
        }




        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
    }


    /**
     * Mostra una stringa in un JPanel per indicare che non ci sono voli disponibili.
     */
    public void pannelloVuoto(){
        JPanel pannelloVolo = new JPanel();
        pannelloVolo.setLayout(new GridLayout(0,4, 5, 5));

        Border lineaNera = BorderFactory.createLineBorder(Color.BLACK, 3);
        Border paddingInterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        pannelloVolo.setBorder(BorderFactory.createCompoundBorder(lineaNera, paddingInterno));

        pannelloVolo.add(new JLabel("Non ci sono voli attualmente disponibili. Ci scusiamo per il disagio!"));

        listaVoliPanel.add(pannelloVolo);
    }


    /**
     * Aggiunge bottoni per eliminare e modificare un volo e
     * inizializza gli actionListener
     *
     * @param pannelloVolo il pannello dove inserire i bottoni.
     * @param volo oggetto volo considerato.
     */
    public void aggiungiBottoni(JPanel pannelloVolo, Volo volo){
        JButton modifica = new JButton("MODIFICA");
        pannelloVolo.add(modifica);
        JButton elimina = new JButton("ELIMINA");

        elimina.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(
                    frame,
                    "Sei sicuro di voler eliminare il volo " + volo.getCodiceVolo() + "?",
                    "Conferma Eliminazione",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (scelta == JOptionPane.YES_OPTION) {

                String esito = controller.eliminaVolo(volo.getCodiceVolo(), volo.getGate());

                if (esito.equals("Volo cancellato correttamente!")) {
                    JOptionPane.showMessageDialog(frame, esito);
                    creaPannelli();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            esito,
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pannelloVolo.add(elimina);


        modifica.addActionListener(e -> {
            new ModificaVolo(frame,controller,volo);
            frame.setVisible(false);
        });
    }

    /**
     * Popola il JPanel con i dati sul volo inserito come parametro del metodo.
     *
     * @param pannelloVolo il pannello dove inserire i dati.
     * @param volo l'oggetto volo dal quale prendere i dati.
     */
    public void popolaDati(JPanel pannelloVolo, Volo volo){
        pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
        pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
        if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
        if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
        pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
        if(volo.getOrigine().equalsIgnoreCase("Napoli")){
            pannelloVolo.add(new JLabel("PARTE ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
        }else{
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
        }
        pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
        pannelloVolo.add(new JLabel("STATO: " + volo.getStatoVolo()));
        if(volo.getGate() != null) pannelloVolo.add(new JLabel("GATE: " + volo.getGate().getNumero()));
        else pannelloVolo.add(new JLabel("GATE: NON ASSEGNATO"));
    }


}

