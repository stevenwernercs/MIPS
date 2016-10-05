package com.trifidearth.mips;


public class Me_reg 
{
	
	Control control;
	int computedValue;
	int writeReg;
	int branchPC;
	int pc;
	
	public Me_reg(Control control, int computedValue, int writeReg, int branchPC, int pc) 
	{

		this.control = control;
		this.computedValue=computedValue; 
		this.writeReg 	= writeReg;
		this.branchPC	= branchPC;
		this.pc 		= pc;
	}

	public String print()
	{
		return 	"Memory:\t\t" + /*control.print() +*/ "\tResultValue: " + this.computedValue + 
				"\t\tbranchPC: " + this.branchPC + "\t\t\t\t\twriteBackReg: " + this.writeReg +
				"\t\tpc: " + pc + "\n";
	}
}
