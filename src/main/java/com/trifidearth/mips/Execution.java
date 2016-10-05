package com.trifidearth.mips;


public class Execution
{

	public Execution() 
	{
		// TODO Auto-generated constructor stub
	}

	public Me_reg procEX(Ex_reg ex_reg) 
	{
		if(ex_reg.control==null)
			return new Me_reg(null, 0, 0, 0, ex_reg.pc);
		
		return new Me_reg (	ex_reg.control,
							ex_reg.control.runALUFunc(),
							ex_reg.writeReg,
							pc(ex_reg),	//if take branch pc
							ex_reg.pc	//else pc
							);
	}

	/**
	 * should do shift 2 (*=4 but word addressed so no worries..)
	 * @param ex_reg
	 * @return
	 */
	private int pc(Ex_reg ex_reg) 
		{return (ex_reg.signExtended);} //ex_reg.pc+  this simulation doesnt do that.. :(

}
