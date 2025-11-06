package gui;
import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Amministratore {
    private JButton inserisciUnNuovoVoloButton;
    private JPanel AmministratorePanel;
    public JFrame frame;
    private JTextField ricercaVoloButton;
    private JButton logoutButton;
    private JButton aggiornaUnVoloButton;

    private Controller controller;

    //TESTING//
    public Amministratore(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        frame = new JFrame("Pannello Amministratore TEST");
        frame.setContentPane(AmministratorePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();



        inserisciUnNuovoVoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InserisciVolo(frame,controller);
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


        /*
        aggiornaUnVoloButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new AggiornaVolo(frame);
               frame.dispose();
           }
        });

         */
    }

}

    //private Controller controller;
/*
    public Amministratore(Controller controller) {
        this.controller = controller;
        frame = new JFrame("Pannello Amministratore");
        frame.setContentPane(panelRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



        inserisciUnNuovoVoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InserisciVolo nuovoVolo = new InserisciVolo(controller, frame);
                nuovoVolo.frame.setVisible(true);
                frame.setVisible(false);
            }
        });

        creaPannelli(); //questo metodo non è utilizzabile qui.
        // i metodi non dovrebbero mai essere chiamati nel costruttore.
    }

    private void creaPannelli(){
        ArrayList<Volo> listaVoli = controller.getVoli();

        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(2,2));

            pannelloVolo.add(new JLabel("Codice volo: "));
            pannelloVolo.add(new JTextField(volo.getCodiceVolo()));

            pannelloVolo.add(new JLabel("Compagnia aerea: "));
            pannelloVolo.add(new JTextField(volo.getCompagniaAerea()));

            pannelloVolo.add(new JLabel("Origine: "));
            pannelloVolo.add(new JTextField(volo.getOrigine()));

            pannelloVolo.add(new JLabel("Destinazione: "));
            pannelloVolo.add(new JTextField(volo.getDestinazione()));

            pannelloVolo.add(new JLabel("Data: "));
            pannelloVolo.add(new JTextField(volo.getData()));

            pannelloVolo.add(new JLabel("Orario di arrivo previsto: "));
            pannelloVolo.add(new JTextField(volo.getOrarioPrevisto()));

            pannelloVolo.add(new JLabel("Ritardo: "));
            pannelloVolo.add(new JTextField(volo.getRitardo()));

            JButton aggiorna = new JButton("Aggiorna");

            aggiorna.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AggiornaVolo nuovoVolo = new AggiornaVolo(controller, frame, volo);
                    nuovoVolo.frame.setVisible(true);
                    frame.setVisible(false);
                }
            });

            pannelloVolo.add(aggiorna);
            panelRoot.add(pannelloVolo);
        }

        panelRoot.revalidate();
        panelRoot.repaint();
    }

    public JPanel getPanel() {
        return panelRoot;
    }
}
*/