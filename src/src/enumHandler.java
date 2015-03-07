package src;

public class enumHandler {

	public static Enum stringToEnum(String in){
		in = in.toUpperCase();
		try{
			CompassEnum e = CompassEnum.valueOf(in);
			return e;
		}catch(Exception e){}
		try{
			CharacterCreationEnum e = CharacterCreationEnum.valueOf(in);
			return e;
		}catch(Exception e){}
		try{
			InteractEnum e = InteractEnum.valueOf(in);
			return e;
		}catch(Exception e){}
		try{
			SkillEnum e = SkillEnum.valueOf(in);
			return e;
		}catch(Exception e){}
		return null;//Didn't find any matching enums
	}
	public static String getAllEnums(){
		String enums = "";
		for(CompassEnum direction : CompassEnum.values()){
			enums+=direction.toString()+", ";
		}
		for( CharacterCreationEnum e : CharacterCreationEnum.values()){
			enums+=e.toString()+", ";
		}
		for( InteractEnum e : InteractEnum.values()){
			enums+=e.toString()+", ";
		}
		for(SkillEnum e : SkillEnum.values()){
			enums+=e.toString()+", ";
		}
	return enums;
	}
}
