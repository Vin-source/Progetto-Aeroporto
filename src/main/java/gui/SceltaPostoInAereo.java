package gui;
// import controller.Controller;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SceltaPostoInAereo {
    private String postoScelto = "";
    private JButton a1;
    private JButton b1;
    private JButton c1;
    private JButton d1;
    private JButton e1;
    private JButton a2;
    private JButton b2;
    private JButton c2;
    private JButton d2;
    private JButton e2;
    private JButton a3;
    private JButton b3;
    private JButton c3;
    private JButton d3;
    private JButton e3;
    private JButton a4;
    private JButton b4;
    private JButton c4;
    private JButton d4;
    private JButton e4;
    private JButton a5;
    private JButton b5;
    private JButton c5;
    private JButton d5;
    private JButton e5;
    private JButton a6;
    private JButton b6;
    private JButton c6;
    private JButton d6;
    private JButton e6;

    private JButton[] buttons = {a1, a2, a3, a4, a5, a6,
            b1, b2, b3, b4, b5, b6,
            c1, c2, c3, c4, c5, c6,
            d1, d2, d3, d4, d5, d6,
            e1, e2, e3, e4, e5, e6};
    private JPanel scegliPostoPanel;
    private JButton confermaButton;
    public JFrame frame;
    private Controller controller;

    public SceltaPostoInAereo(Controller controller, JFrame frameChiamante, EffettuaNuovaPrenotazione metodoSelezionePosto, String codiceVolo) {

        frame = new JFrame("Scelta Posto in Aereo");
        frame.setContentPane(scegliPostoPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        this.controller = controller;



        //Chiamata al Controller per ottenere i posti occupati
        ArrayList<String> postiOccupati = controller.getPostiOccupati(codiceVolo);


        initListeners(metodoSelezionePosto, frameChiamante);

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



    //aggiunto getPanel
    public JPanel getPanel(){
        return scegliPostoPanel;
    }
}
