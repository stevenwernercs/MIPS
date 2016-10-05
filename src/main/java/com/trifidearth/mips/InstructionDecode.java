package com.trifidearth.mips;


public class InstructionDecode{

	boolean [][] registers;
	
	public InstructionDecode() 
	{
		//empty at start!
		registers = new boolean [32][32];
		boolean [] temp1 = 
			   {false,false,false,false,false,false,false,false, //stack pointer is 256 = size of stack!
				true,false,false,false,false,false,false,false,
				false,false,false,false,false,false,false,false,
				false,false,false,false,false,false,false,false};
		boolean [] temp2 = 
			   {true,true,true,true,true,true,true,true, //return address = max value
				true,true,true,true,true,true,true,true,
				true,true,true,true,true,true,true,true,
				true,true,true,true,true,true,true,false};
		registers[29] = temp1; //stack pointer = size of stack
		registers[31] = temp2; //return address = -1 to start, if end then done!
	}

	public static void main(String [] args)
	{
		InstructionDecode id = new InstructionDecode();
		boolean [] t = {true, true, true, true, true, true, true, false, 
						true, false, true, true, false, false, true, true,
						true, false, true, true, false, false, true, true,
						true, false, true, true, true, true, true, true};
		System.out.println(id.getIntFrom(t, 31, 26, false));
		boolean [] re = id.make32bitBool(13);
		for(boolean b :  id.make32bitBool(13))
			System.out.print(b ? 1 : 0);
		System.out.println("\n"+id.getIntFrom(re, 31, 0, false));
	}
	
	
	
	public Ex_reg procWB_ID(Wb_reg wb, Id_reg id) 
	{
		//do WB from last cycle this cycle
		if(wb!=null)
		{
			if(wb.writeReg==31)
				DataPath.clear = true;
			registers[wb.writeReg] = make32bitBool(wb.writeValue);
		}
		//then do read!
		if(id!=null) //<<----should never sees this
			if(id.control!=null)
				return procID(id.control, id.sourceRegNum1, id.sourceRegNum2, id.intToSignExtend, id.writeRegNum, id.pc);
			else
				return new Ex_reg (null, 0, 0, 0, 0, id.pc);//TODO!  (this is a bubble)
		return new Ex_reg (null, 0, 0, 0, 0, 0); //should never happen
	}

	public Ex_reg procID(Control control, int sourceRegNum1, int sourceRegNum2, int intToSignExtend, int writeRegNum, int pc)
	{ 
		//Instruction instr; 
		int opCode = control.code;
		switch (opCode)
		{
			case 0:		//R-type
					return new 	Ex_reg(
							new Control(opCode,
									getIntFrom(registers[sourceRegNum1], 31, 0, true), 	//source1Value
									getIntFrom(registers[sourceRegNum2], 31, 0, true), 	//source2Value
									control.shift, 	//shift
									control.func),	//func
								getIntFrom(registers[sourceRegNum1], 31, 0, true), 	//source1Value
								getIntFrom(registers[sourceRegNum2], 31, 0, true), 	//source2Value
								0, 							//signExtend not used
								writeRegNum, 	//wb reg #
								pc);
			case 4:		//beq  	read values 4,5,43
			case 5:		//bne	read values
			case 8:		//addi	keep wb reg 8,10,35
			case 10:	//slti	keep wb reg
			case 35:	//lw	read values
			case 43:	//sw	keep wb reg
						return new Ex_reg(
								new Control(opCode, 
										getIntFrom(registers[sourceRegNum1], 31, 0, true), 	//source1Num
										((opCode==43 || opCode==5 || opCode==4) ? getIntFrom(registers[writeRegNum], 31, 0, true): writeRegNum), //wb reg #
										intToSignExtend),	 //to signextend later (even though done)
								getIntFrom(registers[sourceRegNum1], 31, 0, true), 	//source1
								0,					//not used
								intToSignExtend, 	//signextend  ... done (if conditonal ALU finds)
								((opCode==43 || opCode==5 || opCode==4) ? getIntFrom(registers[writeRegNum], 31, 0, true): writeRegNum), //wb reg #
								pc);
			case 2:		//j
			case 3:		//jal
						return new Ex_reg(
								new Control(opCode, intToSignExtend),
								sourceRegNum1,				//not used
								0,							//not used
								intToSignExtend,			//not used
								31,							//not used
								intToSignExtend);//signExtend, and imediate jump (no ALU) dont add to pc, have direct in simulation
			default:	System.out.println("Segmintation fault: " + opCode);		
		}
		System.exit(0);
		return null;
	}
	
	private boolean[] make32bitBool(int writeValue) 
	{
		boolean [] result =  new boolean[32];
		char [] binary = Integer.toBinaryString(writeValue).toCharArray();
		for(int i = 0; i < 32; i++)
		{	
			int index = binary.length-i-1;
			if(index<0)
				break; // finished..  
			result[i] = (binary[index]=='1');
		}
		return result;
	}
	
	//instrLine 31, 26 = bits 32,16,8,4,2,1
	private int getIntFrom(boolean [] machineCodeInstruction, int msb, int lsb, boolean canBeNegative)
	{
		int returnValue = 0;
		if(msb <32 && lsb >=0 && msb >= lsb)
		{
			if(machineCodeInstruction[msb] && canBeNegative)
			{	for(int i = msb; i >= lsb; i--)//negitive!
					returnValue += (machineCodeInstruction[i] ? 0 : (int)Math.pow(2.0, (double)(i-lsb)));
				returnValue = (returnValue + 1)* -1;
			}
			else
				for(int i = msb; i >= lsb; i--)//positive
					returnValue += (machineCodeInstruction[i] ? (int)Math.pow(2.0, (double)(i-lsb)) : 0);
		}
		else
		{
			System.out.println("ERROR IN PARSING INSTR");
			System.exit(0);
		}
		return returnValue;
	}

	public void printRegs()
	{
		System.out.print("\nRegisters:");
		for(int i = 0; i < registers.length;i++)
			System.out.print((i%8==0 ? "\n\t" : " ") + getIntFrom(registers[i], 31, 0, true) + " ");
		System.out.println("\n");
	}
}
