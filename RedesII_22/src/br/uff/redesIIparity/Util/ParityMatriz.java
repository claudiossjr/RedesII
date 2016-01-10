package br.uff.redesIIparity.Util;

public class ParityMatriz {
    public static char[][] mat = new char[10][10];
    
    public int erroLinha;
    public int erroColuna;
    
    public static void montaMatriz(byte[] vet){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mat[i][j] = SytemHelper.getByteAsBitString(Integer.toBinaryString(vet[i+2])).charAt(j);
            }
            mat[i][8] = '|';
            mat[i][9] = SytemHelper.getByteAsBitString(Integer.toBinaryString(vet[1])).charAt(i);
        }
        for (int i = 0; i < 10; i++) {
            mat[8][i] = '-';
        }
        for (int i = 0; i < 8; i++) {
            mat[9][i] = SytemHelper.getByteAsBitString(Integer.toBinaryString(vet[0])).charAt(i);
        }
        mat[9][8] = '|';
    }
    
    public ParityMatriz(byte[] vet){
        montaMatriz(vet);
    }
    
    public char[][] getMatriz(){
        return mat;
    }
}
