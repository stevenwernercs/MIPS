package com.trifidearth.mips;


public abstract class Instruction 
{

	final static String [] REG ={
					"$zero", 
					"$at", 
					"$v0", "$v1", 
					"$a0", "$a1", "$a2", "$a3",
					"$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6", "$t7",
					"$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7",
					"$t8", "$t8",
					"$k0", "$k1",
					"$gp", 
					"$sp", 
					"$fp", 
					"$ra"};
	final static String [] NUM = {	"00000", "00001", "00010", "00011", "00100", 
									"00101", "00110", "00111", "01000", "01001", 
									"01010", "01011", "01100", "01101", "01110", 
									"01111", "10000", "10001", "10010", "10011", 
									"10100", "10101", "10110", "10111", "11000", 
									"11001", "11010", "11011", "11100", "11101", 
									"11110", "11111"};
	
	public Instruction() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public abstract int runALU();

	public abstract String print();
}
