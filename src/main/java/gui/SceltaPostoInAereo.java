package gui;
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
    private JButton a1, b1, c1, d1, e1;
    private JButton a2, b2, c2, d2, e2;
    private JButton a3, b3, c3, d3, e3;
    private JButton a4, b4, c4, d4, e4;
    private JButton a5, b5, c5, d5, e5;
    private JButton a6, b6, c6, d6, e6;


    private JButton[] buttons = {a1, a2, a3, a4, a5, a6,
            b1, b2, b3, b4, b5, b6,
            c1, c2, c3, c4, c5, c6,
            d1, d2, d3, d4, d5, d6,
            e1, e2, e3, e4, e5, e6};
    private JPanel scegliPostoPanel;
    private JButton confermaButton;
    /**
     * Il frame della finestra SceltaPostoInAereo
     */
    public JFrame frame;
    private Controller controller;

    /**
     * Costruisce la finestra per la scelta di un posto in aereo
     *
     * @param controller           Il controller che effettua chiamate al model/db
     * @param frameChiamante       Il frame padre (EffettuaNuovaPrenotazione.java)
     * @param metodoSelezionePosto Il metodo selezionePosto che applicherà i cambiamenti nel frame padre
     * @param codiceVolo           Il codice del volo selezionato in fase di prenotazione
     */
    public SceltaPostoInAereo(Controller controller,
                              JFrame frameChiamante,
                              EffettuaNuovaPrenotazione metodoSelezionePosto,
                              String codiceVolo,
                              ModificaPrenotazione metodoModificaPosto,
                              String postoAssegnato) {


        frame = new JFrame("Scelta Posto in Aereo");
        frame.setContentPane(scegliPostoPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;



        //Chiamata al Controller per ottenere i posti occupati
        ArrayList<String> postiOccupati = controller.getPostiOccupati(codiceVolo);

        if(metodoSelezionePosto != null){
            initListeners(metodoSelezionePosto,null, frameChiamante);
            initColors(postiOccupati, null);
        }else{
            initListeners(null, metodoModificaPosto, frameChiamante);
            initColors(postiOccupati, postoAssegnato);
        }



    }


    /**
     * Metodo che contiene gli ActionListener per i componenti della gui
     * Gestisce la logica di selezione del posto
     *
     * @param metodoSelezionePosto Il metodo che applica il posto selezionato durante la creazione della prenotazione
     * @param metodoModificaPosto  Il metodo che viene utilizzato quando viene modificata una prenotazione
     * @param frameChiamante       Il frame padre
     */
    public void initListeners(EffettuaNuovaPrenotazione metodoSelezionePosto, ModificaPrenotazione metodoModificaPosto, JFrame frameChiamante){
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(metodoSelezionePosto != null)  metodoSelezionePosto.setPostoScelto(postoScelto); //aggiunta la chiamata al controller indicata sopra
                    else metodoModificaPosto.setPostoScelto(postoScelto);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Posto non valido: " + ex.getMessage(), "Errore di input", JOptionPane.WARNING_MESSAGE);
                }
            }
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
                        if(postoScelto.equals(bttn.getText())){
                            postoScelto = "";
                            return;
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
     * @return il JPanel scegliPostoPanel
     */
//aggiunto getPanel
    public JPanel getPanel(){
        return scegliPostoPanel;
    }


    /**
     * Metodo che inizializza i colori all'interno della gui SceltaPostoInAereo
     *
     * @param postiOccupati  L'arrayList contenente i posti già occupati nel volo
     * @param postoAssegnato Il posto precedentemente scelto dall'utente alla creazione della prenotazione
     */
    public void initColors(ArrayList<String> postiOccupati, String postoAssegnato) {
        for (JButton button : buttons) {
            String nomePosto = button.getText().toUpperCase();

            if (postiOccupati.contains(nomePosto)) {
                button.setBackground(Color.RED);
                if(postoAssegnato != null && nomePosto.equals(postoAssegnato)){
                    button.setBackground(Color.GRAY);
                }

                if(postiOccupati.contains(nomePosto) && button.getBackground().equals(Color.RED)){
                    for (ActionListener al : button.getActionListeners()) {
                        button.removeActionListener(al);
                    }
                }
            } else {
                button.setBackground(Color.GRAY);
            }
        }
    }
}
