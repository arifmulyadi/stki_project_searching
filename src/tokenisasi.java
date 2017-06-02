
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class tokenisasi {
    public static String[][] tokens = new String[2][100000];
    public static int index;
    public void token(){
        convertword cw = new convertword();
        index=0;
        for(int i=0;i<cw.convert.length;i++){
            StringTokenizer token = new StringTokenizer(cw.convert[i]);
            while(token.hasMoreTokens()){                
                String cek =  token.nextToken().toLowerCase();
                if(cek!=null)
                    tokens[0][index] = cek; 
                tokens[1][index] = String.valueOf(i+1);
                index++;
            }
        }
    }
    public String[] token(String data){
        String[] data1 = new String[10];
        index=0;
        StringTokenizer token = new StringTokenizer(data);
        while(token.hasMoreTokens()){                
            String cek =  token.nextToken().toLowerCase();
            if(cek!=null)
                data1[index] = cek; 
            index++;
        }
        return data1;
    }
}
