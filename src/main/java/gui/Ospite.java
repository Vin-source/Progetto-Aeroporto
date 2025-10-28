package gui;

// import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Ospite extends JFrame {
    private JPanel ospiteContainer;

    private JTextField email;
    private JPasswordField password;
    private JButton accediButton;


    private JPanel voliContainer;


    // voli
    private JScrollPane listaVoliScroll;
    private JPanel listaVoliPanel;




 //   private Controller controller;

    public Ospite() {
        // this.controller = new Controller();





        // aggiornaListaVoli(this.controller.getVoli());



        // initListener();

        listaVoliPanel = new JPanel();
        listaVoliPanel.setLayout(new GridLayout(20, 1));
        listaVoliScroll.setViewportView(listaVoliPanel);

        ArrayList<Volo> voli = new ArrayList<>();
        voli.add(new Volo("a", "a", "a", "q", "12/10/1999", "13:23", 2));
        voli.add(new Volo("AZ78893", "ItAirways", "Roma", "Napoli", "16/10/1999", "17:30", 23));
        aggiornaListaVoli(voli);
    }



/*
    private void initListener() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String emailInserita = email.getText();
                    String passwordInserita = new String(password.getPassword());

                    String result = controller.login(emailInserita, passwordInserita);

                    if ("amministratore".equals(result)) {
                        Amministratore amministratore = new Amministratore(controller);
                        amministratore.frame.setVisible(true);
                        frame.setVisible(false);

                    } else if ("utente".equals(result)) {
                        Utente utente = new Utente(controller);
                        utente.frame.setVisible(true);
                        frame.setVisible(false);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore: username o password vuoti o non validi!", JOptionPane.ERROR_MESSAGE);
                }catch(AuthenticationException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore: Credenziali errate!", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore imprevisto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
*/



    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(1,7, 10, 10));
            pannelloVolo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pannelloVolo.add(new JLabel("Codice volo: " + volo.getCodiceVolo()));
            pannelloVolo.add(new JLabel("Compagnia aerea: " + volo.getCompagniaAerea()));
            if(volo.getOrigine() != null) pannelloVolo.add(new JLabel("Origine: " + volo.getOrigine()));
            if(volo.getDestinazione() != null) pannelloVolo.add(new JLabel("Destinazione: " + volo.getDestinazione()));
            pannelloVolo.add(new JLabel("Data: " + volo.getData()));
            pannelloVolo.add(new JLabel("Orario di arrivo: " + volo.getOrarioPrevisto()));
            pannelloVolo.add(new JLabel("Ritardo: " + volo.getRitardo() + " minuti"));
            JButton prenota = new JButton("Prenota");


            pannelloVolo.add(prenota);

            listaVoliPanel.add(pannelloVolo);
            listaVoliPanel.revalidate();
            listaVoliPanel.repaint();
        }
    }

    public JPanel getMainPanel() {
        return ospiteContainer;
    }
}
