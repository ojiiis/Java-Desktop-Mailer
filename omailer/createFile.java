package omailer;
import java.io.FileNotFoundException;
import java.util.Formatter;
public class createFile {
	
	Formatter nf;
	createFile(String fullpath){
		try {
			nf = new  Formatter(fullpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	void writeToFile(String content){
		nf.format("%s", content+"\r\n");	
	}
	void closeFile() {
		nf.close();	
	}
}
