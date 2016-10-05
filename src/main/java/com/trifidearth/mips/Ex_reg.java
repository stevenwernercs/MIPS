package com.trifidearth.mips;


public class Ex_reg 
{
	int read1;
	int read2;
	int signExtended;
	int writeReg;
	Control control;
	
	int pc;
	
	public Ex_reg(Control control, int read1, int read2, int signExtended, int writeReg, int pc) 
	{
		this.control		= control;
		this.read1 			= read1;
		this.read2		 	= read2;
		this.signExtended 	= signExtended;
		this.writeReg 		= writeReg;
		this.pc 			= pc;
	}

	public String print()
	{
		return "Execute:\t" + /*control.print() +*/ "\tsource1value: " + read1 + "\t\tsource2value: " + read2 + "\t\tintToSignExted: " + signExtended +
		"\tregNumToWrite: " + writeReg + "\tpc: " + pc + "\n";
	}
}
