package omailer;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JTextField;
import javax.mail.internet.MimeBodyPart;


public class mailerClass {

	   Session newSession = null;
	    MimeMessage mimeMessage = null;
	    String subject = null;
	    String body= null;
	    String user= null;
		String pass= null;
		String host= null;
		String port= null;
		String Sendto= null;
		String senderName = null;
		  int letter_in;
		   int lead_in;
		   int currentSmtp = 0;
		   int opr = 0;
		boolean work = false;
	    opp op = new opp();  
	    File Getleads_path = new File("src/leads");
	    String[] All_lead = Getleads_path.list();
	  mailerClass() throws AddressException,MessagingException,IOException{
		
		 File leads = new File("src/leads");
		  File letters = new File("src/letters");
		  File smtps = new File("src/smtps");
		  String[] letter = letters.list();
		  String[] lead = leads.list();
		  String[] smtp = smtps.list();
		  
		 String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
		   String[] arr = sr.split(" ");
		   int letter_in = Integer.parseInt(arr[1]);
		   int lead_in = Integer.parseInt(arr[0]);
		   int currentSmtp = Integer.parseInt(arr[3]);
		   int opr = Integer.parseInt(arr[2]);
		   if(letter.length > 0 && lead.length > 0 && smtp.length > 0) {
			   work = true;	
		   }
			if(opr == 1) {
				if(letter.length > 0) {
		subject = String.valueOf(op.getFileContents("src/opp/"+letter[letter_in]));
		body = String.valueOf(op.getFileContents("src/letters/"+letter[letter_in]));
		 }
				if(lead.length > 0) {
					String leadSet = String.valueOf(op.getFileContents("src/opp/"+lead[lead_in]));
					String[] leadSetArr = leadSet.split(" ");
					int cst = Integer.parseInt(String.valueOf(leadSetArr[3]).trim());
					int t = Integer.parseInt(leadSetArr[2]);
					if(cst >= t) {
						work = false;	
					}
					String LeadPath = "src/leads/"+lead[lead_in];
					Sendto = op.gotoFileLine(cst,LeadPath);
					if(Sendto.length() < 1) {
					work = false;	
					}
					 }
				if(smtp.length > 0) {
				String smtp_setting  = String.valueOf(op.getFileContents("src/smtps/"+smtp[currentSmtp]));
				String[] smtpArr = smtp_setting.split("> ");
				user = smtpArr[0];
				 pass = smtpArr[1];
				 host = smtpArr[2];
				port = smtpArr[3];
				senderName = smtpArr[4];
				setupSeverProperties(port,user,senderName,subject,body,Sendto);
			      }
			
			
		}
	 
		

		
	}
	  
    public boolean canSendMail() {
    	return work;
    }
	public void sendMail(JTextField display) throws NoSuchProviderException {
		display.setText("Connecting to client");
		Transport trans = newSession.getTransport("smtp");
		try {
			trans.connect(host,user,pass);
		} catch (MessagingException e) {
			display.setText("Error "+e);
			e.printStackTrace();
		}
		try {
			trans.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			trans.close();
			display.setText("Message has been sent to "+Sendto);
			update_lead_opp(true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			display.setText("Error "+e);
			String FailedPath = "src/failed_leads/"+All_lead[lead_in];
		   op.writeToFile(FailedPath,Sendto);
			update_lead_opp(false);
		}
		
	
		
		
		
	}

	private void update_lead_opp(boolean status) {
		  File leads = new File("src/leads");
		  String[] lead = leads.list();
		String leadSet = String.valueOf(op.getFileContents("src/opp/"+lead[lead_in]));
		String[] leadSetArr = leadSet.split(" ");
		int cst = Integer.parseInt(String.valueOf(leadSetArr[3]).trim());
		int sent = Integer.parseInt(leadSetArr[1]);
		int failed = Integer.parseInt(leadSetArr[0]);
		cst +=1;
		 createFile copp = new createFile("src/opp/"+lead[lead_in]);
		if(status) {
			sent += 1;
			String Write = leadSetArr[0]+" "+sent+" "+leadSetArr[2]+" "+cst; 			
			copp.writeToFile(Write);	
		}else {
			failed += 1;
			String Write = failed+" "+leadSetArr[1]+" "+leadSetArr[2]+" "+cst; 			
			copp.writeToFile(Write);		
		}
	
		copp.closeFile();		
	}

	private MimeMessage setupSeverProperties(String port,String from,String senderName,String Subject,String Body,String Sendto) throws AddressException, MessagingException, UnsupportedEncodingException {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.auth", "true");
			
		prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.debug", "true");
		newSession = Session.getDefaultInstance(prop,null);
		mimeMessage = new MimeMessage(newSession); 
		mimeMessage.setHeader("Content-Type", "text/plain; charset=UTF-8");
		mimeMessage.setHeader("Content-Transfer-Encoding", "quoted-printable");   
	mimeMessage.addRecipients(Message.RecipientType.TO, Sendto);
		mimeMessage.setSubject(Subject);
		
		mimeMessage.setFrom(new InternetAddress(from,senderName));
		MimeMultipart mimempart = new MimeMultipart();
		MimeBodyPart bodypart = new MimeBodyPart();
		bodypart.setContent(Body,"text/html; charset=UTF-8");
		mimempart.addBodyPart(bodypart);
		mimeMessage.setContent(mimempart);
		return mimeMessage;
		}


}






