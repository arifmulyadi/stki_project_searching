
import IndonesianStemmer.IndonesianStemmer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class stemming {
    public static java.util.List<Dog> list = new ArrayList<Dog>();
    public void loadstemming(){
        tokenisasi ts = new tokenisasi();
        IndonesianStemmer stemmer = new IndonesianStemmer();
        for(int i=0;i<ts.index;i++){
            String cek = stemmer.findRootWord(ts.tokens[0][i]);
            if(cek==null){
                cek=ts.tokens[0][i];
            }
            list.add(new Dog(cek, Integer.parseInt(ts.tokens[1][i])));
        }
        Collections.sort(list);
    }
    public String loadstemming(String data){
        IndonesianStemmer stemmer = new IndonesianStemmer();
        String cek = stemmer.findRootWord(data);
        if(cek==null)
            cek = data;
        return cek;
    }
}
