/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.model;

import br.uff.redesIIparity.model.services.ReceiverMessage;

/**
 *
 * @author claudio
 */
public class ConversationModel_Receiver {
    
    private final int MINELEMENTSNUMBER     = 8;
    
    private final int ELEMENTSINPARITYARRAY = 10;
    
    private final ReceiverMessage sender  = new ReceiverMessage (  );
    
    
    
    public boolean workMessage ( String message ) throws Exception
    {
        char[] messageArray     = message.toCharArray();
        
        int elementsRemaining   = MINELEMENTSNUMBER - messageArray.length;
        
        if( elementsRemaining > 0 )
        {
            char[] tempArray    = new char[MINELEMENTSNUMBER];
            
            int countStopped    = 0;
            
            for( int i = 0 ; i < messageArray.length; i ++ )
            {
                tempArray[i]    = messageArray[i];
                countStopped     ++;
            }
            
            for( int i = 0 ; i < elementsRemaining; i++ )
            {
                tempArray[i + countStopped] = '0';
            }
            
            messageArray        = tempArray;
            
        }
        
        int arrayPosition       = 0;
        
        byte[] messagesInBytes  = getNewByteArray (  );
        
        int tempArrayPosition   = 0;
        
        int iteration           = 1;
        
        while ( arrayPosition < messageArray.length )
        {
            if( iteration % 8 == 0 )
            {
                tempArrayPosition = 0;
                
                boolean wasSent = sender.sendMessage ( getByteArrayWithParity ( messagesInBytes ) );
                
                messagesInBytes = getNewByteArray (  );
                
                if( !wasSent )
                {
                    throw new Exception( "Message was not sent" );
                }
            
            }
            
            messagesInBytes[tempArrayPosition] = ( byte ) messageArray[ arrayPosition ];
            
            iteration           ++;
            tempArrayPosition   ++;
            arrayPosition       ++;
            
        }
        
        sender.flushMessage ();
        return true;
        
    }
    
    
    private byte[] getNewByteArray() {
        return new byte[MINELEMENTSNUMBER];
    }

    private byte[] getByteArrayWithParity(byte[] messagesInBytes) 
    {
        byte[] arrayWithParity  = new byte[ELEMENTSINPARITYARRAY];
        
        byte lineParity         = getLineParity ( messagesInBytes );
        
        byte columnParity       = getColumnParity ( messagesInBytes );
        
        arrayWithParity[0]      = columnParity;
        arrayWithParity[1]      = lineParity;
        
        fillArray ( messagesInBytes, arrayWithParity );
        
        return arrayWithParity;
    }

    private void fillArray(byte[] messagesInBytes, byte[] arrayWithParity) 
    {
        for (int i = 0; i < messagesInBytes.length; i++) {
            arrayWithParity[i+2] = messagesInBytes[i];
        }
    }

    private byte getLineParity(byte[] messagesInBytes) 
    {
        byte lineParity = 0;
        
        for (int i = 0; i < messagesInBytes.length; i++) {
            lineParity ^= messagesInBytes[i];
        }
        
        return lineParity;
    }

    private byte getColumnParity(byte[] messagesInBytes) 
    {
        byte columnParity = 0;
        
        for (int i = 0; i < messagesInBytes.length; i++) 
        {
            columnParity     += doBitVerification ( messagesInBytes[i], MINELEMENTSNUMBER - (i+1));
        }
        
        return columnParity;
    }
    
    /**
     * This Method counts how many 1's bits have inside one byte. And them,
     * return a number that could be use to create a line parity number
     * 
     * @param element
     * @param position
     * @return number
     */
    private int doBitVerification( byte element, int position )
    {
        
        int elem = 1;
        
        boolean isOne = false;
        
        for(int i = 0 ; i < MINELEMENTSNUMBER ; i ++)
        {
            if( (element & elem) > 0 )
            {
                isOne = !isOne;
            }
            
            elem <<= 1;
        }
        
        return (isOne) ? (1 << position) : 0;
    }

}
