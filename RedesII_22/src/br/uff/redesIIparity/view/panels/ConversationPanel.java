/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.view.panels;

import br.uff.redesIIparity.controller.ConversationController;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author claudio
 */
public class ConversationPanel extends JPanel{
    
    private JButton                 btSend;
    
    private JTextArea               taMessage;
    
    private final ConversationController  controller;
    
    public ConversationPanel ( ConversationController controller )
    {
        super ();
        
        this.controller = controller;
        
        initComponents ();
        
        initLayout ();
        
        initListeners ();
        
    }

    private void initComponents() 
    {
        this.btSend      = new JButton   ( "Send" );
        
        this.taMessage   = new JTextArea (  );
    }

    private void initLayout() 
    {
        GroupLayout layout = new GroupLayout ( this );
        
        layout.setHorizontalGroup( layout.createSequentialGroup()
                
            .addContainerGap()
                
            .addGroup ( layout.createParallelGroup ( GroupLayout.Alignment.LEADING )
                    
                    .addComponent ( taMessage )
                    
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap(0,350)
                            .addComponent( btSend )
                    )
                    
            )
            .addContainerGap()    
        );
        
        layout.setVerticalGroup( layout.createSequentialGroup()
                .addGap(10)
                .addComponent ( taMessage )
                .addGap(10)
                .addComponent ( btSend )
                .addGap(10)
                
        );
        
        this.setLayout ( layout );
    }

    private void initListeners() 
    {
        btSend.addActionListener (controller);
    }
    
    /**
     * This method returns message to be sent.
     * 
     * @return message
     */
    public String getMessage ()
    {
        return (taMessage == null)  ?   ""  :   taMessage.getText (  ) ;
    }

    public void cleanTextArea() 
    {
        taMessage.setText ( "" );
    }
    
}
