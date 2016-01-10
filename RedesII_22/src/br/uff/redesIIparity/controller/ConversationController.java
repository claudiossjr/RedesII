/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.controller;

import br.uff.redesIIparity.model.ConversationModel;
import br.uff.redesIIparity.view.panels.ConversationPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author claudio
 */
public class ConversationController implements ActionListener{
    
    private final ConversationModel model = new ConversationModel (  );
    
    private final Logger logger = Logger.getLogger( ConversationController.class.getName() );

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        JButton           btSend    = ( JButton ) e.getSource();
        
        ConversationPanel panel     = ( ConversationPanel ) btSend.getParent();
        
        String message              = panel.getMessage();
        
        try {
            boolean wasSent = model.workMessage ( message );
            
            if(wasSent) panel.cleanTextArea (  );
                
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
    }
    
}
