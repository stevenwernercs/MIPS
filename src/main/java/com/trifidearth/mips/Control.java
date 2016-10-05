package com.trifidearth.mips;



public class Control 
{
	Instruction instr;
	int source1;
	int source2;
	int code;
	int shift;
	int func;
	
	public Control(int code, int source1, int source2, int shift, int func)
	{
		instr = new R_type(code, source1, source2, shift, func);
		this.code = code;
		this.shift = shift;
		this.func = func;
		this.source1 = source1;
		this.source2 = source2;
	}
	public Control(int code, int sourceS, int sourceT, int immediate)
	{
		instr = new I_type(code, sourceS, sourceT, immediate) ;
		this.code = code;
		this.source1 = sourceS;
		this.source2 = sourceT;
	}
	public Control(int code, int immediate)
	{
		instr = new J_type(code, immediate);
		this.code = code;
	}
	
	
	public int runALUFunc() {
		return instr.runALU();
	}
	public boolean branch() {
		return (this.code==4 || this.code==5);
	}
	public boolean store() {
		return this.code==43;
	}
	public boolean laod() {
		return this.code==35;
	}
	public boolean jr(){
		return this.code==0 && this.func==8;
	}
	public boolean jal(){
		return this.code == 3;
	}
	public String print() {
		return instr.print();
	}
}
