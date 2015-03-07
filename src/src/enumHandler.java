package src;

/**
 * This is horrible code because exceptions are expensive
 * @author Matthew Breggs
 */
public class enumHandler {

	public static Enum stringToEnum(String in){
		in = in.toUpperCase();
		try{
			CompassEnum e = CompassEnum.valueOf(in);
			return e;
		}catch(IllegalArgumentException e){}
		try{
			CharacterCreationEnum e = CharacterCreationEnum.valueOf(in);
			return e;
		}catch(IllegalArgumentException e){}
		try{
			InteractEnum e = InteractEnum.valueOf(in);
			return e;
		}catch(IllegalArgumentException e){}
		try{
			SkillEnum e = SkillEnum.valueOf(in);
			return e;
		}catch(IllegalArgumentException e){}
                try {
                    SpendSkillpointEnum e = SpendSkillpointEnum.valueOf(in);
                }catch(IllegalArgumentException e){}
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
                for(SpendSkillpointEnum e : SpendSkillpointEnum.values()){
                        enums+=e.toString()+", ";
                }
	return enums;
	}
}
