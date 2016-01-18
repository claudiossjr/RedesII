/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.model.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claudio
 */
public class SenderMessage {
    
    
    private RandomAccessFile raf;
    
    private String originalPath;
    
    private ArrayList<Byte> wholeMessageInByte;
    
    private Path path;
    
    private boolean firstTime = true;
    
    public SenderMessage ( )
    {
        initComponents (  );
    }
    
    public boolean sendMessage ( byte[] message )
    {
        if( firstTime )
        {
            createNewFile ();
            firstTime = false;
        }
        
        for (int i = 0; i < message.length; i++) {
            wholeMessageInByte.add(message[i]);
        }
        
        return true;
    }

    public void flushMessage ( ) throws Exception
    {
        firstTime = true;
        byte[] tempArray = new byte[wholeMessageInByte.size()];
        
        int i = 0;
        
        for( Byte b : wholeMessageInByte ) tempArray[i++] = b;
        
        raf.write ( tempArray );
        
        raf.close (  );
        
    }

    private void initComponents() 
    {
        wholeMessageInByte = new ArrayList<>();
        
        originalPath       = "../resources/";
        
        path               = Paths.get( originalPath );
    }

    private void createNewFile() 
    {
        
        if( !Files.exists( path ) )
        {
            try {
                Files.createDirectory( path );
            } catch (IOException ex) {
                Logger.getLogger(SenderMessage.class.getName()).log(Level.SEVERE, "Can not create resources folder", ex);
            }
        }
        String filePath    = originalPath + UUID.randomUUID().toString() + ".data";
        
        try {
            raf                = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SenderMessage.class.getName()).log(Level.SEVERE, " Don't have file", ex);
        }
    }

    public void createCloneFileName(String name) 
    {
        if( !Files.exists( path ) )
        {
            try {
                Files.createDirectory( path );
            } catch (IOException ex) {
                Logger.getLogger(SenderMessage.class.getName()).log(Level.SEVERE, "Can not create resources folder", ex);
            }
        }
        
        String filePath    = originalPath + UUID.randomUUID().toString()+"-"+ name;
        
        try {
            raf                = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SenderMessage.class.getName()).log(Level.SEVERE, " Don't have file", ex);
        }
        
    }

    public void closeFile() 
    {
        try {
            raf.close();
        } catch (IOException ex) {
            Logger.getLogger(SenderMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeBuffer(byte[] byteArrayWithParity) throws IOException 
    {
        if(raf != null)
        {
            raf.write(byteArrayWithParity);
        }
    }
    
}
