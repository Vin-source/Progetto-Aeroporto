package gui;
//import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaGate {
    private JComboBox<String> gateDisponibili;
    private JButton confermaGateButton;
    private JPanel modificaGate;
    public JFrame frame;
    private JTextField gateAttuale;
    private JButton annullaButton;

    public ModificaGate(JFrame frameChiamante) {
        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        confermaGateButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(frame, "Il gate è stato modificato");
               frameChiamante.setVisible(true);
               frame.dispose();
           }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

/*
    public ModificaGate(Controller controller, AggiornaVolo frameChiamante, String gateAttuale) {

        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        this.gateAttuale.setText(gateAttuale);

        confermaGateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testoGate = gateDisponibili.getSelectedItem().toString();
                int nuovoGate = Integer.parseInt(testoGate);
                frameChiamante.impostaNuovoGate(nuovoGate);
                frameChiamante.frame.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

 */
}
