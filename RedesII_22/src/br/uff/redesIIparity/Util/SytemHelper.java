/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.Util;

/**
 *
 * @author claudio
 */
public class SytemHelper {
    
    /**
     * This Method returns a string that contains a string with completely 8-bits representation
     * 
     * @param message
     * @return bitString
     */
    public static String getByteAsBitString(String message)
    {
        int count           = 8 - message.length();
        
        String bitsMessage  = "";
        
        for (int i = 0; i < count; i++) {
            bitsMessage    += "0";
            
        }
        
        bitsMessage        += message;
        
        return bitsMessage;
        
    }
}
