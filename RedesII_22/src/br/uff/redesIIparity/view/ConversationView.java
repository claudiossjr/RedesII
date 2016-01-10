/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.view;

import br.uff.redesIIparity.controller.ConversationController;
import br.uff.redesIIparity.view.panels.ConversationPanel;
import javax.swing.JFrame;

/**
 *
 * @author claudio
 */
public class ConversationView extends JFrame{
    
    private final ConversationController controller = new ConversationController (  );
    
    private final ConversationPanel      panel      = new ConversationPanel ( controller );
    
    public ConversationView ()
    {
        super();
        
        initComponents ();
        
    }

    private void initComponents() 
    {
        this.add ( panel );
        
        this.setSize ( 800, 600 );
        
        this.setLocationRelativeTo ( null );
        
        this.setVisible ( true );
        
        this.setResizable ( false );
        
        this.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
    }
    
    
    
}
