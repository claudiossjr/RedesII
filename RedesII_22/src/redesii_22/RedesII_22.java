/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redesii_22;

import java.io.File;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author Claudio
 */
public class RedesII_22 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in          = new Scanner(System.in);
        String message      = in.nextLine();
        byte[] byteMessage  = message.getBytes();
        try{
            
            RandomAccessFile raf = new RandomAccessFile(new File("Message.meta"), "rw");
            
            for(int i = 0 ; i < byteMessage.length; i ++)
            {
                raf.writeByte( byteMessage[i] );
            }
            
            raf.close();
            
            raf = new RandomAccessFile( new File("Message.meta") , "r");
            
            int columnParity    = 0;
            int lineParity      = 0;
            
            for(int i = 0; i < raf.length(); i ++)
            {
                byte temp       = raf.readByte();
                columnParity    = columnParity ^ temp;
                lineParity     += doBitVerification(temp, i);
                String tempStr  = "" + temp;
                System.out.println(new BigInteger(tempStr.getBytes()).toString(2));
            }
            String tempOp       = "" + columnParity;
            System.out.println("Column " + (new BigInteger( tempOp.getBytes()).toString(2)) );
            tempOp              = "" + lineParity;
            System.out.println("Line " + (new BigInteger( tempOp.getBytes()).toString(2)) );
            raf.close();
            
            
        }catch(Exception e) {
            
        }
    }
    
    private static int doBitVerification( byte element, int position )
    {
        
        int elem = 1;
        
        boolean isOne = false;
        
        for(int i = 0 ; i < 8 ; i ++)
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
