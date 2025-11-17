package gui;
import controller.Controller;
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

    private Controller controller;

    /**
     * Costruisce la pagina per modificare i gate dei voli
     *
     * @param frameChiamante Il frame di Amministratore
     * @param controller     Il controller che effettua le chiamate al model/db
     * @param gateAttuale    Il gate attuale
     * @param modificaVolo   Il volo da modificare
     */
    public ModificaGate(JFrame frameChiamante,Controller controller, String gateAttuale, ModificaVolo modificaVolo) {
        this.controller = controller;


        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        this.gateAttuale.setText(gateAttuale);
        this.gateAttuale.setEditable(false);

        popolaGateDisponibili();
        initListeners(frameChiamante, modificaVolo);

        frame.setVisible(true);
    }


    /**
     * Popola il menu a tendina dei gate con i gate disponibili
     */
    private void popolaGateDisponibili() {

        gateDisponibili.removeAllItems();

        gateDisponibili.addItem("1");
        gateDisponibili.addItem("2");
        gateDisponibili.addItem("3");
        gateDisponibili.addItem("4");
        gateDisponibili.addItem("5");
        gateDisponibili.addItem("6");
        gateDisponibili.addItem("7");
        gateDisponibili.addItem("8");
        gateDisponibili.addItem("9");
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
                String gateSelezionato = (String) gateDisponibili.getSelectedItem();

                modificaVolo.impostaNuovoGate(Integer.valueOf(gateSelezionato));

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
