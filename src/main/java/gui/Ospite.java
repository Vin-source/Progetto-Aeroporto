package gui;

// import controller.Controller;
import controller.Controller;
import model.Utente;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Gui Ospite
 */
public class Ospite extends JFrame {
    private JPanel ospiteContainer;
    /**
     * Frame di Ospite
     */
    public JFrame frame;
    private JTextField email;
    private JPasswordField password;
    private JButton accediButton;


    private JPanel voliContainer;


    // voli
    private JScrollPane listaVoliScroll;
    private JPanel listaVoliPanel;




    private Controller controller;


    /**
     * Costruttore della classe Ospite
     * <p>
     *
     * Il costruttore inizializza gli elementi, i listener per i bottoni
     * e chiama il metodo aggiornaListaVoli per mostrare i voli disponibili
     *
     * @param controller Il controller che verrà passato dal Main method
     */

    public Ospite(Controller controller) {



        frame = new JFrame("La mia GUI Swing");
        frame.setContentPane(ospiteContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        this.controller = controller;



        listaVoliPanel = new JPanel();
        listaVoliPanel.setLayout(new GridLayout(20, 1));
        listaVoliScroll.setViewportView(listaVoliPanel);


        aggiornaListaVoli(controller.getTuttiVoli());
        initListeners(controller);
        frame.pack();
    }



    /**
     * Inizializza tutti gli actionListener
     */
    private void initListeners(Controller controller) {

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String emailInserita = email.getText();
                    String passwordInserita = new String(password.getPassword());

                    if(emailInserita.isEmpty() || passwordInserita.isEmpty()) {
                        throw new IllegalArgumentException("Username o password mancante!");
                    }


                    String result = controller.login(emailInserita, passwordInserita);

                    if ("amministratore".equals(result)) {
                        new Amministratore(controller, frame).frame.setVisible(true);
                    } else if ("utente".equals(result)) {
                        new gui.Utente(controller, frame).frame.setVisible(true);
                    }else{
                        throw new IllegalArgumentException("Utente non trovato");
                    }

                    frame.dispose();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore di accesso", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore imprevisto: ", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    /**
     * Metodo per popolare la pagina Ospite di tutti i voli disponibili
     * Crea un JPanel per ogni elemento
     * e lo popola con informazioni utilizzando i metodi dell'oggetto volo corrispondente
     *
     *
     * @param listaVoli ArrayList dei Voli disponibili
     */
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,8, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
            pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
            if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
            if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
            pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
            pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));
            pannelloVolo.add(new JLabel("STATO: " + volo.getStatoVolo()));

            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));
            listaVoliPanel.revalidate();
            listaVoliPanel.repaint();
        }
    }

    /**
     * Ritorna il panel principale della pagina Ospite
     *
     * @return Oggetto panel
     */
    public JPanel getMainPanel() {
        return ospiteContainer;
    }
}
