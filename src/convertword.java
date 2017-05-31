
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
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
public class convertword {
    public static String[] convert = new String[30];
    public void word() {
        loadfiles lf = new loadfiles();
        for(int i=0;i<lf.listOfFiles.length;i++){
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            OOXMLParser  msofficeparser = new OOXMLParser();
            FileInputStream inputstream;
         
            try {
                inputstream = new FileInputStream(lf.listOfFiles[i]);
                msofficeparser.parse(inputstream, handler, metadata,pcontext);
            } catch (IOException | SAXException | TikaException ex) {
                System.err.println("Terjadi Kesalahan : "+ex);
                //Logger.getLogger(convertword.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            convert[i] = handler.toString();
        }
    }
}
