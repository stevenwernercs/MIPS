package com.trifidearth.mips;


public class Memory{

	int [] stack;
	
	public Memory(int[] stack) {
		// TODO Auto-generated constructor stub
		if (stack!=null)
			this.stack = stack;
		else	//256/4 = 64
			stack = new int [65];  //stack = new boolean [256][32];  //empty stack!
	}

	public Wb_reg procME(Me_reg me_reg) 
	{
		if(me_reg.control==null)
			return null;
		if(me_reg.control.store())
		{
			int memAddress = me_reg.computedValue / 4; //all must use words!
			stack[memAddress] = me_reg.writeReg;
			return null;
		}
		else if(me_reg.control.laod())
		{
			int memAddress = me_reg.computedValue / 4; //all must use words!
			me_reg.computedValue = stack[memAddress];
			return new Wb_reg(me_reg.writeReg, me_reg.computedValue, -1);
		}
		else if(me_reg.control.branch() && me_reg.computedValue==1)
			return new Wb_reg(0, 0, me_reg.branchPC);
		else if(me_reg.control.branch() && me_reg.computedValue==0)
			return null;
		else if(me_reg.control.jal())
			return new Wb_reg(me_reg.writeReg, me_reg.computedValue, -1);
		return new Wb_reg(me_reg.writeReg, me_reg.computedValue, -1);
	}

}
