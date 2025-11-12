package gui;
//import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Classe Modifica gate della gui
 */
public class ModificaGate {
    private JComboBox<String> gateDisponibili;
    private JButton confermaGateButton;
    private JPanel modificaGate;
    /**
     * Il frame della classe ModificaGate
     */
    public JFrame frame;
    private JTextField gateAttuale;
    private JButton annullaButton;

    /**
     * Costruisce la pagina per modificare i gate dei voli
     *
     * @param frameChiamante Il frame di Amministratore
     * @param gateAttuale    Il gate attuale
     * @param modificaVolo   Il volo da modificare
     */
    public ModificaGate(JFrame frameChiamante, String gateAttuale, ModificaVolo modificaVolo) {
        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        this.gateAttuale.setText(gateAttuale);
        this.gateAttuale.setEditable(false);
        initListeners(frameChiamante, modificaVolo);
    }

    /**
     * Metodo che contiene i vari action listener
     *
     * @param frameChiamante Il frame di Amministratore
     * @param modificaVolo   Il volo da modificare
     */
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
