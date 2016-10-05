package com.trifidearth.mips;


public class Wb_reg 
{
	int writeReg;
	int writeValue;
	int pc;
	
	public Wb_reg(int writeReg, int writeValue, int pc) 
	{
		this.writeReg	= writeReg;
		this.writeValue	= writeValue;
		this.pc			= pc;
	}

	public String print() 
	{
		return "WriteBack:\t\twriteReg: " + this.writeReg + "\twriteValue: " + this.writeValue +  "\tBranchPC: " + this.pc + "\n";
	}

}
