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

    public ModificaGate(JFrame frameChiamante, String gateAttuale) {
        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        this.gateAttuale.setText(gateAttuale);
        initListeners(frameChiamante);
    }

    public void initListeners(JFrame frameChiamante) {
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
}
