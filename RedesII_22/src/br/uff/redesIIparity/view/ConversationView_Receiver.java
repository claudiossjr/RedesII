/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.view;

import br.uff.redesIIparity.controller.ConversationController_Receiver;
import br.uff.redesIIparity.view.panels.ConversationPanel_Receiver;
import javax.swing.JFrame;

/**
 *
 * @author claudio
 */
public class ConversationView_Receiver extends JFrame{
    
    private final ConversationController_Receiver controller = new ConversationController_Receiver (  );
    
    private final ConversationPanel_Receiver      panel      = new ConversationPanel_Receiver ( controller );
    
    public ConversationView_Receiver ()
    {
        super();
        
        initComponents ();
        
    }

    private void initComponents() 
    {
        this.add ( panel );
        
        this.setSize ( 900, 600 );
        
        this.setLocationRelativeTo ( null );
        
        this.setVisible ( true );
        
        this.setResizable ( false );
        
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
    }
    
    
    
}
