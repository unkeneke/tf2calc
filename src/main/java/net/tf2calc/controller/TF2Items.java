package net.tf2calc.controller;

import java.text.DecimalFormat;

public enum TF2Items {
	
	WEAPONS(0, 0.055d, 1, "Weapon(s)"),
	SCRAP(1, 0.11d, 2, "Scrap"),
	RECLAIMED(2, 0.33d, 6, "Reclaimed"),
	REFINED(3, 1d, 18, "Refined"),
	KEYS(4, 0d, 0, "Key(s)");
	
	public int hierarchy;
	public double refValue;
	public int  weaponValue;
	public String itemName;
	
	TF2Items(int argHierarchy, double argValue, int argWeaponValue, String argName){
		this.hierarchy = argHierarchy;
		this.refValue = argValue;
		this.weaponValue = argWeaponValue;
		this.itemName = argName;
	}
	
	public static void setKeyRefValue(double argNewValue){
		KEYS.refValue = argNewValue;
		KEYS.weaponValue = getItemValueInWeapons(argNewValue, REFINED);
	}
	
	public static int getItemValueInWeapons(double argKeyValue, TF2Items argItemType){
		int tmpRefined = getDoubleIntegerPart(argKeyValue);
		
		int tmpWeapons = tmpRefined * argItemType.weaponValue;
		
		double tmpDecimals = getDoubleFractionalPart(argKeyValue);
		
		tmpWeapons = tmpWeapons + (int)(tmpDecimals / WEAPONS.refValue);
		
		return tmpWeapons;
	}
	
	public static int getDoubleIntegerPart(double argValue){
		return (int)argValue;
	}
	
	public static double getDoubleFractionalPart(double argValue){
		double tmpExtendedDecimalValue = argValue - (long)argValue;
		
		return truncateDouble(tmpExtendedDecimalValue);
	}
	
	public static double parseStringToDouble(String argValue){
		double tmpdoubleValue = Double.parseDouble(argValue);
		
		return truncateDouble(tmpdoubleValue);
	}
	
	public static double truncateDouble(double argValue){
		String tmpFormat = new DecimalFormat("#.###").format(argValue);
		
		double tmpFinalValue = Double.valueOf(tmpFormat);
		
		return tmpFinalValue;
	}

	
}
