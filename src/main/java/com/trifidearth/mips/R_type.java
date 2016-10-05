package com.trifidearth.mips;


public class R_type extends Instruction {

	public static final String [] TYPES = {"move", "add", "sub", "and", "nor", "jr", "slt", "mult", "sll"};
	public static final boolean [][] OPP_BOOL = {	{true, false, false, false, false, false}, 	//move->add
													{true, false, false, false, false, false}, 	//add
													{true, false, false, false, true, false}, 	//sub
													{true, false, false, true, false, false}, 	//and
													{true, false, false, true, true, true},		//nor
													{false, false, false, false, false, false},	//jr
													{true, false, true, false, true, false},	//slt
													{false, true, true, false, false, false},//sll
													{false, false, false, false, false, false}};//sll
	public static final String [] OPP_STRING = 	{"000000", "000000", "000000", "000000", "000000", "000000", "000000", "000000", "00000000000"};
	public static final String [] FUN_STRING = 	{"100000", "100000", "100010", "100100", "100111", "001000", "101010", "011000", "000000", };
	
	public static int rTypeCount = 0;
	public static int rTypeBubbles = 0;
	
	int code;
	int source1;
	int source2;
	int shift;
	int func;
	
	public R_type(boolean[] machineCodeInstruction) {
		// TODO Auto-generated constructor stub
	}
	 
	public R_type(int code, int source1, int source2, int shift, int func) 
	{
		this.code = code;
		this.source1 = source1;
		this.source2 = source2;
		this.shift = shift;
		this.func = func;
	}

	public static String is(String line)
	{
		StringBuilder machineLine = new StringBuilder();
		String [] split = line.split(",");
		int i = 0;
		for(; i < OPP_BOOL.length; i++)
		{
			if(split[0].equalsIgnoreCase(TYPES[i]))
			{	
				machineLine.append(OPP_STRING[i]); //opCode found
				break;
			}		
		}
		//if not found
		if(i==OPP_BOOL.length)
			return null;
		
		if(i==0) //move istr -> add
		{
			if(split.length==3)
			{	
				String [] newSplit = {"add", split[1], split[2], "$zero"};
				split = newSplit;
			}
			else
				return null;
		}
		
		String shift = "00000";
		
		if(i==OPP_BOOL.length-1) //sll
		{
			if(split.length==4)
			{
				try
				{
					int shiftnumb = Integer.parseInt(split[3]);
					if(shiftnumb <0)
						return null;
					shift=Integer.toBinaryString(shiftnumb);
				}
				catch (Exception e) {return null;}
				String [] temp = {split[0], split[1], split[2]};
				split = temp;
			}
			else 
				return null;
		}
			
		//ensure all are registers....
		//String [] temp = new String[split.length];
		int regI = 0;
		for(int j = split.length-1; j > 0; j--)
		{
			for(regI = 0; regI < REG.length; regI++)
			{
				if(split[j].equalsIgnoreCase(REG[regI]))
				{	
					//temp[j] = NUM[regI];
					machineLine.append(NUM[regI]);
					break;
				}
			}
			if (regI == REG.length)
				return null;   //invalid parameter
		}
			
		
		if(split.length==2)
			if(split[0].equals("jr"))
			{
				machineLine.append("0000000000");
			}
			else
				return null;
		
		return machineLine.append(size5(shift)).append(FUN_STRING[i]).reverse().toString();
	}

	private static String size5(String binaryString) 
	{
		String zero = "00000";
		int len = binaryString.length();
		if(len>5)
			return binaryString.substring(len-5, len);
		else if (len<5)
		{
			String t = zero.substring(0, 5-len)+binaryString;
			return t;
		}
		else
			return binaryString;
	}
	
	@Override
	public int runALU() 
	{
		int result = 0;
		R_type.rTypeCount++;
		switch(this.func)
		{
			case 32:	return add();	//add || move
			case 34:	return sub();	//sub
			case 36: 	return add();
			case 39: 	return nor();
			case 24:	return mult();
			case 8:		return jr();
			case 42: 	return slt();
			case 0: 	return sll();
			default:	System.exit(0);
		}
		return result;
	}
	
	private int mult() 
		{return this.source1*this.source2;}

	private int sll() 
		{return this.source2*((int)Math.pow(2, this.shift));}

	private int jr() 
		{return this.source1;}

	private int slt() 
		{return (this.source2 < this.source1 ? 1 : 0);}

	private int nor() 
		{return ~(this.source1|this.source2);}

	private int sub() 
		{return this.source2-this.source1;}

	private int add() 
		{return this.source1+this.source2;}
	
	@Override
	public String print() {
		return "R_code: " + code + " source1Value: " + source1 + " source2Value: " + source2 + " shift: " + shift + " func: " + func;
	}
}
