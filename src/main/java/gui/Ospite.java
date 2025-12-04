package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Classe che rappresenta la schermata principale dell'Ospite (schermata di accesso).
 * Permette di inserire i dati nella login e di visualizzare l'elenco dei voli .
 */
public class Ospite extends JFrame {
    private JPanel ospiteContainer;
    /**
     * Il frame della finestra Ospite
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
     * Costruisce la finestra Ospite
     * <p></p>
     *
     * Il costruttore inizializza gli elementi, il layout, i listener per i bottoni
     * e chiama il metodo aggiornaListaVoli per mostrare i voli disponibili
     *
     * @param controller Il controller che verrà passato dal Main method
     */

    public Ospite(Controller controller){



        frame = new JFrame("La mia GUI Swing");
        frame.setContentPane(ospiteContainer);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        this.controller = controller;



        listaVoliPanel = new JPanel();
        listaVoliPanel.setLayout(new GridLayout(20, 1));
        listaVoliScroll.setViewportView(listaVoliPanel);


        aggiornaListaVoli(controller.getTuttiVoli());
        initListeners(controller);
        frame.pack();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                aggiornaListaVoli(controller.getTuttiVoli());
            }
        });

        frame.setVisible(true);
    }



    /**
     * Metodo che inizializza l'actionListener del bottone accedi per la login
     * L'actionListener verifica prima se i dati esistono, chiama il controller
     * per verificare se esiste l'utente e ritorna un messaggio sulla base dell'esito
     * dell'operazione.
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
                    }else if(emailInserita.length() > 30 || passwordInserita.length() > 30){
                        throw new IllegalArgumentException("Username o password troppo lunghi!");
                    }


                    String result = controller.login(emailInserita, passwordInserita);

                    if ("amministratore".equals(result)) {
                        new Amministratore(controller, frame).frame.setVisible(true);
                        frame.dispose();
                    } else if ("utente".equals(result)) {
                        new gui.Utente(controller, frame).frame.setVisible(true);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, result, "Errore di accesso", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore di accesso", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception _) {
                    JOptionPane.showMessageDialog(null, "Errore imprevisto: ", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    /**
     * Metodo per popolare la pagina Ospite con tutti i voli disponibili
     * Crea un JPanel per ogni volo
     * e lo popola con informazioni utilizzando i metodi dell'oggetto volo corrispondente
     *
     * @param listaVoli ArrayList dei Voli disponibili
     */
    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        listaVoliPanel.removeAll();

        if(listaVoli.isEmpty()){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,8, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pannelloVolo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            pannelloVolo.add(new JLabel("Non ci sono voli attualmente disponibili. Ci scusiamo per il disagio!"));


            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));

            listaVoliPanel.revalidate();
            listaVoliPanel.repaint();
        }else{
            for(Volo volo: listaVoli){
                JPanel pannelloVolo = new JPanel();
                pannelloVolo.setLayout(new GridLayout(1,8, 10, 10));
                pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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

                listaVoliPanel.add(pannelloVolo);
                listaVoliPanel.add(Box.createVerticalStrut(5));
                listaVoliPanel.revalidate();
                listaVoliPanel.repaint();
            }
        }
    }

    /**
     * Ritorna il panel principale della pagina Ospite
     *
     * @return Oggetto panel principale
     */
    public JPanel getMainPanel() {
        return ospiteContainer;
    }
}
