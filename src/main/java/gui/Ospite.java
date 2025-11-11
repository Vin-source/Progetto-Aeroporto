package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ospite extends JFrame {
    private JPanel ospiteContainer;
    public JFrame frame;
    private JTextField email;
    private JPasswordField password;
    private JButton accediButton;


    private JPanel voliContainer;


    // voli
    private JScrollPane listaVoliScroll;
    private JPanel listaVoliPanel;




    private Controller controller;

    public Ospite(Controller controller) {
        this.controller = controller;

        frame = new JFrame("La mia GUI Swing");
        frame.setContentPane(ospiteContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // aggiornaListaVoli(this.controller.getVoli());



        listaVoliPanel = new JPanel();
        listaVoliPanel.setLayout(new GridLayout(20, 1));
        listaVoliScroll.setViewportView(listaVoliPanel);

        aggiornaListaVoli(this.controller.getTuttiVoli());

        initListeners();

        frame.pack();
        frame.setVisible(true);
    }




    private void initListeners() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String emailInserita = email.getText();
                    String passwordInserita = new String(password.getPassword());

                    if(emailInserita.isEmpty() || passwordInserita.isEmpty()) {
                        throw new IllegalArgumentException("Username o password mancante!");
                    }

/*
                    if(emailInserita.contains("admin")){
                        new Amministratore(frame, controller).frame.setVisible(true);
                    }else {
                        new gui.Utente(frame,).frame.setVisible(true);
                    }

 */
                    String result = controller.login(emailInserita, passwordInserita);
                    if("amministratore".equals(result)) {
                        new Amministratore(frame, controller);
                        frame.setVisible(false);
                    }else if("utente".equals(result)) {
                        new Utente(frame, controller);
                        frame.setVisible(false);
                    }
                    // String result = controller.login(emailInserita, passwordInserita);

                    /*if ("amministratore".equals(result)) {
                        Amministratore amministratore = new Amministratore(controller);
                        amministratore.frame.setVisible(true);
                        frame.setVisible(false);

                    } else if ("utente".equals(result)) {
                        Utente utente = new Utente(controller);
                        utente.frame.setVisible(true);
                        frame.setVisible(false);
                    }
                    */
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore di accesso", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception _) {
                    JOptionPane.showMessageDialog(null, "Errore imprevisto: ", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }




    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,7, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pannelloVolo.add(new JLabel("CODICE: " + volo.getCodiceVolo().toUpperCase()));
            pannelloVolo.add(new JLabel("COMPAGNIA AEREA: " + volo.getCompagniaAerea().toUpperCase()));
            if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("ORIGINE: " + volo.getOrigine().toUpperCase()));
            if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("DESTINAZIONE: " + volo.getDestinazione().toUpperCase()));
            pannelloVolo.add(new JLabel("DATA: " + volo.getData().toUpperCase()));
            pannelloVolo.add(new JLabel("ARRIVA ALLE ORE: " + volo.getOrarioPrevisto().toUpperCase()));
            pannelloVolo.add(new JLabel("RITARDO: " + volo.getRitardo() + " minuti"));

            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.add(Box.createVerticalStrut(5));
        }
        listaVoliPanel.revalidate();
        listaVoliPanel.repaint();
    }

    public JPanel getMainPanel() {
        return ospiteContainer;
    }
}
