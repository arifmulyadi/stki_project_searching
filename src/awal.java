
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.tika.exception.TikaException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class awal extends javax.swing.JFrame {

    /**
     * Creates new form awal
     */
    private static loadfiles lf = new loadfiles();
    private static convertword cw = new convertword();
    private static stopword sw = new stopword();
    private static tokenisasi tk = new tokenisasi();
    private static stemming st = new stemming();
    private static frekuensi fr = new frekuensi();
    private static DecimalFormat df = new DecimalFormat("#.##");
    public float pr[][] = new float[30][2];
    public float pr1[][] = new float[30][2];
    public int termfile;
    
    public awal() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jrgroup.add(jr1);
        jrgroup.add(jr2);
        
        file();
        cvwrd();
        stpwrd();
        tknss();
        stmmg();
        frkns();
    }
    
    public void file(){
        lf.load();
        int i=1;
        for (File file : lf.listOfFiles) {
            if (file.isFile()) {
                tafiles.append(i+". "+file.getName()+"\n");
                i++;
            }
        }
    }
    public void cvwrd(){
        cw.word();
        for(int i=0;i<cw.convert.length;i++){
            taconverter.append("-------------------------dokument : "+(i+1)+"-------------------------\n");
            taconverter.append(cw.convert[i]+"\n\n");
        }
    }
    public void stpwrd(){
        
        try {
            sw.loadstopword();
        } catch (IOException ex) {
            Logger.getLogger(awal.class.getName()).log(Level.SEVERE, null, ex);
        }
        sw.stop();
        
        for(int i=0;i<cw.convert.length;i++){
            tastopword.append("-------------------------dokument : "+(i+1)+"-------------------------\n");
            tastopword.append(cw.convert[i]+"\n\n");
        }
    }
    public void tknss(){
        tk.token();
        int cek=0;

        for(int j=0;j<lf.listOfFiles.length;j++){
            tatokenisasi.append("-------------------------dokument : "+(j+1)+"-------------------------\n");
            for(int i=0;i<tk.index;i++){
                if(tk.tokens[1][i].equals(String.valueOf(j+1))){
                    tatokenisasi.append(tk.tokens[0][i]+"\t");
                    cek++;
                }
                if(cek==5){
                    tatokenisasi.append("\n");
                    cek=0;
                }
            }
            tatokenisasi.append("\n\n");
        }
    }
    public void stmmg(){
        st.loadstemming();
        int cek=0;
        for(int i=0;i<lf.listOfFiles.length;i++){
            tastemming.append("-------------------------dokument : "+(i+1)+"-------------------------\n");
            for(Dog a: st.list){
                if(a.getDogAge()==i+1){
                    tastemming.append(a.getDogName()+"\t");
                    cek++;
                }
                if(cek==5){
                    tastemming.append("\n");
                    cek=0;
                }
            }
            tastemming.append("\n\n");
        }
    }
    public void frkns(){
        
        fr.frek();
        tafrekuensi.append("Token\tD1\tD2\tD3\tD4\tD5\tD6\tD7\tD8\tD9\tD10\tD11\tD12\tD13\tD14\tD15\tD16\tD17\tD18\tD19\tD20\tD21\tD22\tD23\tD24\tD25\tD26\tD27\tD28\tD29\tD30\tdf\tD/df\tIDF\n");
        tafrekuensi.append("=============================================================================================================================================================================="
                + "=======================================================================================================================================================================================\n");
        tabobot.append("Token\tD1\tD2\tD3\tD4\tD5\tD6\tD7\tD8\tD9\tD10\tD11\tD12\tD13\tD14\tD15\tD16\tD17\tD18\tD19\tD20\tD21\tD22\tD23\tD24\tD25\tD26\tD27\tD28\tD29\tD30\n");
        tabobot.append("=============================================================================================================================================================================="
                + "==============================================================================================================================================================================\n");
        for(int j=0;j<fr.index;j++){
            tafrekuensi.append(fr.kata[j]+"\t");
            tabobot.append(fr.kata[j]+"\t");
            for(int i=1;i<=30;i++){
                tafrekuensi.append(fr.frk[j][i]+"\t");
                tabobot.append(df.format(fr.wbobot[j][i])+"\t");
            }
            tafrekuensi.append(df.format(fr.tfidf[j][0])+"\t"+df.format(fr.tfidf[j][1])+"\t"+df.format(fr.tfidf[j][2])+"\n");
            tabobot.append("\n");
        }
    }
    public void nyareh(int query){
        tapencari.setText(null);
        search se = new search();
        se.pertama(tfcari.getText(),query);
        int termfile=0;
        for(int i=0;i<30;i++){
            if(se.nilai[i][0]>0){
                int idx = (int) Math.round(se.nilai[i][1]);
                tapencari.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                tapencari.append("ID Doc\t\t: "+idx);
                tapencari.append("\nName File\t\t: "+lf.listOfFiles[idx-1].getName());
                tapencari.append("\nTotal Kesamaan kata\t: "+(int) se.nilai[i][2]);
                tapencari.append("\nRangking\t\t: "+df.format(se.nilai[i][0]));
                tapencari.append("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                tapencari.append("\nIsi dari File\t: \n"+cw.convert[idx-1]);
                tapencari.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");
                termfile++;
            }
        }
        if(termfile>0)
            JOptionPane.showMessageDialog(null, "Data Ditemukan : "+termfile+" Dokumen\nPencarian Selesai!");
        else
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan!");
    }
    public void presiandcall(){
        tapencari.setText(null);
        tahasil.setText(null);
        repre.setText(null);
        search se = new search();
        float p1 = 0,p2 = 0,r1 = 0,r2 = 0;
        
        se.pertama(tfcari1.getText(),1);
        termfile=0;
        for(int i=0;i<30;i++){
            if(se.nilai[i][0]>0){
                termfile++;
            }
        }
        int term=0;
        tahasil.append("Query 1\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        tahasil.append("|\tP@K\t|\tR@K\t|\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        for(int i=0;i<termfile;i++){
            int idx = (int) Math.round(se.nilai[i][1]);
            repre.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            repre.append("ID Doc\t\t: "+idx);
            repre.append("\nName File\t\t: "+lf.listOfFiles[idx-1].getName());
            repre.append("\nTotal Kesamaan kata\t: "+(int) se.nilai[i][2]);
            repre.append("\nRangking\t\t: "+df.format(se.nilai[i][0]));
            repre.append("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            repre.append("\nIsi dari File\t: \n"+cw.convert[idx-1]);
            repre.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");

            int cek = JOptionPane.showConfirmDialog(null, 
                "Apakah Document ini relevant Query1","pressision & recall", JOptionPane.YES_NO_OPTION
            );
            if(cek == JOptionPane.YES_OPTION)
                term++;
            pr[i+1][0]=(float)term/(i+1);
            pr[i+1][1]=(float)(i+1)/termfile;
            p1=p1+pr[i+1][0];
            r1=r1+pr[i+1][1];
            tahasil.append("|\t"+pr[i+1][0]+"\t|\t"+pr[i+1][1]+"\t|\n");
            continue;
        }
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        p1=p1/termfile;
        r1=r1/termfile;
        tahasil.append("Average P@K : "+p1+"\t Average R@K : "+r1+"\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n\n");
        
        se.pertama(tfcari1.getText(),2);
        termfile=0;
        for(int i=0;i<30;i++){
            if(se.nilai[i][0]>0){
                termfile++;
            }
        }
        term=0;
        tahasil.append("Query 2\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        tahasil.append("|\tP@K\t|\tR@K\t|\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        for(int i=0;i<termfile;i++){
            int idx = (int) Math.round(se.nilai[i][1]);
            repre.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            repre.append("ID Doc\t\t: "+idx);
            repre.append("\nName File\t\t: "+lf.listOfFiles[idx-1].getName());
            repre.append("\nTotal Kesamaan kata\t: "+(int) se.nilai[i][2]);
            repre.append("\nRangking\t\t: "+df.format(se.nilai[i][0]));
            repre.append("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            repre.append("\nIsi dari File\t: \n"+cw.convert[idx-1]);
            repre.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");

            int cek = JOptionPane.showConfirmDialog(null, 
                "Apakah Document ini relevant Query2","pressision & recall", JOptionPane.YES_NO_OPTION
            );
            if(cek == JOptionPane.YES_OPTION)
                term++;
            pr1[i+1][0]=(float)term/(i+1);
            pr1[i+1][1]=(float)(i+1)/termfile;
            p2=p2+pr1[i+1][0];
            r2=r2+pr1[i+1][1];
            tahasil.append("|\t"+pr1[i+1][0]+"\t|\t"+pr1[i+1][1]+"\t|\n");
            continue;
        }
        tahasil.append("---------------------------------------------------------------------------------------------\n");
        p2=p2/termfile;
        r2=r2/termfile;
        tahasil.append("Average P@K : "+p2+"\t Average R@K : "+r2+"\n");
        tahasil.append("---------------------------------------------------------------------------------------------\n\n");
        tahasil.append("Average Q1&Q2 pada P@K : "+((p1+p2)/2)+"\t Average Q1&Q2 pada R@K : "+((r1+r2)/2)+"\n");
        histogram();
    }
    
    public void histogram(){
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
         "Gambar 1" ,
         "Recall" ,
         "Precision" ,
         createDatasetred(),
         PlotOrientation.VERTICAL ,
         true , true , false);
         
        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setSize(jPanel14.getWidth(), jPanel14.getHeight());
        chartPanel.setVisible(true);
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        plot.setRenderer( renderer ); 
        jPanel14.removeAll();
        jPanel14.add(chartPanel);
        jPanel14.repaint();
    }
    
    private XYDataset createDatasetred( )
   {
      final XYSeries merah = new XYSeries( "P@K" ); 
      final XYSeries hijau = new XYSeries( "R@K" ); 
      for(int i=0;i<termfile;i++){
          merah.add(pr[i+1][0], pr[i+1][1]);
          hijau.add(pr1[i+1][0], pr1[i+1][1]);
      }
    
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( merah );
      dataset.addSeries( hijau );
      return dataset;
   }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgroup2 = new javax.swing.ButtonGroup();
        jrgroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tafiles = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taconverter = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tastopword = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tatokenisasi = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tastemming = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tafrekuensi = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabobot = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tapencari = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfcari = new javax.swing.JTextField();
        jbcari = new javax.swing.JButton();
        jr1 = new javax.swing.JRadioButton();
        jr2 = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tfcari1 = new javax.swing.JTextField();
        jbcari1 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        repre = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tahasil = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tafiles.setColumns(20);
        tafiles.setRows(5);
        jScrollPane5.setViewportView(tafiles);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Files", jPanel7);

        taconverter.setColumns(20);
        taconverter.setRows(5);
        jScrollPane4.setViewportView(taconverter);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Converter", jPanel5);

        tastopword.setColumns(20);
        tastopword.setRows(5);
        jScrollPane3.setViewportView(tastopword);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Stopword", jPanel2);

        tatokenisasi.setColumns(20);
        tatokenisasi.setRows(5);
        jScrollPane2.setViewportView(tatokenisasi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tokenisasi", jPanel1);

        tastemming.setColumns(20);
        tastemming.setRows(5);
        jScrollPane1.setViewportView(tastemming);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Stemming", jPanel3);

        tafrekuensi.setColumns(20);
        tafrekuensi.setRows(5);
        jScrollPane6.setViewportView(tafrekuensi);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("TF-IDF", jPanel8);

        tabobot.setColumns(20);
        tabobot.setRows(5);
        jScrollPane7.setViewportView(tabobot);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bobot", jPanel9);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("SISTEM TEMU KEMBALI");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("SEARCHING");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(569, 569, 569)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(629, 629, 629)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tapencari.setColumns(20);
        tapencari.setRows(5);
        jScrollPane8.setViewportView(tapencari);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setText("Mesin Pencari");

        tfcari.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tfcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcariActionPerformed(evt);
            }
        });

        jbcari.setText("Cari");
        jbcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcariActionPerformed(evt);
            }
        });

        jr1.setText("Query 1");

        jr2.setText("Query 2");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel3))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbcari, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jr1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jr2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(42, 42, 42))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbcari)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jr1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jr2)))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Searching", jPanel10);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel4.setText("Recall & Presision");

        tfcari1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tfcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcari1ActionPerformed(evt);
            }
        });

        jbcari1.setText("Cari");
        jbcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcari1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(tfcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfcari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbcari1))
                .addGap(28, 28, 28))
        );

        repre.setColumns(20);
        repre.setRows(5);
        jScrollPane9.setViewportView(repre);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Presision & Recall", jPanel11);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );

        tahasil.setColumns(20);
        tahasil.setRows(5);
        jScrollPane10.setViewportView(tahasil);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 150, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Perhitungan", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane2))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcariActionPerformed
        
        if(jr1.isSelected()){
            nyareh(1);
        }
        else
            nyareh(2);        // TODO add your handling code here:
    }//GEN-LAST:event_jbcariActionPerformed

    private void tfcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcariActionPerformed
        if(jr1.isSelected()){
            nyareh(1);
        }
        else
            nyareh(2);
    }//GEN-LAST:event_tfcariActionPerformed

    private void tfcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcari1ActionPerformed
        presiandcall();       // TODO add your handling code here:
    }//GEN-LAST:event_tfcari1ActionPerformed

    private void jbcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcari1ActionPerformed
        presiandcall();     // TODO add your handling code here:
    }//GEN-LAST:event_jbcari1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(awal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(awal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(awal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(awal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new awal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton jbcari;
    private javax.swing.JButton jbcari1;
    private javax.swing.JRadioButton jr1;
    private javax.swing.JRadioButton jr2;
    private javax.swing.ButtonGroup jrgroup;
    private javax.swing.ButtonGroup rbgroup2;
    private javax.swing.JTextArea repre;
    private javax.swing.JTextArea tabobot;
    private javax.swing.JTextArea taconverter;
    private javax.swing.JTextArea tafiles;
    private javax.swing.JTextArea tafrekuensi;
    private javax.swing.JTextArea tahasil;
    private javax.swing.JTextArea tapencari;
    private javax.swing.JTextArea tastemming;
    private javax.swing.JTextArea tastopword;
    private javax.swing.JTextArea tatokenisasi;
    private javax.swing.JTextField tfcari;
    private javax.swing.JTextField tfcari1;
    // End of variables declaration//GEN-END:variables
}
