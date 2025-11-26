package gui;
import controller.Controller;

import model.Gate;
import model.Volo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * La classe Amministratore della gui
 */
public class Amministratore {
    /**
     * The Frame.
     */
    public JFrame frame;
    private JPanel AmministratorePanel;
    private JButton inserisciUnNuovoVoloButton;
    private JButton logoutButton;
    private JTextField ricercaVoli;
    private JPanel listaVoliPanel;

    private Controller controller;


    /**
     * Costruisce la finestra che si apre
     * quando l'ospite si logga come amministratore
     * @param frameChiamante Il frame di Ospite.java
     * @param controller Il controller che effettua chiamate model/db
     */
    public Amministratore(Controller controller, JFrame frameChiamante) {

        this.controller = controller;
        frame = new JFrame("Pannello Amministratore TEST");
        frame.setContentPane(AmministratorePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listaVoliPanel.setLayout(new BoxLayout(listaVoliPanel, BoxLayout.Y_AXIS));


        initListeners(frameChiamante);
        //aggiornaListaVoli(voli);
        frame.pack();
       // frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                creaPannelli();
            }
        });


        frame.setVisible(true);
    }

    /**
     * Metodo che contiene gli ActionListener
     *
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
     * Metodo che permette di aggiornare la lista dei voli
     *
     */
    private void creaPannelli() {
        ArrayList<Volo> listaVoli = controller.getTuttiVoli();
        aggiornaListaVoli(listaVoli);
    }


    /**
     * Metodo che permette di aggiornare la lista dei voli
     * @param listaVoli ArrayList contenente la lista dei voli
     */
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {

        listaVoliPanel.removeAll();

        if(listaVoli == null){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,9, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // altezza fissa

            pannelloVolo.add(new JLabel("Non ci sono voli attualmente disponibili. Ci scusiamo per il disagio!"));

            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));

        }else{
            for(Volo volo: listaVoli){
                JPanel pannelloVolo = new JPanel();
                pannelloVolo.setLayout(new GridLayout(1,9, 10, 10));
                pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
                pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
                if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
                if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
                pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
                pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
                pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
                pannelloVolo.add(new JLabel("STATO: " + volo.getStatoVolo()));

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

                        String esito = controller.eliminaVolo(volo.getCodiceVolo());

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


                listaVoliPanel.add(pannelloVolo);
                listaVoliPanel.add(Box.createVerticalStrut(5));

            }
        }




        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
    }
}

