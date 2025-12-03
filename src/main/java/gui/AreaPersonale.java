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
 * Classe che rappresenta l'AreaPersonale dell'utente
 * Permette di visualizzare e modificare una prenotazione
 */
public class AreaPersonale {
    private JPanel AreaPersonale;
    private JTextField email;
    private JTextField cercaPrenotazione;
    private JScrollPane listaPrenotazioniScroll;
    private JPanel listaPrenotazioni;
    private JButton indietroButton;

    Controller controller;
    /**
     * Il Frame della finestra AreaPersonale.java
     */
    JFrame frame;


    /**
     * Costruisce la finestra AreaPersonale,
     * inserisce le prenotazioni
     * e inizializza i metodi che aggiungono gli ActionListener ai bottoni
     *
     * @param framePadre Il frame padre (Utente.java)
     * @param controller Il controller che effettua collegamenti con DB/Model
     */
    public AreaPersonale(Controller controller, JFrame framePadre){
        this.controller = controller;


        frame = new JFrame("Area Personale");
        frame.setContentPane(AreaPersonale);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        email.setText(this.controller.getEmail());
        email.setEditable(false);

        listaPrenotazioni.setLayout(new BoxLayout(listaPrenotazioni, BoxLayout.Y_AXIS));


        aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni());
        initListeners(framePadre);



        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }


    /**
     * Inizializza gli actionListeners
     * Gestisce la ricerca delle prenotazioni chiamando il controller
     * @param framePadre Il frame padre
     */
    public void initListeners(JFrame framePadre) {

        cercaPrenotazione.getDocument().addDocumentListener(new DocumentListener() {
            private void aggiornaP() {
                SwingUtilities.invokeLater(() -> {
                    String testo = cercaPrenotazione.getText();
                    aggiornaPrenotazioni(controller.ricercaPrenotazioni(testo));

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
                // Metodo non necessario per questo listener, ma obbligatorio per l'interfaccia
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
     * Crea i JPanel per ogni prenotazione e li popola con i dati.
     * Per ogni prenotazione crea una riga con i dettagli. Se la prenotazione è attiva,
     * mostra i pulsanti per modificare o cancellare. Se è cancellata, mostra spazi vuoti
     * per mantenere l'allineamento della griglia.
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
                // prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // altezza fissa

                prenotazione.add(new JLabel("ID: " + p.getIdPrenotazione().toUpperCase()));
                prenotazione.add(new JLabel("NOME: " + p.getNome().toUpperCase()));
                prenotazione.add(new JLabel("COGNOME: " + p.getCognome().toUpperCase()));
                prenotazione.add(new JLabel("CARTA D'IDENTITA: " + p.getCartaIdentita().toUpperCase()));
                prenotazione.add(new JLabel("POSTO ASSEGNATO: " + p.getPostoAssegnato()));
                prenotazione.add(new JLabel("STATO: " + p.getStatoPrenotazione()));

                ArrayList<Bagaglio> bagagli = p.getBagaglio();
                if(bagagli != null){
                    JPanel bagagliPanel = new JPanel();
                    bagagliPanel.setLayout(new BoxLayout(bagagliPanel, BoxLayout.Y_AXIS));

                    JPanel pesoBagagli = new JPanel();
                    float pesoTotale = 0;

                    for(Bagaglio b : bagagli){
                        bagagliPanel.add(new JLabel("CODICE BAGAGLIO: " + b.getCodice()));
                        pesoTotale = b.getPeso();
                    }
                    pesoBagagli.add(new JLabel("PESO TOTALE BAGAGLI: " + pesoTotale));
                    prenotazione.add(bagagliPanel);
                    prenotazione.add(pesoBagagli);
                }


                if(!p.getStatoPrenotazione().equals(StatoPrenotazione.CANCELLATA)){

                    JButton modificaPrenotazione = new JButton("MODIFICA");
                    JButton cancellaPrenotazione = new JButton("CANCELLA");
                    prenotazione.add(modificaPrenotazione);
                    prenotazione.add(cancellaPrenotazione);


                    modificaPrenotazione.addActionListener(e ->
                        new ModificaPrenotazione(this.controller, frame, p, gui.AreaPersonale.this).frame.setVisible(true)
                    );

                    cancellaPrenotazione.addActionListener(e -> {
                        String res = controller.cancellaPrenotazione(p.getIdPrenotazione());
                        JOptionPane.showMessageDialog(null, res, "Avviso", JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(() ->
                                aggiornaPrenotazioni(this.controller.getTutteLePrenotazioni())
                        );
                    });
                }


                prenotazione.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                        prenotazione.getPreferredSize().height));
                listaPrenotazioni.add(prenotazione);
                listaPrenotazioni.add(Box.createVerticalStrut(5));

            }

            listaPrenotazioni.add(Box.createVerticalGlue());
        }

        listaPrenotazioni.revalidate();
        listaPrenotazioni.repaint();
    }
}
