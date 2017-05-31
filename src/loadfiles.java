
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arifztenk
 */
public class loadfiles {
    public File folder;
    public static File[] listOfFiles;
    
    public void load(){
        folder = new File("List");
        listOfFiles = folder.listFiles();
    }
    
        
}
