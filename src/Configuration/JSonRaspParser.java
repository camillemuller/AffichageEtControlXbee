package Configuration;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSonRaspParser {

	static String avant = "";
	static String arriere = "";

	public static List<String> getParamsDistanceArriereAvant(String userInput)
	{
		List<String> lesParams = new ArrayList<String>();
		

		JSONParser parser = new JSONParser();
		JSONParser parserArr = new JSONParser();

		KeyFinder finderAvant = new KeyFinder();
		KeyFinder finderArriere = new KeyFinder();


		finderAvant.setMatchKey("distance_avant");
		try{
			while(!finderAvant.isEnd()){
				parser.parse(userInput, finderAvant, true);
				if(finderAvant.isFound()){
					finderAvant.setFound(false);
					avant =  Long.toString((long) finderAvant.getValue());
				}
			}           
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}
		finderArriere.setMatchKey("distance_arriere");
		try{
			while(!finderArriere.isEnd()){
				parserArr.parse(userInput, finderArriere, true);
				if(finderArriere.isFound()){
					finderArriere.setFound(false);
					arriere =Long.toString((long) finderArriere.getValue());
				}
			}           
		}
		catch(ParseException pe){
			pe.printStackTrace();
		}
		
		lesParams.add(avant);
		lesParams.add(arriere);
		
		return lesParams;
	}
	
	public static String getAvant()
	{
		return avant;
	}
	
	public static String getArriere()
	{
		return arriere;
	}
}
