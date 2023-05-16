package omailer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;


public class frame implements ActionListener{
  
	int it = 0;
	int cTotal = 0;
	int cSent = 0;
	int cFailed = 0;
	int current_Letter = 0;
	int current_Lead = 0;
	boolean oldProcess = false;
	JFrame frame = new JFrame();
	//JFrame frame2 = new JFrame();
	JTextField total = new JTextField();
	JLabel totalLabel = new JLabel();
	
	JTextField sent = new JTextField();
	JLabel sentLabel = new JLabel();
	
	JTextField failed = new JTextField();
	JLabel failedLabel = new JLabel();
	
	JTextArea leadArea = new JTextArea();
	
	JButton addLeads = new JButton("Add Leads");
	JButton deleteLeads = new JButton("Delete Current Selection");
	JButton deleteLetter = new JButton("Delete Current Selection");
	JButton Start = new JButton("Start");
	JButton Stop = new JButton("Stop");
	JButton deleteSmtp = new JButton("Delete Current Selection");
	JButton addLetter = new JButton("Add Letters");
	JButton subjectUpdate = new JButton("Update");
	JLabel opLebel = new JLabel();
	JLabel leadsLebal = new JLabel();
	JLabel letterLebal = new JLabel();
	
	JLabel subjectLebal = new JLabel();
	JTextField subject = new JTextField();
	
	final JComboBox<String> letterList ;
	final JComboBox<String> leadList ;
	final JComboBox<String> smtpList;
	
	JLabel userName = new JLabel();
	JLabel password = new JLabel();
	JLabel host = new JLabel();
	JLabel port = new JLabel();
	JLabel senderNameLabel = new JLabel();
	JTextField userNameInput = new JTextField();
	JTextField passwordInput = new JTextField();
	JTextField hostInput = new JTextField();
	JTextField portInput = new JTextField();
	JTextField senderName = new JTextField();
	
	JButton addSmtp = new JButton("Add Smtp Email");
	JEditorPane web = new JEditorPane();
	JTextField display = new JTextField();
	JLabel intervalLabel = new JLabel();
	final JComboBox<String> interval;
	int TimerInterval;
	//system.out.println(interval.sele);
	//TimerInterval
    Timer timer = new Timer(TimerInterval, new ActionListener() {

	   		public void actionPerformed(ActionEvent e) {
	   			try {
	   				mailerClass mail = new mailerClass();
	   				if(mail.canSendMail()) {
	   					display.setText("Mailer is working...");
	   					mail.sendMail(display);
	   					updateSent();
	   					updateFailed();
	   				}else {
	   					display.setText("Unable to send, Check Smtp,Letter,Leads and Network.");
	   					timer.stop();
	   				}
					
				} catch (MessagingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	   			
	   		}
	       	   
	          });
    opp op = new opp(); 
   
    File leads = new File("src/leads");
	  File letters = new File("src/letters");
	  File smtps = new File("src/smtps");
	  String[] letter = letters.list();
	  String[] lead = leads.list();
	  String[] smtp = smtps.list();
	     frame(){
	    //	 int getTimer = Integer.parseInt(); 
	    	 TimerInterval = 10000;
	   leadList = new JComboBox<String>(lead);
	  leadList.addActionListener(this);
	  leadList.setBounds(25,200,400,25);	
	  
	  letterList = new JComboBox<String>(letter);
	  letterList.addActionListener(this);

     
	  
	  totalLabel.setBounds(25, 10, 250, 30);
	  totalLabel.setText("Total Leads");
	  
	  total.setBounds(25, 40, 250, 30);
	  total.setFont(new Font("arial",Font.BOLD,25));
	  total.setEditable(false);
	  total.setHorizontalAlignment(JTextField.CENTER);
	  
	  failedLabel.setBounds(300, 10, 250, 30);
	  failedLabel.setText("Total Failed");
	  
	  failed.setBounds(300, 40, 250, 30);
	  failed.setFont(new Font("arial",Font.BOLD,25));
	  failed.setEditable(false);
	  failed.setHorizontalAlignment(JTextField.CENTER);
	  
	  
	  sentLabel.setBounds(570, 10, 250, 30);
	  sentLabel.setText("Total Sent");
	  
	  sent.setBounds(570, 40, 250, 30);
	  sent.setFont(new Font("arial",Font.BOLD,25));
	  sent.setEditable(false);
	  sent.setHorizontalAlignment(JTextField.CENTER);
	  
	  
	    letterLebal.setText("Select letter template for this operation");
	    letterLebal.setBounds(25,85,400,25);
	    
	    letterList.setBounds(25,110,400,25);
	    addLetter.setText("Add Email Letter");
	    addLetter.addActionListener(this);
	    addLetter.setBounds(25,140,180,25);
	    deleteLetter.setBounds(220,140,180,25);
	    deleteLetter.addActionListener(this);
	    
	    leadsLebal.setText("Select leads file for this operation");
	    leadsLebal.setBounds(25,175,400,25);
	    
	    addLeads.setText("Add Leads");
	    addLeads.addActionListener(this);
	    addLeads.setBounds(25,230,180,25);
	    
	    deleteLeads.addActionListener(this);
	    deleteLeads.setBounds(220,230,180,25);
	    
	   
       
		subjectLebal.setBounds(450,110,400,20);
		subjectLebal.setText("Subject");
		
		subject.setBounds(450,130,300,30);
		 subjectUpdate.addActionListener(this);
		 subjectUpdate.setBounds(760,130,100,30);
		 
		leadArea.setBounds(450,400,400,200);
		
		
		if(lead.length > 0) {
			 String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
			   String[] arr = sr.split(" ");
			int in = Integer.parseInt(arr[0]);
			int opr = Integer.parseInt(arr[2]);
			if(opr == 1) {
		leadArea.setText(String.valueOf(op.getFileContents("src/leads/"+lead[in])));
		leadList.setSelectedIndex(in);
		}else {
			leadArea.setText(String.valueOf(op.getFileContents("src/leads/"+lead[0])));
			leadList.setSelectedIndex(0);	
		}
		}
		
		web.setText("");
		
	    web.setEditable(false);
	    HTMLEditorKit editorKit = new HTMLEditorKit();
	    web.setEditorKit(editorKit);
	    web.setSize(100,web.getHeight());
	    web.setMinimumSize(new Dimension(100, web.getHeight()) );
	   web.setMaximumSize(new Dimension(100, web.getHeight()) );
	    web.setContentType("text/html");
	      
		  JScrollPane sp = new JScrollPane(web); 
		
		sp.setBounds(450,170,400,220);
		
		opLebel.setText("SMTPS");
		opLebel.setBounds(25,250,450,50);
		
		 smtpList = new JComboBox<String>(smtp);	
		
		 if(smtp.length > 1)
		    {
		    	 String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
		    	  String[] settnArr = settings.split(" ");	
		    	  int i = Integer.parseInt(settnArr[3]);
		    	  smtpList.setSelectedIndex(i);
		    	  
		    }
		 smtpList.addActionListener(this);
		 smtpList.setBounds(25,290,400,25);	
		
		 deleteSmtp.addActionListener(this);
		 deleteSmtp.setBounds(25,320,200,25);	
		
		 JPanel smtpPanel = new JPanel();
		 smtpPanel.setLayout(new GridLayout(6,2,0,10));
		// smtpPanel.setBackground(Color.BLACK);
		 smtpPanel.setBounds(25,360,400,240);
		 //.
		 senderNameLabel.setText("SMTP Senders Name:");
		 userName.setText("SMTP Username:");
		 password.setText("SMTP Password:");
		 host.setText("SMTP Host:");
		 port.setText("SMTP Port:");
		 
		 addSmtp.addActionListener(this);
		 String[] intervalList = {"10","15","20","25","30","35","40","45","50","55","60"};
		 interval = new JComboBox<String>(intervalList);
		 intervalLabel.setText("Select sending interval (Seconds)");
		 
		 intervalLabel.setBounds(25,590,200,40);
		 interval.setBounds(25,620,200,20);
		 interval.addActionListener(this);
		 
		 display.setEditable(false);
		 display.setBounds(250,610,350,40);
		 display.setBackground(Color.black);
		 display.setForeground(Color.white);
		 
		  Start.setText("Start");
	      Start.addActionListener(this);
		  Start.setBounds(640,610,100,40);
		  Start.setBackground(Color.green);
		  Start.setForeground(Color.white);
		  Start.setFont(new Font("Arial",Font.BOLD,25));
		  
		  Stop.setText("Stop");
		  Stop.addActionListener(this);
		  Stop.setBounds(750,610,100,40);
		  Stop.setBackground(Color.red);
		  Stop.setForeground(Color.white);
		  Stop.setFont(new Font("Arial",Font.BOLD,25));
		 smtpPanel.add(senderNameLabel); 
		 smtpPanel.add(senderName); 
		 smtpPanel.add(userName);
		 smtpPanel.add(userNameInput);
		 smtpPanel.add(password);
		 smtpPanel.add(passwordInput);
		 smtpPanel.add(host);
		 smtpPanel.add(hostInput);
		 smtpPanel.add(port);
		 smtpPanel.add(portInput);
		 smtpPanel.add(addSmtp);
		frame.add(smtpPanel); 
		frame.add(subjectLebal);
		frame.add(subject);
		frame.add(subjectUpdate);
		frame.add(sp);
		frame.add(sentLabel);
		frame.add(sent);
		frame.add(failedLabel);
		frame.add(failed);
		frame.add(totalLabel);
		frame.add(total);
		frame.add(leadArea);
		frame.add(letterLebal);
		frame.add(letterList);
		frame.add(addLetter);
		frame.add(deleteLetter);
        frame.add(leadsLebal);
        frame.add(addLeads);
        frame.add(deleteLeads);
		frame.add(leadList);
		frame.add(opLebel);
		frame.add(smtpList);
		frame.add(deleteSmtp);
		frame.add(intervalLabel);
		frame.add(interval);
		frame.add(display);
		frame.add(Start);
		frame.add(Stop);
		frame.setTitle("Ojiiis Mailer Beta V.1.0 [https://nigeriansonly.com/free-mailer]");
		ImageIcon imc = new  ImageIcon("src/user_settings/logo-black.png");
		frame.setIconImage(imc.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,700);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		updateTotal();	
		updateSent();
		updateFailed();

		 if(letter.length > 0) {
			  String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
			   String[] arr = sr.split(" ");
			   int in = Integer.parseInt(arr[1]);
			   int opr = Integer.parseInt(arr[2]);
				if(opr == 1) {
			loadLetterFile("src/letters/"+letter[in]);
			letterList.setSelectedIndex(in);
			subject.setText(String.valueOf(op.getFileContents("src/opp/"+letter[in])));
			 }else {
				loadLetterFile("src/letters/"+letter[0]);
				letterList.setSelectedIndex(0);
				subject.setText(String.valueOf(op.getFileContents("src/opp/"+letter[0])));
				
			}
			
		}
		
		
	}
      
	 
      public void repaint() {
    	  frame.repaint();
      }
      public void updateTotal() {
    	  String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
    	    String[] settnArr = settings.split(" ");

    		 if(lead.length > 0) {
    			 int a = Integer.parseInt(settnArr[0]);
    			 int o = Integer.parseInt(settnArr[2]);
    			 if(o == 1) {
    				 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[a]));
    			  	   String[] arr = sr.split(" ");
    			  	 cTotal = Integer.parseInt(arr[2]);
    			  	total.setText(String.valueOf(cTotal)); 
    			 }else {
    			 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[0]));
    	  	   String[] arr = sr.split(" ");
    	  	 cTotal = Integer.parseInt(arr[2]);
    	  	total.setText(String.valueOf(cTotal));
    			 }
    		 //0-currentlead 1-currentletter 2-operation 3-smtp
    		//total.setText("ad");    
    		 }
      }
     
      
      public void updateSent() {
    	  String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
    	    String[] settnArr = settings.split(" ");

    		 if(lead.length > 0) {
    			 int a = Integer.parseInt(settnArr[0]);
    			 int o = Integer.parseInt(settnArr[2]);
    			 if(o == 1) {
    				 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[a]));
    			  	   String[] arr = sr.split(" ");
    			  	 cSent = Integer.parseInt(arr[1]);
    			  	sent.setText(String.valueOf(cSent)); 
    			 }else {
    			 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[0]));
    	  	   String[] arr = sr.split(" ");
    	  	 cSent = Integer.parseInt(arr[1]);
    	  	sent.setText(String.valueOf(cSent));
    			 }
    		 }
      } 
      
      public void updateFailed() {
    	  String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
    	    String[] settnArr = settings.split(" ");

    		 if(lead.length > 0) {
    			 int a = Integer.parseInt(settnArr[0]);
    			 int o = Integer.parseInt(settnArr[2]);
    			 if(o == 1) {
    				 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[a]));
    			  	   String[] arr = sr.split(" ");
    			  	 cFailed = Integer.parseInt(arr[0]);
    			  	failed.setText(String.valueOf(cFailed)); 
    			 }else {
    			 String sr = String.valueOf(op.fileFirstLine("src/opp/"+lead[0]));
    	  	   String[] arr = sr.split(" ");
    	  	 cFailed = Integer.parseInt(arr[0]);
    	  	failed.setText(String.valueOf(cFailed));
    			 }
    		 }
      } 
      public  void resetLeadPointer() {
    	  String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
  	    String[] settnArr = settings.split(" ");
  	    String Write = "0 "+settnArr[1]+" "+0+" 0"; 					
  	    createFile copp = new createFile("src/user_settings/operation.txt");
  		copp.writeToFile(Write);
  		copp.closeFile();
      }
      public void resetLetterPointer() {
    	  String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
    	    String[] settnArr = settings.split(" ");
    	    String Write = settnArr[1]+" 0 0 0"; 					
    	    createFile copp = new createFile("src/user_settings/operation.txt");
    		copp.writeToFile(Write);
    		copp.closeFile();  
      }
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println(e);
		if(e.getSource() == leadList) 
		{
			
			 File leads = new File("src/leads");
			  String[] lead = leads.list();
			 leadArea.setText(String.valueOf(op.getFileContents("src/leads/"+lead[leadList.getSelectedIndex()])));
			 current_Lead = leadList.getSelectedIndex();
		 
			   String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
			   String[] arr = sr.split(" ");
			    String a = arr[1];
			    String Write = leadList.getSelectedIndex()+" "+a+" "+1+" "+arr[3];
			    createFile copp = new createFile("src/user_settings/operation.txt");
				copp.writeToFile(Write);
				copp.closeFile();
				updateTotal();	
				updateSent();
				updateFailed();
		}
		
		   if(e.getSource() == addLeads) {
			   File Oldleads = new File("src/leads");
			   String[] oldlead = Oldleads.list();
			   fileChooser ch =	new  fileChooser();
		        ch.chooser("lead");
		       
try {
	File leads = new File("src/leads");
	   String[] lead = leads.list();
	 if(lead.length > oldlead.length) {
	  int newLead = lead.length - 1;
	 leadArea.setText(String.valueOf(op.getFileContents("src/leads/"+lead[newLead])));
	
	 String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
	   String[] arr = sr.split(" ");
	    String Write = newLead+" "+arr[1]+" "+1+" "+arr[3];
	    createFile copp = new createFile("src/user_settings/operation.txt");
		copp.writeToFile(Write);
		copp.closeFile();
		
    frame.dispose(); 
    new frame();
    }
}catch(Exception ee) {
	
}	 
				}
		   if(e.getSource() == deleteLeads) {
				  try {
					  File leads = new File("src/leads");
					  if(leads.list().length > 0) {
					   String path = leads.list()[leadList.getSelectedIndex()];
					   File toDelete = new File("src/leads/"+path);
					   File toDelete2 = new File("src/opp/"+path);
					  if(toDelete.delete() && toDelete2.delete()) {
						  resetLeadPointer();
						  frame.dispose(); 
					        new frame();  
					  }else {
						  System.out.println("unable to delete file");
					  } 
				  }
				  } catch(Exception g) {
					  System.out.println(g); 
				  }
				  
					}
		   
		   
			if(e.getSource() == letterList) 
			{
				  File letters = new File("src/letters");
				  String[] letter = letters.list();
				  current_Letter = letterList.getSelectedIndex();
			 
				   String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
				   String[] arr = sr.split(" ");
		
				    String Write = arr[0]+" "+letterList.getSelectedIndex()+" 1 "+arr[3];
				    createFile copp = new createFile("src/user_settings/operation.txt");
					copp.writeToFile(Write);
					copp.closeFile();
					loadLetterFile("src/letters/"+letter[letterList.getSelectedIndex()]);
	
			} 
		   
          if(e.getSource() == addLetter) {
        fileChooser ch =	new  fileChooser();
        ch.chooser("letter");
        
  	    File letters = new File("src/letters");
  	   String[] letter = letters.list();
  	  
        int current_letter = letter.length - 1;
        String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
  	    String[] settnArr = settings.split(" ");
  	    String Write = settnArr[0]+" "+String.valueOf(current_letter)+" 0 "+settnArr[3]; 					
  	    createFile copp = new createFile("src/user_settings/operation.txt");
  		copp.writeToFile(Write);
  		copp.closeFile();  
  		try {
  			loadLetterFile("src/letters/"+letter[current_letter]);
  			subject.setText(String.valueOf(op.getFileContents("src/opp/"+letter[current_letter])));
  			 frame.dispose(); 
  	        new frame();
  		}catch(Exception ee) {
  			
  		}
  		
      
			}
          
	   if(e.getSource() == deleteLetter) {
		  try {
			  File letters = new File("src/letters");
			  String[] letterListArr = letters.list();
			  if(letterListArr.length > 0) {
			   String path = letterListArr[letterList.getSelectedIndex()];
			   File LetterToDelete = new File("src/letters/"+path);
			   File toDelete2 = new File("src/opp/"+path);
			  if(LetterToDelete.delete() && toDelete2.delete()) {
				  resetLetterPointer();
				  frame.dispose(); 
			        new frame();  
			  }else {
				  System.out.println("unable to delete file");
			  }  
			  }
		  } catch(Exception g) {
			  System.out.println(g); 
		  }
		  
			}
	   if(e.getSource() == subjectUpdate) 
	   {
			 if(letter.length > 0) {
				 String sr = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
				   String[] arr = sr.split(" ");
				   int in = Integer.parseInt(arr[1]);
				   int opr = Integer.parseInt(arr[2]);
					if(opr == 1) {
				 createFile copp = new createFile("src/opp/"+letter[in]);
  	    	     copp.writeToFile(subject.getText());
  	    		 copp.closeFile();		
				 }else {
					 createFile copp = new createFile("src/opp/"+letter[0]);
	  	    	     copp.writeToFile(subject.getText());
	  	    		 copp.closeFile();	
					
				}
				
			}
		   
	   }
	   if(e.getSource() == addSmtp) {
		   String email = userNameInput.getText();
		   String password = passwordInput.getText();
		   String host = hostInput.getText();
		   String port = portInput.getText();
		   String sendersName = senderName.getText();
		   String addSenderName;
		   if(email.length() > 0 && password.length() > 0 && host.length() > 0 && port.length() > 0 ) {
			   addSenderName =  email.split("@")[0];
			   if(sendersName != "" && sendersName.length() > 0 ) {
				   addSenderName =  sendersName;  
			   }
			  String SMTP = email+"> "+password+"> "+host+"> "+port+"> "+addSenderName;
			   createFile copp = new createFile("src/smtps/["+addSenderName+"] "+email+".smtp");
			   copp.writeToFile(SMTP);
			   copp.closeFile();  
			   File oldSmtp = new File("src/smtps");
				  String[] oldSmtps = oldSmtp.list();
				 int CurrentIndex = oldSmtps.length  - 1;
	              if(oldSmtps.length > 1){
			        String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
			  	    String[] settnArr = settings.split(" ");
			  	    String Write = settnArr[0]+" "+settnArr[1]+" "+settnArr[2]+" "+CurrentIndex; 					
			  	    createFile copp1 = new createFile("src/user_settings/operation.txt");
			  	  copp1.writeToFile(Write);
			  	copp1.closeFile(); 
	              }
	              frame.dispose(); 
			        new frame();	 
		   }
		   
	   }
  
	   
	   if(e.getSource() == deleteSmtp) {
		  int toDeleteIndex = smtpList.getSelectedIndex(); 
		  File all_smtps = new File("src/smtps");
		  String[] smtpArr = all_smtps.list();
		  if(smtpArr.length > 0) {
		   String path = smtpArr[toDeleteIndex];
		   File smtpToDelete = new File("src/smtps/"+path);
		   
		 
		   String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
	  	    String[] settnArr = settings.split(" ");
	  	    int SettingIndex = Integer.parseInt(settnArr[3]);
	  	   
		  if(smtpToDelete.delete()) {
			  if(SettingIndex != 0 && SettingIndex == toDeleteIndex) {
			String Write = settnArr[0]+" "+settnArr[1]+" "+settnArr[2]+" 0"; 					
		   createFile copp1 = new createFile("src/user_settings/operation.txt");
		   copp1.writeToFile(Write);
			copp1.closeFile();  
			  	 }
			  frame.dispose(); 
		        new frame();  
		  }else {
			  System.out.println("unable to delete file");
		  }  
		  }
		  
	   }
	   
	   if(e.getSource() == smtpList) {
		   int selectedIndex = smtpList.getSelectedIndex();  
		   String settings = String.valueOf(op.fileFirstLine("src/user_settings/operation.txt"));
	  	    String[] settnArr = settings.split(" ");
		   String Write = settnArr[0]+" "+settnArr[1]+" "+settnArr[2]+" "+selectedIndex; 					
		   createFile copp1 = new createFile("src/user_settings/operation.txt");
		   copp1.writeToFile(Write);
			copp1.closeFile();  
	   }
	   if(e.getSource() == interval) {
		   int selected = Integer.parseInt(String.valueOf(interval.getSelectedItem()));  
		   TimerInterval = selected * 1000;
	   }
	   if(e.getSource() == Start) {
		   display.setText("Working...");
		   timer.start();
	   }
	   if(e.getSource() == Stop) {
		   timer.stop();
		   display.setText("");
	   }
		}

		private void loadLetterFile(String path) {
		String html = String.valueOf(op.getFileContents(path));
			 web.setText(html);
		}
	    
	    
	
}


