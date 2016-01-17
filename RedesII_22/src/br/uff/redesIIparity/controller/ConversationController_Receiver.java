/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.controller;

import br.uff.redesIIparity.Util.ParityMatriz;
import br.uff.redesIIparity.model.ConversationModel_Receiver;
import br.uff.redesIIparity.view.panels.ConversationPanel_Receiver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author claudio
 */
public class ConversationController_Receiver implements ActionListener {

    private final ConversationModel_Receiver model = new ConversationModel_Receiver();

    private final Logger logger = Logger.getLogger(ConversationController_Receiver.class.getName());

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton btOpen = (JButton) e.getSource();

        ConversationPanel_Receiver panel = (ConversationPanel_Receiver) btOpen.getParent();
        
        model.GetPanel(panel);

        try {
            String caminho = getPath();
            
            byte[] vet = model.getMessage(caminho);
            
            model.Decoder(vet);
            
            //ParityMatriz parityMatriz = new ParityMatriz(vet);
            
            //panel.showParityMatriz(parityMatriz);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public static String getPath() {

        String caminho = null;

        JFileChooser abrir = new JFileChooser("..\\resources");
        int retorno = abrir.showOpenDialog(null);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminho = abrir.getSelectedFile().getAbsolutePath();
        }
        return caminho;
    }

}
