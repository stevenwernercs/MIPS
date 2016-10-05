package com.trifidearth.mips;


import java.util.ArrayList;

public class I_type extends Instruction
{
	public static final String [] TYPES = {"addi", "beq", "bne", "slti", "sw", "lw"};
	public static final boolean [][] OPP_BOOL = {	{false, false, true, false, false, false}, 	//addi 
													{false, false, false, true, false, false}, 	//beq
													{false, false, false, true, false, true}, 	//bne
													{false, false, true, false, true, false},	//slti
													{true, false, true, false, true, true}, 	//sw
													{true, false, false, false, true, true}};	//lw
	public static final String [] OPP_STRING = 	{"001000", "000100", "000101", "001010", "101011", "100011"};
	
	public static int iTypeCount = 0;
	public static int iTypeBubbles = 0;
	
	int code;
	int sourceS;
	int sourceT;
	int immediate;
	
	public I_type(boolean[] machineCodeInstruction) 
	{
		// TODO Auto-generated constructor stub
	}

	public I_type(int code, int sourceS, int sourceT, int immediate) 
	{
		this.code = code;
		this.sourceS = sourceS;
		this.sourceT = sourceT;
		this.immediate = immediate;
	}

	public static String is(String line, ArrayList<JumpTag> jumpTags, int insrtuctionLine)
	{
		StringBuilder machineLine = new StringBuilder();
		String [] split = line.split(",");
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
		
		int immediate = 0;
		
		if (split.length==3)
		{
			try
			{
				String  integer = split[2].substring(0, split[2].indexOf('('));
				immediate = Integer.parseInt(integer);
				String registr = split[2].substring(split[2].indexOf('(') + 1, split[2].indexOf(')'));
				String [] temp = {split[0], split[1], registr, integer};
				split = temp;
			}
			catch (Exception e){return null;}
			
		}  
		else if(split.length==4)
		{
			if((immediate = isJump(split[3], jumpTags))<0) //if not a jump, must be int
			{
				try{immediate = Integer.parseInt(split[3]);} 
				catch (Exception e){return null;} //if not int, must be error
			}
			else
			{	
				//line of branch == i
				//branch dest = immediate..
				//1
				//2brach:
				//3			-3-1=-4
				//4
				//5dest
				//6
				//TODO think this has been fixed a more delicate way
				//immediate = immediate - i -1;
			}
		}
		else
		{
			System.out.println("bad args: " + line);
			return null;
		}
		
		//ensure all are registers.... op, reg, reg, not_reg || op, reg, odd
		int regI = 0;
		for(int j = split.length -2; j > 0; j--)
		{
			for(regI = 0; regI < REG.length; regI++)
			{
				if(split[j].equalsIgnoreCase(REG[regI]))
				{	
					machineLine.append(NUM[regI]);
					break;
				}
			}
			if (regI == REG.length)
				return null;   //invalid parameter
		}
		
		 
		machineLine.append(size16(Integer.toBinaryString(immediate))).reverse();
		
		return machineLine.toString(); 
	}

	private static String size16(String binaryString) 
	{
		String zero = "0000000000000000";
		int len = binaryString.length();
		if(len>16)
			return binaryString.substring(len-16, len);
		else if (len<16)
		{
			String t = zero.substring(0, 16-len)+binaryString;
			return t;
		}
		else
			return binaryString;
	}

	private static int isJump(String title, ArrayList<JumpTag> list) 
	{
		for(JumpTag tag : list)
			if(tag.title.equals(title))
				return tag.lineIndex;
		return -1;
	}
	
	@Override
	public int runALU() 
	{	//{"addi", "beq", "bne", "slti", "sw", "lw"};
		//{"001000", "000100", "000101", "001010", "101011", "100011"};
		I_type.iTypeCount++;
		int result = 0;
		switch(this.code)
		{
			case 8:		return addi();	//add || move
			case 4:		return beq();	//sub
			case 5: 	return bne();
			case 10: 	return slti();
			case 43:	return sw();
			case 35: 	return lw();
			default:	System.exit(0);
		}
		return result;
	}
	
	private int addi() 
		{return this.sourceS+this.immediate;}

	private int beq() 
		{return ((sourceS == sourceT) ? 1 : 0);}

	private int bne() 
		{return ((sourceS != sourceT) ? 1 : 0);}

	private int slti() 
		{return (sourceS < immediate ? 1 : 0);}

	private int sw() 
		{return sourceS+immediate;}

	private int lw() 
		{return sourceS+immediate;}

	@Override
	public String print() {
		return "I_code: " + code + " sourceSValue: " + sourceS + " sourceTValue: " + sourceT + " immediate: " + immediate;
	}
}
