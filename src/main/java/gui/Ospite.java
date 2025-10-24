package gui;

import controller.Controller;
import model.Volo;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ospite extends JFrame {
    public JFrame frame;
    private JPanel loginPanel;
    private JTextField email;
    private JPasswordField password;
    private JButton accediButton;
    private JScrollPane listaVoliScroll;


    private Controller controller;

    public Ospite() {
        this.controller = new Controller();

        frame = new JFrame("Login");


        aggiornaListaVoli(this.controller.getVoli());

        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        initListener();

        frame.setVisible(true);
    }

    public JPanel getPanel() {
        return loginPanel;
    }



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


    private void aggiornaListaVoli(ArrayList<Volo> listaVoli) {
        listaVoliScroll.removeAll();

        for(Volo volo: listaVoli){
            JPanel pannelloVolo = new JPanel();
            pannelloVolo.setLayout(new GridLayout(0,2));

            pannelloVolo.add(new JLabel("Codice volo: "));
            JTextField codice = new JTextField(volo.getCodiceVolo());
            codice.setEditable(false);
            pannelloVolo.add(codice);

            pannelloVolo.add(new JLabel("Compagnia aerea: "));
            JTextField compagnia = new JTextField(volo.getCompagniaAerea());
            compagnia.setEditable(false);
            pannelloVolo.add(compagnia);

            pannelloVolo.add(new JLabel("Origine: "));
            JTextField origine = new JTextField(volo.getOrigine());
            origine.setEditable(false);
            pannelloVolo.add(origine);

            pannelloVolo.add(new JLabel("Destinazione: "));
            JTextField destinazione = new JTextField(volo.getDestinazione());
            destinazione.setEditable(false);
            pannelloVolo.add(destinazione);

            pannelloVolo.add(new JLabel("Data: "));
            JTextField data = new JTextField(volo.getData());
            data.setEditable(false);
            pannelloVolo.add(data);

            pannelloVolo.add(new JLabel("Orario di arrivo: "));
            JTextField orario = new JTextField(volo.getOrarioPrevisto());
            orario.setEditable(false);
            pannelloVolo.add(orario);

            pannelloVolo.add(new JLabel("Ritardo: "));
            JTextField ritardo = new JTextField(volo.getRitardo());
            ritardo.setEditable(false);
            pannelloVolo.add(ritardo);

            JButton prenota = new JButton("Prenota");
            pannelloVolo.add(prenota);

            listaVoliScroll.add(pannelloVolo);
        }

        listaVoliScroll.revalidate();
        listaVoliScroll.repaint();
    }
}
