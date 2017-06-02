/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class frekuensi {
    public static String[] kata =  new String[10000];
    public static int[][] frk = new int[10000][31];
    public static double[][] tfidf = new double[10000][3];
    public static double[][] wbobot = new double[10000][31];
    public static int index;
    public void frek(){
        loadfiles lf = new loadfiles();
        stemming st = new stemming();
        index=0;
        int cek=-1,cek1=-1;
        for(Dog a: st.list){
            for(int i=0;i<index;i++){
                if(a.getDogName().equals(kata[i])){
                    cek=i;
                    cek1=a.getDogAge();
                }
            }
            if(cek==-1){
                kata[index]=a.getDogName();
                frk[index][a.getDogAge()]=1;
                index++;
            }
            else{
                frk[cek][cek1]++;
            }
            cek=-1;
            cek1=-1;
        }
        for(int i=0;i<index;i++){
            for(int j=1;j<=30;j++){
                if(frk[i][j]>0){
                    tfidf[i][0]++;
                }
            }
            tfidf[i][1]=30/tfidf[i][0];
            tfidf[i][2]=Math.log(tfidf[i][1]);
            for(int j=1;j<=30;j++){
                if(frk[i][j]>0){
                    wbobot[i][j]=frk[i][j]*tfidf[i][2];
                }
                else
                    wbobot[i][j]=0;
            }
        }
    }
}
