package com.trifidearth.mips;


import java.util.Scanner;

public class MainClass {

	String filename;
	Assembler assembler;
	Control control;
	DataPath dataPath;
	int [] stack; //was boolean :( gave up
	
	/**
	 * main: makes instance of processor, and runs code
	 * 		code is from file specified in args
	 * 		if no file name in args, it asks for file name
	 * @param args
	 */
	public static void main(String[] args) 
	{
		MainClass intel = new MainClass(args);	
		intel.run(intel.assembler.machineCode);
	}
	
	
	/**
	 * Constructor:
	 * sets up processor..
	 * uses call parameters or retrieves parameters if none
	 * 
	 * @param args call parameters
	 */
	public MainClass(String [] args) 
	{
		
		if(!verify(args))
			return;
		
		stack = initiateStack();
		dataPath = new DataPath(assembler.machineCode, stack, assembler.print());
		
	}

	/**
	 * just an array of ints representing a memory stack
	 * @return
	 */
	private int [] initiateStack()
	{	//256 / 4 = 64
		int [] temp = {	
				2,1,100,0,3,5,52,92,31,82,12,29,61,45,72,57,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		//							 s0s1s2s3ra
		//a0==0 = s2
		//a1==16 = s3
		//0 = s0
		//t0 = (s3 < s0 ? 1 : 0)
		return temp;
	}
	
	/**
	 * NO TIME: was going to finish implementing my bitwise simulation but proved too time consuming
	 * if your code sorts an array of ints, this is wher you put the ints.. 
	 * @return the start state of the stack!
	 */
	public boolean[][] initiateStackOFF() 
	{	//TODO make a console start stack state retrieval method!!!
		//= new boolean [256][8];
		boolean [][] temp = {	
	//256	
		//128
			//64
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
			//64
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
		//128		
			//64
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
			//64	
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				//32
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}, 
				{false, false, false, false, false, false, false, false}};
		return temp;
	}

	/**
	 * @param args
	 */ 


	/**
	 * if no filename in args then use console to get filename
	 * 	also can desplay a help!
	 * @param args
	 * @return
	 */
	private boolean verify(String [] args) 
	{
		if (args==null ||  args.length<=1)
		{	
			args = new String [2];
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the filepath and name of the file you want to run:");
			args[1] = in.nextLine();
		}
		else if(args[1].equals("help"))
		{
			System.out.println(	"Intel: Simple MIPS ISA 5 Stage Pipeline Processor Simulation\n" +
								"\n" +
								"usage: Intel input_file_path/name.txt\n" +
								"\n" +
								"read_file_path/name.txt:\n" +
								"	This file is to be a .txt file containing simple MIPS asm instructions, one per line");
			return false;
		}
		filename = args[1];
		assembler = new Assembler(filename);
		if(assembler.machineCode != null)
			return true;
		//given in assembler
		//System.out.println("Error reading file '" + filename + "'");
		return false;
	}
	
	/**
	 * send machine code to the dataPath and process your cycles
	 * @param machineCode
	 */
	private void run(boolean [][] machineCode) 
	{	
		//TODO
		System.out.println("\n\nTHIS IS THE BEGINNING!\n\nThe Stack @ start:");
		for(int stackElement : stack)
			System.out.print(" " + stackElement);
		System.out.println("\n");
		
		while(dataPath.processCycle());
		
		System.out.println("The Stack @ end:");
		for(int stackElement : stack)
			System.out.print(" " + stackElement);
		
	}
}
