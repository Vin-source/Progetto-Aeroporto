package gui;
// import controller.Controller;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * La gui SceltaPostoInAereo
 * Inserito in una nuova pagina per rendere comoda
 * la scelta del posto nell'aereo selezionato in fase di prenotazione
 */
public class SceltaPostoInAereo {
    private String postoScelto = "";
    private JButton a1, b1, c1, d1, e1, a2, b2, c2, d2, e2, a3, b3, c3, d3, e3;
    private JButton a4, b4, c4, d4, e4, a5, b5, c5, d5, e5,a6, b6, c6, d6, e6;


    private JButton[] buttons = {a1, a2, a3, a4, a5, a6,
            b1, b2, b3, b4, b5, b6,
            c1, c2, c3, c4, c5, c6,
            d1, d2, d3, d4, d5, d6,
            e1, e2, e3, e4, e5, e6};
    private JPanel scegliPostoPanel;
    private JButton confermaButton;
    /**
     * Il frame della pagina SceltaPostoInAereo.java
     */
    public JFrame frame;
    private Controller controller;

    /**
     * Costruttore della pagina
     *
     * @param controller           Il controller che effettua chiamate al model/db
     * @param frameChiamante       Il frame padre (EffettuaNuovaPrenotazione.java)
     * @param metodoSelezionePosto Il metodo selezionePosto che applicherà i cambiamenti nel frame padre
     * @param codiceVolo           Il codice del volo selezionato in fase di prenotazione
     *
     */
    public SceltaPostoInAereo(Controller controller, JFrame frameChiamante, EffettuaNuovaPrenotazione metodoSelezionePosto, String codiceVolo) {


        frame = new JFrame("Scelta Posto in Aereo");
        frame.setContentPane(scegliPostoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;



        //Chiamata al Controller per ottenere i posti occupati
        ArrayList<String> postiOccupati = controller.getPostiOccupati(codiceVolo);


        initListeners(metodoSelezionePosto, frameChiamante);
        initColors(postiOccupati);


    }

    public SceltaPostoInAereo(Controller controller, JFrame frameChiamante, ModificaPrenotazione metodoSelezionePosto, String codiceVolo, String postoAssegnato) {


        frame = new JFrame("Scelta Posto in Aereo");
        frame.setContentPane(scegliPostoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;



        //Chiamata al Controller per ottenere i posti occupati
        ArrayList<String> postiOccupati = controller.getPostiOccupati(codiceVolo);


        initListeners(metodoSelezionePosto, frameChiamante);
        initColors(postiOccupati, postoAssegnato);


    }


    /**
     * Inizializza gli actionListeners
     *
     * @param metodoSelezionePosto Il metodo che applica il posto selezionato
     * @param frameChiamante       Il frame padre
     */
    public void initListeners(EffettuaNuovaPrenotazione metodoSelezionePosto, JFrame frameChiamante){
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    metodoSelezionePosto.setPostoScelto(postoScelto); //aggiunta la chiamata al controller indicata sopra
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Posto non valido: " + ex.getMessage(), "Errore di input", JOptionPane.WARNING_MESSAGE);
                }
            };
        });


        for(JButton bttn : buttons){
            bttn.setBackground(Color.GRAY);

            bttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bttn.setBackground(Color.GREEN);
                    if(!postoScelto.isEmpty()){
                        for(JButton posto: buttons){
                            if(posto.getText().equals(postoScelto)){
                                posto.setBackground(Color.GRAY);
                            }
                        }
                    }
                    postoScelto = bttn.getText();
                }
            });
        }
    }


    public void initListeners(ModificaPrenotazione metodoSelezionePosto, JFrame frameChiamante){
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    metodoSelezionePosto.setPostoScelto(postoScelto); //aggiunta la chiamata al controller indicata sopra
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Posto non valido: " + ex.getMessage(), "Errore di input", JOptionPane.WARNING_MESSAGE);
                }
            };
        });


        for(JButton bttn : buttons){
            bttn.setBackground(Color.GRAY);

            bttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bttn.setBackground(Color.GREEN);
                    if(!postoScelto.isEmpty()){
                        for(JButton posto: buttons){
                            if(posto.getText().equals(postoScelto)){
                                posto.setBackground(Color.GRAY);
                            }
                        }
                    }
                    postoScelto = bttn.getText();
                }
            });
        }
    }


    /**
     * Ritorna il panel dell'attuale pagina
     *
     * @return scegliPostoPanel
     */
//aggiunto getPanel
    public JPanel getPanel(){
        return scegliPostoPanel;
    }

    public void initColors(ArrayList<String> postiOccupati) {
        for (JButton button : buttons) {
            String nomePosto = button.getText().toUpperCase();

            if (postiOccupati.contains(nomePosto)) {
                button.setBackground(Color.RED);
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
            } else {
                button.setBackground(Color.GRAY);
            }
        }
    }

    public void initColors(ArrayList<String> postiOccupati, String postoAssegnato) {
        for (JButton button : buttons) {
            String nomePosto = button.getText().toUpperCase();

            if (postiOccupati.contains(nomePosto) && !nomePosto.equals(postoAssegnato)) {
                button.setBackground(Color.RED);
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
            } else {
                button.setBackground(Color.GRAY);
            }
        }
    }
}
