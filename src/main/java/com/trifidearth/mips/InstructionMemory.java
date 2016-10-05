package com.trifidearth.mips;


import java.util.ArrayList;

public class InstructionMemory{

	boolean [][] instrMemory;  //byte addressed main memory containing the full program code...
	ArrayList<Dependent> dependents;
	
	public InstructionMemory(boolean[][] machineCode) 
	{
		instrMemory = machineCode;
		dependents = new ArrayList<Dependent>();
	}

	public Id_reg procIF(int pc) 
	{
		//TODO check dependencies!!!
		//Separate parts of code!!
		
		if(pc<0)
		{
			System.out.println("Segmentation fault..."); 
			System.exit(0);
		}
		if(pc>=instrMemory.length)
		{
			System.out.println("\n\nPROGRAM FINISHED..."); 
			return null;
		} //TODO + 4    word addressed not byte addressed!!!!
		return extractInstr(instrMemory[pc], pc);
	}

	public Id_reg extractInstr(boolean [] mci, int pc)
	{ 
		Id_reg temp = null;
		//Instruction instr; 
		int opCode = getIntFrom(mci, 31, 26, false);
		switch (opCode)
		{
			case 0:		//R-type
						temp = new 	Id_reg(
								new Control(opCode,
										getIntFrom(mci, 25, 21, false), 	//source1Num
										getIntFrom(mci, 20, 16, false), 	//source2Num
										getIntFrom(mci, 10, 6, false), 	//shift
										getIntFrom(mci, 5, 0, false)),		//func
								getIntFrom(mci, 25, 21, false), 	//source1Num
								getIntFrom(mci, 20, 16, false), 	//source2Num
								0, 							//signExtend not used
								getIntFrom(mci, 15, 11, false), 	//wb reg #Num
								pc + 1);
						break;
			case 4:		//beq
			case 5:		//bne
			case 8:		//addi
			case 10:	//slti
			case 35:	//lw
			case 43:	//sw
						temp = new Id_reg(
								new Control(opCode, 
										getIntFrom(mci, 25, 21, false), 	//source1Num
										getIntFrom(mci, 20, 16, false), 	//wb reg #
										getIntFrom(mci, 15, 0, true)),	 //to signextend later (even though done)
								getIntFrom(mci, 25, 21, false), 	//source1Num
								0,							//not used
								getIntFrom(mci, 15, 0, true), 	//TODO signextend
								getIntFrom(mci, 20, 16, false), 	//wb reg #Num
								pc + 1);//TODO THIS MAY BE A JUMP!!!!!!  wont find out till later!
						break;
			case 2:		//j
			case 3:		//jal
						temp = new Id_reg(
								new Control(opCode, pc),
								0,							//source1
								0,							//not used
								pc,	//immediate
								31,							//$ra for jal wb
								getIntFrom(mci, 25, 0, false));//TODO signExtend and add immediate jump possibility!!
						break;
			default:	System.out.println("Segmintation fault.\n\tCan not handle opCode:" + opCode);
						System.exit(0);
						break;
		}
		
		if(!dependent(temp))
			return temp;
		return new Id_reg(null, 0, 0, 0, 0, pc); //bubble! do not change PC
	}
	
	/**
	 * checks for dependencies and adds in new ones!
	 * @return
	 */
	private boolean dependent(Id_reg temp) 
	{
		boolean dependent = false;
		int size = dependents.size();
		try{for(int i = 0; i < size; i++)
		{
			Dependent instr = dependents.get(i);
			if((instr.life = instr.life - 1) == 0)
			{
				dependents.remove(i);
				size--;
				i--;
			}
			else if(temp.control!=null && (instr.reg==temp.control.source1 || instr.reg==temp.control.source2))
			{	
				R_type.rTypeBubbles += (temp.control.instr.getClass()==R_type.class ? 1:0);
				I_type.iTypeBubbles += (temp.control.instr.getClass()==I_type.class ? 1:0);
				J_type.jTypeBubbles += (temp.control.instr.getClass()==J_type.class ? 1:0);
				dependent = true; //still dependent!!  make bubble
			}
		}} catch (Exception e) {e.printStackTrace();}
		if(!dependent && temp.writeRegNum!=0 && !temp.control.store() && !temp.control.branch())
			dependents.add(new Dependent (temp.writeRegNum, 3));
		//if(temp.control.laod())
		//	dependents.add(new Dependent (temp.sourceRegNum1, 3));
		return dependent;
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
}