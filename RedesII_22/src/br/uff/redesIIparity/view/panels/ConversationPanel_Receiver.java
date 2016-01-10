/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.view.panels;

import br.uff.redesIIparity.controller.ConversationController_Receiver;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author claudio
 */
public class ConversationPanel_Receiver extends JPanel{
    
    private JButton                 btOpen;
    
    private JTextArea               taMessage;
    
    private final ConversationController_Receiver  controller;
    
    public ConversationPanel_Receiver ( ConversationController_Receiver controller )
    {
        super ();
        
        this.controller = controller;
        
        initComponents ();
        
        initLayout ();
        
        initListeners ();
        
    }

    private void initComponents() 
    {
        this.btOpen      = new JButton   ( "Open" );
        
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
                            .addComponent( btOpen )
                    )
                    
            )
            .addContainerGap()    
        );
        
        layout.setVerticalGroup( layout.createSequentialGroup()
                .addGap(10)
                .addComponent ( taMessage )
                .addGap(10)
                .addComponent ( btOpen )
                .addGap(10)
                
        );
        
        this.setLayout ( layout );
    }

    private void initListeners() 
    {
        btOpen.addActionListener (controller);
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

    public void cleanTextArea(String pCaminho) 
    {
        taMessage.setText ( pCaminho );
    }
    
}
