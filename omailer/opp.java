package omailer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
public class opp {
	  String line;
	  //File file = new File("src/opp/opp.ojiis");
	  //first line - total sent
	  //second line - total failed
	  //third line - current sending or to send
	     String gotoFileLine(int n,String file) {
	    	 try (Stream<String> lines = Files.lines(Paths.get(file))) {
	              line = lines.skip(n).findFirst().get();
	          } catch (IOException e1) {
				
				e1.printStackTrace();
			}
			return line;
	     }
	     String fileFirstLine(String path) {
	    	File gf = new File(path);
	    	String result = null;
	    	try {
				Scanner rf = new Scanner(gf);
				result = rf.nextLine();
				/**while(rf.hasNext()) {
				result = rf.nextLine();
				}
				*/
				rf.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return result;
	     }
	     
	     String getFileContents(String path) {
		    	File gf = new File(path);
		    	String result = "";
		    	try {
					Scanner rf = new Scanner(gf);
				     while(rf.hasNext()) {
					result += rf.nextLine()+"\r\n";
					}
					
					rf.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return result;
		     }
	     
	 	public void writeToFile(String path,String content) {
			 FileWriter fw = null;
		        BufferedWriter bw = null;
		        PrintWriter pw = null;

		        try {
		            try {
		             	fw = new FileWriter(path, true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            bw = new BufferedWriter(fw);
		            pw = new PrintWriter(bw);
		            pw.println(content); 
		            pw.flush();

		        } finally {
		            try {
		                pw.close();
		                bw.close();
		                fw.close();
		            } catch (IOException io) {// can't do anything }
		            }
		        }

		}
}
