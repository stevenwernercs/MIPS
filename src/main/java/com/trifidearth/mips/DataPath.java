package com.trifidearth.mips;


import java.util.LinkedList;

public class DataPath {
	
	static boolean clear = true;
	private String []				hInstr; 
	
	private InstructionMemory 		iFetch;
	private InstructionDecode 		iDecode;
	private Execution				execution;
	private Memory					memory;

	private LinkedList<Id_reg> 		id;
	private LinkedList<Ex_reg> 		ex;
	private LinkedList<Me_reg> 		me;
	private LinkedList<Wb_reg> 		wb;
	
	private int 					pc;
	private int 					bubble;
	private int						cycle;
	private int 					clearCount;
	private int 					instrCount;

	public DataPath(boolean[][] machineCode, int [] stack, String[] hInstr)
	{
		this.hInstr = hInstr;
		
		iFetch 		= new InstructionMemory(machineCode);
		iDecode 	= new InstructionDecode(); 		
		execution 	= new Execution();
		memory 		= new Memory(stack);
			 
		id 			= new LinkedList<Id_reg>();
		ex 			= new LinkedList<Ex_reg>();
		me 			= new LinkedList<Me_reg>();
		wb 			= new LinkedList<Wb_reg>();
		
		id.addFirst(new Id_reg(null, 0, 0, 0, 0, 0));
		ex.addFirst(new Ex_reg(null, 0, 0, 0, 0, 0));
		me.addFirst(new Me_reg(null, 0, 0, 0, 0));
		wb.addFirst(null);
		
		
		pc 			= 0;
		
		cycle 		= 0;
		instrCount	= 0;
		clearCount 	= 0;
		
		iDecode.printRegs();
	}
	
	public boolean processCycle()
	{
		iDecode.printRegs();
		printState();
		pc = getPC();
		if(pc < hInstr.length)
			System.out.println(hInstr[pc]);
		id.addFirst(iFetch.procIF(pc));
		if(id.getFirst()==null)
		{
			System.out.println("\n\nReport!!!!:\n--------------Total Overall----------------------");
			System.out.println("Total Instr in code:\t" + this.hInstr.length);
			System.out.println("Total Cycles:       \t" + cycle);
			System.out.println("Total Bubbles:      \t" + bubble);
			System.out.println("Total Instrs Exe:   \t" + instrCount);
			System.out.println("All Pipeline Clears:\t" + clearCount);
			System.out.println("Sum of Types:	    \t" + (R_type.rTypeCount + I_type.iTypeCount + J_type.jTypeCount) + "  differnece due to noOps at end of code before jr $ra");
			System.out.println("Overall average CPI:\t" + ((double)cycle/(double)instrCount));
			System.out.println("\n------------------R-Type-------------------------");
			System.out.println("R-Type Instructions:\t" + R_type.rTypeCount);
			System.out.println("R-Type Bubbles:     \t" + R_type.rTypeBubbles);
			System.out.println("R-Type Frequency:   \t" + ((double)R_type.rTypeCount/(double)instrCount));
			System.out.println("\n------------------I-Type-------------------------");
			System.out.println("I-Type Instructions:\t" + I_type.iTypeCount);
			System.out.println("I-Type Bubbles:     \t" + I_type.iTypeBubbles);
			System.out.println("I-Type Frequency:   \t" + ((double)I_type.iTypeCount/(double)instrCount));
			System.out.println("\n------------------J-Type-------------------------");
			System.out.println("J-Type Instructions:\t" + J_type.jTypeCount);
			System.out.println("J-Type Bubbles:     \t" + J_type.jTypeBubbles);
			System.out.println("J-Type Frequency:   \t" + ((double)J_type.jTypeCount/(double)instrCount));
			System.out.println();
			
			
			return false;
		}
		if(id.getFirst().control==null)
			bubble++;
		else
		{
			instrCount++;
		}
		//pc=id.getFirst().pc;//for pipeline, could be overwritten.. 
		//wb and decode in same process, wb of prev and decode of current!
		ex.addFirst(iDecode.procWB_ID(wb.removeLast(),id.removeLast()));
		me.addFirst(execution.procEX(ex.removeLast()));
		//pc=me.getFirst().pc; //may or may not be different..
		wb.addFirst(memory.procME(me.removeLast()));
		cycle++;
		return true;
	}

	private int getPC() 
	{
		Ex_reg temp = ex.getFirst();
		if(temp!=null && temp.control!=null && temp.control.jr())
		{
			//clearPipeLine();
			return temp.control.source1; //nonconditional jump
		}
		
		int pcNoJump = id.getFirst().pc;
			
		if (wb.getFirst()==null)
			return pcNoJump;
		int pcIS = wb.getFirst().pc;
		
		if(pcIS>=0 && DataPath.clear)
		{
			clearPipeLine();
			clearCount++;
			return pcIS;
		}
		return pcNoJump;
	}

	private void clearPipeLine() 
	{
		id 			= new LinkedList<Id_reg>();
		ex 			= new LinkedList<Ex_reg>();
		me 			= new LinkedList<Me_reg>();
		wb 			= new LinkedList<Wb_reg>();
		
		id.addFirst(new Id_reg(null, 0, 0, 0, 0, 0));
		ex.addFirst(new Ex_reg(null, 0, 0, 0, 0, 0));
		me.addFirst(new Me_reg(null, 0, 0, 0, 0));
		wb.addFirst(null);
		
		System.out.println("Pipeline Cleared!!\n");
	}

	private void printState() {
		// TODO print what ran that cycle!
		System.out.println(	
				"\t" + id.getFirst().print() + 
				"\t" + ex.getFirst().print() +
				"\t" + me.getFirst().print() +
				"\t" + (wb.getFirst()!=null ? wb.getFirst().print() : "no WB") + "\n");
	}
}
