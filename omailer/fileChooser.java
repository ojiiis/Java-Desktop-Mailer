package omailer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class fileChooser {
	JFileChooser jfc = new JFileChooser();
        
  void  chooser(String type){
	  FileNameExtensionFilter filter;
	  String path;
	  String fileName;
	  switch(type) {
	  case "letter":
	       filter = new FileNameExtensionFilter("html","txt");
	    	jfc.setFileFilter(filter);  
	    	path = "src/letters/";   	
	    	fileName = randomString.numStr(25)+".html";
	  break;
	  case "lead":
		     filter = new FileNameExtensionFilter("txt","txt");
		    	jfc.setFileFilter(filter);  
		    	path = "src/leads/";
		    	fileName = randomString.numStr(25)+".txt";
	 break;
	  default:
	     filter = new FileNameExtensionFilter("txt","txt");
	    	jfc.setFileFilter(filter);
	    	 path = "src/settings/";
	    	 fileName = randomString.numStr(25)+".txt";
	    	break;
	    	
	  }

    		   int returnVal = jfc.showOpenDialog(jfc);
    		   if (returnVal == JFileChooser.APPROVE_OPTION) {
    	           File file = jfc.getSelectedFile();
    	         //  System.out.println(file.getName());
    	           //This is where a real application would open the file.
    	           try {
    				Scanner sc = new Scanner(file);
    				
    				createFile cf = new createFile(path+fileName);
    				int t = 0;
    				while(sc.hasNextLine()) {
    				cf.writeToFile(sc.nextLine());
    		     	t++;
    				}
    				sc.close();
    				cf.closeFile();
    				if(type == "lead") {
    					String Write = "0 0 "+t+" 0"; 					
    			createFile copp = new createFile("src/opp/"+fileName);
    			copp.writeToFile(Write);
    			copp.closeFile();	
    				
    				}else if(type == "letter") {
    				    String Write = "Letter Subject - "+randomString.numStr(5); 					
        	    	    createFile copp = new createFile("src/opp/"+fileName);
        	    	   copp.writeToFile(Write);
        	    		copp.closeFile();		
    				}
    			} catch (FileNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	           
    	           
    	       } else {
    	          
    	       }
    		   
    }
}
