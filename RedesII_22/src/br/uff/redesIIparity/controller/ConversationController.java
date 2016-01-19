/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.controller;

import br.uff.redesIIparity.Util.HelperAttributes;
import br.uff.redesIIparity.model.ConversationModel;
import br.uff.redesIIparity.view.panels.ConversationPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
        JButton           button    = ( JButton ) e.getSource();
        
        if ( button.getName().equalsIgnoreCase ( HelperAttributes.BTSENDNAME ) )
        {
        
            ConversationPanel panel     = ( ConversationPanel ) button.getParent();

            String message              = panel.getMessage();

            try {
                boolean wasSent = model.workMessage ( message );

                if(wasSent) panel.cleanTextArea (  );

            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        else if ( button.getName().equalsIgnoreCase ( HelperAttributes.BTFILEIMPORTNAME ) )
        {
            File selectedFile           = getSelectedFile (  );
            
            ConversationPanel panel     = ( ConversationPanel ) button.getParent();
            
            String fileName             = null;
            
            if ( selectedFile != null ) 
            {
                int optionChosen = JOptionPane.showConfirmDialog ( null, "Deseja processar a paridade do arquivo selecionado ?" );
                
                if ( optionChosen == JOptionPane.YES_OPTION )
                {
                    fileName = selectedFile.getName();
                    
                    try {
                        this.model.workFile ( selectedFile );
                    } catch (IOException ex) {
                        Logger.getLogger(ConversationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    fileName = null;
                }
            
            }
            
            panel.setButtonImportText ( fileName );
            
            JOptionPane.showConfirmDialog(null, "Parity Caculated successfully.");
            
        }
        
    }
    
    private File getSelectedFile (  )
    {
        JFileChooser fileChooser = new JFileChooser (  );
        
        int result = fileChooser.showOpenDialog ( null );
        
        if ( result == JFileChooser.APPROVE_OPTION )
        {
            File file = fileChooser.getSelectedFile();
            return file;
        }
            
        return null;
    }
    
}
