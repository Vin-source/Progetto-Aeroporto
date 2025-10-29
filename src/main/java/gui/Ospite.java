package gui;

// import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        initListener();
    }




    private void initListener() {
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // try {
                    String emailInserita = email.getText();
                    String passwordInserita = new String(password.getPassword());

                    email.setText("pippo");
                    password.setText("mauro");
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
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore: username o password vuoti o non validi!", JOptionPane.ERROR_MESSAGE);
                }catch(AuthenticationException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore: Credenziali errate!", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore imprevisto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                } */
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
            listaVoliPanel.revalidate();
            listaVoliPanel.repaint();
        }
    }

    public JPanel getMainPanel() {
        return ospiteContainer;
    }
}
