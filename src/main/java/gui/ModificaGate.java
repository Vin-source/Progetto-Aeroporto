package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 *  Classe che rappresenta la schermata ModificaGate della gui
 */
public class ModificaGate {
    private JComboBox<String> gateDisponibili;
    private JButton confermaGateButton;
    private JPanel modificaGate;
    /**
     * Il Frame della classe ModificaGate
     */
    public JFrame frame;
    private JTextField gateAttuale;
    private JButton annullaButton;

    private Controller controller;
    private String codiceVolo;
    /**
     * Costruisce la pagina per modificare i gate dei voli
     *
     * @param frameChiamante Il frame di Amministratore
     * @param controller     Il controller che effettua le chiamate al model/db
     * @param gateAttuale    Il gate attuale
     * @param modificaVolo   Il volo da modificare
     */
    public ModificaGate(JFrame frameChiamante,Controller controller, String gateAttuale, ModificaVolo modificaVolo, String codiceVolo) {
        this.controller = controller;
        this.codiceVolo = codiceVolo;


        frame = new JFrame("Modifica Gate");
        frame.setContentPane(modificaGate);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);


        this.gateAttuale.setText(gateAttuale);
        this.gateAttuale.setEditable(false);

        popolaGateDisponibili();
        initListeners(frameChiamante, modificaVolo);

        frame.setVisible(true);
    }


    /**
     * Popola il menu a tendina dei gate con i gate disponibili.
     * Recupera la lista dei gate dal Controller.
     */
    private void popolaGateDisponibili() {

        gateDisponibili.removeAllItems();
        gateDisponibili.addItem(null);
        ArrayList<String> gateRisultanti = controller.getGateDisponibili();
        if(gateRisultanti == null) return;
        for(String s : gateRisultanti){
            gateDisponibili.addItem(s);
        }
    }


    /**
     * Metodo che contiene i vari action listener
     *
     * @param frameChiamante Il frame di Amministratore
     * @param modificaVolo   L'oggetto volo da modificare
     */
    public void initListeners(JFrame frameChiamante, ModificaVolo modificaVolo) {
        confermaGateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gateSelezionato = (String) gateDisponibili.getSelectedItem();
                modificaVolo.impostaNuovoGate(gateSelezionato);
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
