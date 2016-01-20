/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.redesIIparity.model;

import br.uff.redesIIparity.model.services.ReceiverMessage;
import br.uff.redesIIparity.Util.SytemHelper;
import br.uff.redesIIparity.view.panels.ConversationPanel_Receiver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claudio
 */
public class ConversationModel_Receiver {

    private final int MINELEMENTSNUMBER = 8;

    private final ReceiverMessage sender = new ReceiverMessage();

    private ConversationPanel_Receiver panel;

    public byte[] getMessage(File file) throws Exception {
        //return workFile(path);
        RandomAccessFile raf = null;
        
        //File file = new File(path);
        
        try {
            raf = new RandomAccessFile( file, "r" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConversationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            long tamanho = raf.length();
            long count = 0;
            
            byte[] vet = new byte[10];
            
            while(tamanho - count*10 > 0){
                for (int i = 0; i < 10; i++) {
                    vet[i] = raf.readByte();
                }
                Decoder(vet, file.getName());
                count++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ConversationModel_Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sender.flushMessage();
        return null;
    }

    private byte getColumnParity(byte[] messagesInBytes) {
        byte lineParity = 0;

        for (int i = 0; i < messagesInBytes.length; i++) {
            lineParity ^= messagesInBytes[i];
        }

        return lineParity;
    }

    private byte getLineParity(byte[] messagesInBytes) {
        byte columnParity = 0;

        for (int i = 0; i < messagesInBytes.length; i++) {
            columnParity += doBitVerification(messagesInBytes[i], MINELEMENTSNUMBER - (i + 1));
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
    private int doBitVerification(byte element, int position) {

        int elem = 1;

        boolean isOne = false;

        for (int i = 0; i < MINELEMENTSNUMBER; i++) {
            if ((element & elem) > 0) {
                isOne = !isOne;
            }

            elem <<= 1;
        }

        return (isOne) ? (1 << position) : 0;
    }

    public void GetPanel(ConversationPanel_Receiver panel) {
        this.panel = panel;
    }

    private boolean Correct(byte columnParity, byte lineParity, byte[] message) {
        int errosNumberLine = 0;
        int errosNumberColumn = 0;
        for (int i = 0; i < 8; i++) {
            if (SytemHelper.getByteAsBitString(Integer.toBinaryString(columnParity)).charAt(i) != SytemHelper.getByteAsBitString(Integer.toBinaryString(message[0])).charAt(i)) {
                errosNumberColumn++;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (SytemHelper.getByteAsBitString(Integer.toBinaryString(lineParity)).charAt(i) != SytemHelper.getByteAsBitString(Integer.toBinaryString(message[1])).charAt(i)) {
                errosNumberLine++;
            }
        }

        if (errosNumberColumn == 1 && errosNumberLine == 1) {
            for (int i = 0; i < 8; i++) {
                if (SytemHelper.getByteAsBitString(Integer.toBinaryString(columnParity)).charAt(i) != SytemHelper.getByteAsBitString(Integer.toBinaryString(message[0])).charAt(i)) {
                    for (int j = 0; j < 8; j++) {
                        if (SytemHelper.getByteAsBitString(Integer.toBinaryString(lineParity)).charAt(j) != SytemHelper.getByteAsBitString(Integer.toBinaryString(message[1])).charAt(j)) {
                            char[] line = SytemHelper.getByteAsBitString(Integer.toBinaryString(message[j + 2])).toCharArray();
                            if (line[i] == 0) {
                                line[i] = 1;
                            } else {
                                line[i] = 0;
                            }
                            message[j] = new String(line).getBytes(StandardCharsets.UTF_8)[0];
                            return true;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean Decoder(byte [] vet , String fileName) throws Exception {

        if (vet.length % 10 != 0) {
            return false;
        }

        int indice = 0;
        //Each block is a sequence of ten byte
        boolean hasBlock = true;
        byte[] tempVet = new byte[10];
        byte[] justMessage = new byte[8];

        while (hasBlock) {
            try {
                //Get ten byte and put in a variable
                for (int i = indice; i < indice + 10; i++) {
                    tempVet[i % 10] = vet[i];
                    if (i % 10 > 1) {
                        //Get just the Message to calculate the parity bytes
                        justMessage[i % 10 - 2] = vet[i];
                    }
                }
                indice += 10;
            } catch (Exception ex) {
                //If has a IndexOutOfBoundException is because haven't anything to read
                hasBlock = false;
            }
            if (hasBlock) {
                //Calculate the line parity
                byte lineParity = getLineParity(justMessage);
                //Calculate the column parity
                byte columnParity = getColumnParity(justMessage);

                /*ParityMatriz mat = new ParityMatriz(tempVet);
                 panel.showParityMatriz(mat);
                 if(true){
                 return true;
                 }*/
                //if the calculated are different from the received
                if (columnParity == tempVet[0] && lineParity == tempVet[1]) {
                    //Write the message without the parity bytes
                    boolean wasSent = sender.sendMessage(justMessage, fileName);

                    if (!wasSent) {
                        throw new Exception("Message was not sent");
                    }

                } else {
                    //Write error message
                    String message = "Erro no bloco " + indice % 10;
                    panel.showMessage(message);

                    //if successful in the block correction, copy just the message and write to the file
                    if (Correct(columnParity, lineParity, tempVet)) {
                        for (int i = 2; i < 10; i++) {
                            justMessage[i] = tempVet[i];
                        }
                        boolean wasSent = sender.sendMessage(justMessage, fileName);

                        if (!wasSent) {
                            throw new Exception("Message was not sent");
                        }
                    } else {
                        //Show a message and stop
                        message = message + "\n" + "Não foi possivel corrigir o erro";
                        panel.showMessage(message);

                        return false;
                    }
                }
            }
            panel.showMessage("Arquivo recebido com sucesso");
        }

        return false;
    }

}
