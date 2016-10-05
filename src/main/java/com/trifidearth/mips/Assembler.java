package com.trifidearth.mips;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Assembler {

	final int WORD_SIZE = 32;
	String [] instructions;
	boolean [][] machineCode;
	String [] machineLines;
	ArrayList<JumpTag> jumpTags;
	
	public Assembler(String fileName) 
	{
		jumpTags = new ArrayList<JumpTag>();
		if(!checkType(fileName))
			machineCode = null;
	}
	
	/**
	 * @param args
	 */
	public static void mainOFF(String[] args) 
	{
		// TODO Auto-generated method stub
		//Assembler assembler = new Assembler(args[1]);	
	}

	private boolean checkType(String fileName) 
	{
		if(fileName.endsWith(".txt"))	//not yet compiled
		{
			if ((instructions = loadIntoArray(fileName)) == null)
			{
				System.out.println(	"Invalid: parameter!=input_file_path/name.txt\n" +
									"\tcall: 'Intel help' for instructions.");
				return false;
			}
			loadtags(instructions);
			if(!assembleInstructions(instructions))
			{
				System.out.println(	"File format is invalid: Could Not Compile!\n" +
									"	This file is to be a .txt file containing simple MIPS asm instructions, one per line");
				return false;	
			}
			
		}
		if(machineLines==null)
			machineLines = loadIntoArray(fileName);  //reading machineCode file
		if(!readMachineLines(machineLines))	//already assembled, parse booleans...
		{
			System.out.println(	"Input file seems to have been altered or compiled incorrrectly:\n" +
								"	try to recompile and run it again....");
			return false;	
		}
	return true;
	}
	
	private void loadtags(String [] instructions) {
		for(int i = 0; i < instructions.length; i++)
		{
			tagCheck(instructions[i], i);
		}
	}

	private void tagCheck(String instr, int lineIndex)
	{
		if(instr==null || (instr=instr.trim()) == "" || instr.startsWith("#"))
			return;	
		instr=instr.split("#")[0]; //Disregard comments...
		instr=instr.trim();
		if(instr.endsWith(":") && instr.split(":").length==1 && instr.split(" ").length==1)
		{
			jumpTags.add(new JumpTag(instr.split(":")[0], lineIndex));
			return;
		}
		String [] split = instr.split(":");
		if(split.length==0)
		{
			System.out.println("warning: line " + lineIndex + ", out of place ':'. disregaurding line:\n\t"+instr);
			return;
		}
		if(split.length>2)
		{
			System.out.println("warning: line " + lineIndex + ", too many ':'. disregaurding line:\n\t"+instr);
			return;
		}
		if(split.length==2)
			if(split[0].length()>0)
			{	//had jump, add and continue without...
				jumpTags.add(new JumpTag(split[0], lineIndex));
				return;
			}
			else
				System.out.println("warning: line " + lineIndex + ", nothing before ':'. disregaurding line:\n\t"+instr);
		return;
	}

	private boolean readMachineLines(String [] machineLines) 
	{
		machineCode = new boolean [machineLines.length][WORD_SIZE];
		for(int i = 0; i < machineLines.length; i++)
		{
			if(machineLines[i]==null)
				continue;
			char[] split = machineLines[i].toCharArray();
			if(split.length==WORD_SIZE)
			{
				for(int j = 0; j < WORD_SIZE; j++)
				{
					if (split[j]=='0')
						machineCode[i][j] = false;
					else if (split[j]=='1')
						machineCode[i][j] = true;
					else
						return false;
				}
			} 
			else
				return false;
		}
		return true;
	}

	//TODO this will extract machineCode and write to file
	//TODO learn to write binary file 
	/**
	 * actually creates machine code according to the MIPS instructions 
	 * @param instructions (line of raw asm file{minus #lines and blank lines})
	 * @return	an array of machine code one 32 bit instruction per line
	 */
	private boolean assembleInstructions(String [] instructions) 
	{
		//each instruction in in one line!  32 bit bandwidth in main memory!  
			//not byte addressed! word addressed! 
		machineLines = new String [instructions.length];
		for(int i = 0; i < instructions.length; i++)
		{
			if(instructions[i]==null)
				return false;
			String instr = format(instructions[i], i);
			if(instr.equals(""))
				continue; //blank line
			String [] split = instr.split(",");
			if(split.length==1) //no args
			{
				System.out.println("warning: line " + i + ", no args. disregaurding line:\n\t"+instr);
				return false;
			}
			if(split.length==2) //only type J
				if ((machineLines[i] = J_type.is(instr, jumpTags))!=null)
					continue;
				else if ((machineLines[i] = R_type.is(instr))!=null)
					continue;
				else
				{//TODO
					//else unknown op
					machineLines[i] = "11111111111111111111111111111111";
					continue;
					//return false;
				}
			//if(split.length==3)
			if ((machineLines[i] = I_type.is(instr, jumpTags, i))!=null)
				continue;
			if((machineLines[i] = R_type.is(instr))!=null)
				continue;
			//TODO
			//else unknown op
			machineLines[i] = "11111111111111111111111111111111";
			//continue;
			//return false;
		}
		return true;
	}
	
	/**
	 * formats the asm line for easy parsing
	 * @param instr	line of asm file
	 * @param lineIndex	#of line from asm file (for jump tags & warnings)
	 * @return
	 */
	private String format(String instr, int lineIndex) 
	{
		do{ 
			if(instr==null || (instr=instr.trim()) == "" || instr.startsWith("#"))
				return "";
			instr=instr.split("#")[0]; //Disregard comments...
			instr=instr.trim();
			if(instr.endsWith(":") && instr.split(":").length==1 && instr.split(" ").length==1)
			{
				//jumpTags.add(new JumpTag(instr.split(":")[0], lineIndex)); already done
				return "";
			}
			String [] split = instr.split(":");
			if(split.length==0)
			{
				System.out.println("warning: line " + lineIndex + ", out of place ':'. disregaurding line:\n\t"+instr);
				return "";
			}
			if(split.length>2)
			{
				System.out.println("warning: line " + lineIndex + ", too many ':'. disregaurding line:\n\t"+instr);
				return "";
			}
			if(split.length==2)
				if(split[0].length()>0)
				{	//had jump, add and continue without...
					//jumpTags.add(new JumpTag(split[0], lineIndex)); already done
					instr = split[1];
					continue; //sudo recursive check
				}
				else
				{
					System.out.println("warning: line " + lineIndex + ", nothing before ':'. disregaurding line:\n\t"+instr);
					return "";
				}	
			
			//reduce any ' '+ to ' '    could do diff but ehh
			String temp;
			do
			{
				temp = instr;
			}
			while((instr=instr.replace("  ", " "))!=temp);
			
			String [] params = instr.split(" ");
			if(params.length>4) //op r, r, r
			{
				System.out.println("warning: line " + lineIndex + ", too many argss. disregaurding line:\n\t"+instr);
				return "";
			}
			if(params.length==1)
			{
				System.out.println("warning: line " + lineIndex + ", no args. disregaurding line:\n\t"+instr);
				return "";
			}
			StringBuilder build = new StringBuilder();
			build.append(params[0]);
			build.append(",");
			for(int i = 1;  i < params.length; i++)
				build.append(params[i]);
			return build.toString();
		}while(true);
	}

	/**
	 * reads file into an array, skipping any #asm commented lines, and blank lines
	 * @param filename
	 * @return
	 */
	public String [] loadIntoArray(String filename) 
	{
		try
		{
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> lines = new ArrayList<String>();
			String line = null;
			while ( ((line = bufferedReader.readLine()) != null))
			{
				if(!(line.trim().startsWith("#"))) // || line.trim().length()==0)) else blanks are manual bubbles!
				{	
					lines.add(line);
				}
			}
			bufferedReader.close();
			return lines.toArray(new String[lines.size()]);		
		}
		catch (Exception e)
			{return null;}
	}
	
	/**
	 * Prints asm code read from file, and the machine code that is generated from it!
	 * @return
	 */
	public String [] print()
	{
		String [] array = new String[instructions.length];
		
		System.out.println("\t" + print("Rop Rd, Rt, Rs") +  "|---OpCode--|-src Rs--|-src Rt--|-dest Rd-|--shamt--|---funct---|");
		System.out.println("\t" + print("Iop Rt, Rs, int") + "|---OpCode--|-src Rs--|-dest Rt-|-------------Integer-----------|");
		System.out.println("\t" + print("Jop jump_number") + "|---OpCode--|------computed_address_excluding_leading_4_bits----|\n");
		for(int i = 0; i < instructions.length; i++)
		{
			StringBuilder asm = new StringBuilder();
			String instr = (" " + instructions[i]).split("#")[0].trim();
			if(instr.length()!=0)
			{
				String [] split = (instr).split(":");
				if(split.length>1)
				{
					String temp = i + "\t" + split[0].trim() + ":\n\t" + print(split[1].trim());
					asm.append(temp);
					System.out.print(temp);
				}
				else
				{
					String temp = i + "\t" + print(split[0]);
					asm.append(temp);
					System.out.print(temp);
				}
				if(machineCode[i]==null)
				{
					String temp = "no op";
					asm.append(temp);
					System.out.print(temp);
					continue;
				}
			}
			else
			{	
				String temp = i + "\t" + print("");
				asm.append(temp);
				System.out.print(temp);
			}
			
			for	(int j =  machineCode[i].length - 1; j >= 0; j--)
			{
				String temp = " " + (machineCode[i][j] ? "1" : "0");
				asm.append(temp);
				System.out.print(temp);
			}
			array[i] = asm.toString();
			System.out.println();
		}
		return array;
	}

	
	/**
	 * used just for proper alignment or printing 
	 * @param asm
	 * @return
	 */
	private String print(String asm)
	{
		String indent = "                                       ";
		indent = indent.substring(asm.length());
		return asm+indent;
	}
}

