package gui;
// import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaPrenotazione {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton CONFERMAButton;
    private JButton CANCELLAButton;
    public JFrame frame;

    // private Controller controller;
    private String codiceVolo; // necessario per modificare la prenotazione giusta

    public ModificaPrenotazione(/*Controller controller,*/JFrame frameChiamante, String codiceVolo, String idPrenotazione) {
        frame = new JFrame("Modifica Prenotazione");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        // this.controller = controller;
        this.codiceVolo = codiceVolo;

        initListeners(frameChiamante);

    }


    private void initListeners(JFrame frameChiamante) {

        CONFERMAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuovoNome = textField1.getText();
                    String nuovoCognome = textField2.getText();
                    String cartaIdentita = textField3.getText();

                    System.out.println("Nome: " + nuovoNome);

                    /* boolean risultato = controller.modificaPrenotazione(codiceVolo, nuovoNome, nuovoCognome, cartaIdentita, idPrenotazione);
                    if (risultato) {
                        JOptionPane.showMessageDialog(null, "Prenotazione modificata con successo");
                        resetFields();
                        frame.dispose();
                        frameChiamante.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Modifica fallita: prenotazione non trovata");
                    }
                    */
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CANCELLAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void resetFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
