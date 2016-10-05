package com.trifidearth.mips;


public class Id_reg 
{
	int pc;
	int sourceRegNum1;
	int sourceRegNum2;
	int intToSignExtend;
	int writeRegNum;
	
	Control control;
	
	public Id_reg(Control control, int sourceRegNum1, int sourceRegNum2, int intToSignExtend, int writeRegNum, int pc) 
	{
		this.control			= control;
		this.sourceRegNum1 		= sourceRegNum1;
		this.sourceRegNum2	 	= sourceRegNum2;
		this.intToSignExtend 	= intToSignExtend;
		this.writeRegNum		= writeRegNum;
		this.pc 				= pc;
	}
	
	public String print()
	{
		return 	"Instruction Decode:" +
				"\tsourceRegNum1: "	 	+ this.sourceRegNum1 +
				"\tsourceRegNum2: " 	+ this.sourceRegNum2 +
				"\tintToSignExtend: "	+ this.intToSignExtend +
				"\twriteRegNum: "		+ this.writeRegNum + 
				"\t\tpc: " 				+ this.pc + "\n";
	}

}
