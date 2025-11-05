package gui;
// import controller.*;
import model.Volo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificaVolo {
    public JFrame frame;

    private JTextField dataAttuale;
    private JTextField nuovaData;
    private JTextField orarioAttuale;
    private JTextField nuovoOrario;
    private JTextField ritardoAttuale;
    private JTextField nuovoRitardo;


    private JButton confermaCambiamentiVolo;
    private JPanel aggiornaVoloPanel;
    private JLabel codiceVolo;

    private JLabel gateAttuale;
    private JButton modificaGate;



    public ModificaVolo(/*Controller controller,*/ JFrame frameChiamante, Volo volo) {
        frame = new JFrame("Aggiorna Volo");
        frame.setContentPane(aggiornaVoloPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        codiceVolo.setText(volo.getCodiceVolo());
        dataAttuale.setText(volo.getData());
        orarioAttuale.setText(volo.getOrarioPrevisto());
        ritardoAttuale.setText(String.valueOf(volo.getRitardo()));
        gateAttuale.setText(String.valueOf(volo.getGate().getNumero()));

        dataAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        orarioAttuale.setEditable(false);
        ritardoAttuale.setEditable(false);

        initListeners(frameChiamante, volo);
    }


    public void initListeners(JFrame frameChiamante, Volo volo) {

        confermaCambiamentiVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuovaDataText = nuovaData.getText();
                String nuovoOrarioText = nuovoOrario.getText();
                String nuovoRitardoText = nuovoRitardo.getText();
                int gate = Integer.parseInt(gateAttuale.getText());

                /*
                if(nuovaDataText.isEmpty() && nuovoOrarioText.isEmpty() && nuovoRitardoText.isEmpty()){
                    codiceVolo.setText("Non hai inserito nuovi dati!");
                } else {
                        Boolean result = controller.aggiornaVolo(volo.getCodiceVolo(), nuovaDataText, nuovoOrarioText, nuovoRitardoText, gate);
                    if(result){
                        JOptionPane.showMessageDialog(null, "Volo aggiornato con successo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Errore! Volo non aggiornato");
                    }
                }
                */
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });

        modificaGate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaGate nuovoGate = new ModificaGate(/*controller, AggiornaVolo.this, gateAttuale.getText()*/frame, gateAttuale.getText());
                nuovoGate.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }

    public void impostaNuovoGate(int gate){
        gateAttuale.setText(String.valueOf(gate));
    }
}
