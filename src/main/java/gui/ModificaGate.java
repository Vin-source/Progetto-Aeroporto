package gui;
import controller.Controller;
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

    private Controller controller;

    public ModificaGate(JFrame frameChiamante,Controller controller, String gateAttuale, ModificaVolo modificaVolo) {
        this.controller = controller;
        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        this.gateAttuale.setText(gateAttuale);
        this.gateAttuale.setEditable(false);
        initListeners(frameChiamante, modificaVolo);
    }

    public void initListeners(JFrame frameChiamante, ModificaVolo modificaVolo) {
        confermaGateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificaVolo.impostaNuovoGate((Integer.valueOf((String) gateDisponibili.getSelectedItem())));
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
