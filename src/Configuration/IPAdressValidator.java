package Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * Permet de savoir si une adresse IP correspond à une adresse valide 
 * @author http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
 *
 */
public class IPAdressValidator{
 
    private Pattern pattern;
    private Matcher matcher;
 
    /**
     * Regex 
     */
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
 
    /**
     * Constructor
     */
    public IPAdressValidator(){
	  pattern = Pattern.compile(IPADDRESS_PATTERN);
    }
 
   /**
    * Validate ip address with regular expression
    * @param ip ip address for validation
    * @return true valid ip address, false invalid ip address
    */
    public boolean validate(final String ip){		  
	  matcher = pattern.matcher(ip);
	  return matcher.matches();	    	    
    }
}