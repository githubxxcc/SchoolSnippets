package mini.practices;

import java.util.Arrays;
import java.util.regex.Pattern;

public class test {
	
	public static void main(String[] args) {
		String a = "((3+5)*62)";
	    
	    
	    String[] b = a.split("(?<=\\D)|(?=\\D)");
	    System.out.println(Arrays.toString(b));	
	}
	
    
    
    
}
