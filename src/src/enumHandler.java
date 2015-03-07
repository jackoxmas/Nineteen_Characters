package src;

/**
 * This is horrible code because exceptions are expensive
 * @author Matthew Breggs
 */
public class enumHandler {

	public static Key_Commands stringToEnum(String in){
		in = in.toUpperCase();
		try{
			Key_Commands e = Key_Commands.valueOf(in);
			return e;
		}catch(IllegalArgumentException e){}
		
		return null;//Didn't find any matching enums
	}
	public static String getAllEnums(){
		String enums = "";
		for(Key_Commands direction : Key_Commands.values()){
			enums+=direction.toString()+", ";
		}
	return enums;
	}
}
