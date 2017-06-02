
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class search {
    public static String[] data1;
    public static String[] data2;
    public static double[][] nilai;
    
    public void pertama(String data){
        //tokenisasi------------------
        tokenisasi tk = new tokenisasi();
        data1 = tk.token(data);
        
        //stopword-------------
        int idx=0;data2 = new String[10];
        stopword sw = new stopword();
        for(int i=0;i<tk.index;i++){
            data2[idx] = sw.stop(data1[i]);
            if(data2[idx]!=null){
                idx++;
            }
        }
        
        
        //stemming-------------
        stemming st = new stemming();
        for(int i=0;i<idx;i++){
            data2[i] = st.loadstemming(data2[i]);
        }
        Arrays.sort(data2, 0, idx);
        int index=0; nilai = new double[30][3];
        frekuensi fk = new frekuensi();
        for(int i=0;i<fk.index;i++){
            if(fk.kata[i].equals(data2[index])){
                for(int j=0;j<30;j++){
                    nilai[j][0] = nilai[j][0] + fk.wbobot[i][j+1];
                    nilai[j][1] = j+1;
                    nilai[j][2]=nilai[j][2]+fk.frk[i][j+1];
                }
                if(index!=tk.index-1)
                    index++;
            }
        }
        Arrays.sort(nilai, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                return Double.compare(o2[0], o1[0]);
            }
        });
    }
}
