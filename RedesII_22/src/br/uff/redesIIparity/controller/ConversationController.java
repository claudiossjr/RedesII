/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.controller;

import br.uff.redesIIparity.view.panels.ConversationPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author claudio
 */
public class ConversationController implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        JButton           btSend    = ( JButton ) e.getSource();
        
        ConversationPanel panel     = ( ConversationPanel ) btSend.getParent();
        
        System.out.println( panel.getMessage());
        
    }
    
}
