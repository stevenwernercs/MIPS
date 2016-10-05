package com.trifidearth.mips;


import java.util.ArrayList;

public class J_type extends Instruction 
{
	public static final String [] TYPES = {"j", "jal"};
	public static final boolean [][] OPP_BOOL = {	{false, false, false, false, true, false}, 	//j 
													{false, false, false, false, true, true}}; //jal
	public static final String [] OPP_STRING = 	{"000010", "000011"};										

	public static int jTypeCount = 0;
	public static int jTypeBubbles = 0;
	
	int opCode;
	int sourcePC;
	
	public J_type(int opCode, int sourcePC) 
	{
		this.opCode = opCode;
		this.sourcePC = sourcePC;
	}

	public static String is(String line, ArrayList<JumpTag> list)
	{
		StringBuilder machineLine = new StringBuilder();
		String [] split = line.split(",");
		if(split.length!=2)
			return null;
		int i = 0;
		for(; i < OPP_BOOL.length; i++)
		{
			if(split[0].equalsIgnoreCase(TYPES[i]))
			{	
				machineLine.append(OPP_STRING[i]);
				break;
			}		
		}
		//if not found
		if(i==OPP_BOOL.length)
			return null;
		
		int location = isJump(split[1], list);
		if(location<0)
			return null;
		
		return machineLine.append(size26(Integer.toBinaryString(location))).reverse().toString();
	}
	
	private static int isJump(String title, ArrayList<JumpTag> list) 
	{
		for(JumpTag tag : list)
			if(tag.title.equals(title))
				return tag.lineIndex;
		return -1;
	}
	
	
	private static String size26(String binaryString) 
	{
		String zero = "00000000000000000000000000";
		int len = binaryString.length();
		if(len>26)
			return binaryString.substring(len-26, len);
		else if (len<26)
		{
			String t = zero.substring(0, 26-len)+binaryString;
			return t;
		}
		else
			return binaryString;
	}

	@Override
	public int runALU() 
	{
		J_type.jTypeCount++;
		int result = 0;
		switch(this.opCode)
		{
			case 3:	return jal();
			case 2: return j();
			default:	System.exit(0);
		}
		return result;
	}
	
	private int jal() 
	{
		DataPath.clear = false;
		return 1 + sourcePC; //already has plus 1
	} 
	
	private int j()
		{return 1;}

	
	@Override
	public String print() {
		return " J ";
	}
}
