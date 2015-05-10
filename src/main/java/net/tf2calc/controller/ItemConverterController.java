package net.tf2calc.controller;

public class ItemConverterController {
	
	private static boolean isKeyBaseValueValid = false;
	private static boolean isFromValueValid = true;
	
	public static boolean isValidKeyValue(String argText) {
		isKeyBaseValueValid = representsValidRefinedValue(argText, false);
		if( isKeyBaseValueValid ){
			updateKeyValue(argText);
		}
		
		return isKeyBaseValueValid;
	}
	
	public static boolean isValidFromRefValue(String argText) {
		isFromValueValid = representsValidRefinedValue(argText, true);
		return isFromValueValid;
	}
		
	private static boolean representsValidRefinedValue(String argEnteredText, boolean argCanBeZero){
		double tmpTextFieldValue = 0d;		
		
		try{
			tmpTextFieldValue = Double.parseDouble( argEnteredText );
		}catch(NumberFormatException e){
			return false;
		}
		
		if(tmpTextFieldValue <= 0.0d && !argCanBeZero){
			return false;
		}
		
		if( !ItemConverterController.isValidRefinedValue(tmpTextFieldValue) ){
			return false;
		}

		return true;
	}
	
	public static String doConversion(double argEnteredValue, TF2Items argFromValue, TF2Items argToValue){
		if( !canDoConversion(argFromValue, argToValue) ){
			return "Warning: some fields have invalid values";
		}
		
		String tmpResult = convertItemToLowerLevel(argEnteredValue, argFromValue, argToValue);
		
		return tmpResult;
	}

	private static boolean canDoConversion(TF2Items argFromValue, TF2Items argToValue){
		if(isFromValueValid && (isKeyBaseValueValid ||
				(!isKeyBaseValueValid && argFromValue.compareTo(TF2Items.KEYS) != 0 && argToValue.compareTo(TF2Items.KEYS) != 0)) ){
			return true;
		}

		return false; 
	}
	
	private static String convertItemToLowerLevel(double argEnteredValue, TF2Items argFromValue, TF2Items argToValue) {
		int tmpFromWeapons = TF2Items.getItemValueInWeapons(argEnteredValue, argFromValue);
		
		if(tmpFromWeapons <= 0 && argFromValue.compareTo(argToValue) == 0){
			return "0 " + argToValue.itemName; 
		}		
		
		int tmpToWeapons = argToValue.weaponValue;
		
		if( (argFromValue.hierarchy < argToValue.hierarchy && tmpFromWeapons < tmpToWeapons) || 
			(argFromValue.hierarchy > argToValue.hierarchy && tmpFromWeapons <= 0) ){
			return "The origin value is not enough for the type you want to convert to."; 
		}
		
		String tmpResult = analyzeItemPerLevel(tmpFromWeapons, argToValue);
		
		if(argToValue.compareTo(TF2Items.REFINED) == 0){
			tmpResult += "\n\n" + convertWeaponsToRef(tmpFromWeapons) + " " + argToValue.itemName;
		}
		
		return tmpResult;
	}
	
	private static String analyzeItemPerLevel(int argBaseWeaponsValue, TF2Items argItemLevel){
		String tmpResult = "";
		
		if(argBaseWeaponsValue <= 0){
			return tmpResult;
		}
		
		double tmpToTotal = argBaseWeaponsValue / argItemLevel.weaponValue;
		
		int tmpToTotalInteger = TF2Items.getDoubleIntegerPart(tmpToTotal);
		
		if(tmpToTotalInteger > 0){		
			tmpResult = String.valueOf(tmpToTotalInteger) + " " + argItemLevel.itemName;
		}
		
		if(argItemLevel.hierarchy > 0){
			int tmpToTotalWeapons = tmpToTotalInteger * argItemLevel.weaponValue;
			
			int tmpRestWeapons = argBaseWeaponsValue - tmpToTotalWeapons;			
			
			TF2Items tmpNewDownLevelItem = TF2Items.values()[argItemLevel.hierarchy - 1]; 
			
			tmpResult += " " + analyzeItemPerLevel(tmpRestWeapons, tmpNewDownLevelItem);
		}
		
		return tmpResult;
	}
	
	public static boolean isValidRefinedValue(double argKeyValue){
		double tmpFinalDecimalValue = TF2Items.getDoubleFractionalPart(argKeyValue);
		
		double tmpWeaponValue = TF2Items.WEAPONS.refValue;
		
		double tmpRoundedDouble = TF2Items.truncateDouble(tmpFinalDecimalValue / tmpWeaponValue);
		
		double tmpResult = tmpRoundedDouble % 2;
		
		if( (tmpResult == 0.0d || tmpResult == 1.0d) && Double.compare(tmpFinalDecimalValue, 0.99d) < 0 ){
			return true;
		}
		return false;
	}
	
	private static void updateKeyValue(String argText){
		TF2Items.setKeyRefValue( TF2Items.parseStringToDouble(argText) );
	}
		
	private static double convertWeaponsToRef(int argTotalWeapons){
		double tmpToTotal = argTotalWeapons / TF2Items.REFINED.weaponValue;
		
		int tmpToTotalInteger = TF2Items.getDoubleIntegerPart(tmpToTotal);
		
		int tmpToTotalWeapons = tmpToTotalInteger * TF2Items.REFINED.weaponValue;
		
		int tmpRestWeapons = argTotalWeapons - tmpToTotalWeapons;
		
		double tmpRestRefValue = tmpRestWeapons * TF2Items.WEAPONS.refValue;
		
		double tmpResult = (double)tmpToTotalInteger + tmpRestRefValue;
		
		return tmpResult;
	}
	
}
