package algorithms.part1.unionfind;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heron Sanches
 */
public final class TxtUtils {
   
   private Scanner input;
   
   
   /**@param resourcePathFile this file has to be under /src/main/resources/, so the resourcePathFile
    * is the path under /src/main/resources/, e.g., /txt/resourcePathFile.txt, without the "/src/main/resources/".*/
   public TxtUtils(String resourcePathFile){

      try {
         input = new Scanner(Paths.get(getClass().getResource(resourcePathFile).toURI()));
      } catch (URISyntaxException | IOException ex) {
         Logger.getLogger(TxtUtils.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   }
      
   
   /**
    * @return a opened input txt file, Scanner, ready to be read. Otherwise it will return null.*/
   public Scanner getInput(){
      return input;
   }
   
   
   /**It closes the txt file input, Scanner.*/
   public void closeInput(){
      if(input != null)
         input.close();
   }
   

}