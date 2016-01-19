/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.model;

import br.uff.redesIIparity.model.services.SenderMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claudio
 */
public class ConversationModel {
    
    private final int MINELEMENTSNUMBER     = 8;
    
    private final int ELEMENTSINPARITYARRAY = 10;
    
    private final SenderMessage sender  = new SenderMessage (  );
    
    
    
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

    private byte getColumnParity(byte[] messagesInBytes) 
    {
        byte lineParity = 0;
        
        for (int i = 0; i < messagesInBytes.length; i++) {
            lineParity ^= messagesInBytes[i];
        }
        
        return lineParity;
    }

    private byte getLineParity(byte[] messagesInBytes) 
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

    public void workFile(File selectedFile) throws IOException
    {
        this.sender.createCloneFileName ( selectedFile.getName() );
        
        RandomAccessFile raf = null;
        
        try {
            raf = new RandomAccessFile( selectedFile, "r" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConversationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int count = 0;
        
        byte[] byteBuffer = getNewByteArray();
        
        int iteraction = 1;
        
        if( raf != null )
        {
            long length = raf.length();
            
            if( length < 8 )
            {
                iteraction = (int)length;
                for (int i = 0; i < length; i++) {
                    byteBuffer[i] = raf.readByte();
                    
                }
            }
            else
            {
                while( count < length )
                {
                    byteBuffer[iteraction - 1] = raf.readByte();

                    if ( iteraction == 8 )
                    {

                        sender.writeBuffer( getByteArrayWithParity(byteBuffer) );

                        iteraction = 1;
                        byteBuffer = getNewByteArray();
                    }

                    iteraction ++;
                    count ++;
                }
            }
            
            if( iteraction < 8 )
            {
                int iteractionRemaining = 8 - iteraction + 1;
                for (int i = 0; i < iteractionRemaining; i++) {
                    byteBuffer[i+iteraction-1] = 0;
                }
                sender.writeBuffer( getByteArrayWithParity(byteBuffer) );
            }
            
            
            
            sender.closeFile();
            
            raf.close();
        }
        
    }

}
