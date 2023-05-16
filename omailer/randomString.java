package omailer;

import java.util.Random;

public class randomString {
   public static String  numStr(int length){
    	 Random rand = new Random(); 
			String randValue = "";
			 for(int i = 0; i < length; i++) {
				int a = rand.nextInt(9);
			 String str = String.valueOf(a);
			 randValue = randValue +str;	 
			 }
			 return randValue;
     }
}
