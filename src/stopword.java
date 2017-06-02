
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class stopword {
    public static ArrayList<String> itemsSchool = new ArrayList<String>();
    public static String[] arr;
    
    public void loadstopword() throws FileNotFoundException, IOException{
        FileInputStream fstream_school = new FileInputStream("stopw.txt"); 
        DataInputStream data_input = new DataInputStream(fstream_school); 
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input)); 
        String str_line; 

        while ((str_line = buffer.readLine()) != null) 
        { 
            str_line = str_line.trim(); 
            if ((str_line.length()!=0))  
            { 
                itemsSchool.add(str_line);
            } 
        }
        arr = (String[])itemsSchool.toArray(new String[itemsSchool.size()]);
    }
    
    public void stop(){
        convertword cw = new convertword();
        for(int i=0;i<cw.convert.length;i++){
            cw.convert[i]=cw.convert[i].replaceAll("[,.:()]*", "");
            for(int l=0;l<arr.length;l++){
                cw.convert[i]=cw.convert[i].replaceAll(" "+arr[l]+" ", " ");
            }
        }
    }
    public String stop(String data){
        for(int l=0;l<arr.length;l++){
            data=data.replaceAll(arr[l]+" ", null);
        }
        return data;
    }
}
