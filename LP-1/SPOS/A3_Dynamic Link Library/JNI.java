package JNI;

import java.util.Scanner;
public class JNI {
	public native void JniAdd(int no1,int no2);
	public native void JniSub(int no1,int no2);
	public native void JniMult(int no1,int no2);
	public native void JniDiv(double no1,double no2);
	public native void JniPow(int no1,int no2);
	public native void JniSqrt(int no1);
	public native void JniMod(int no1,int no2);
	static { System.load("This-PC\\F:\\Practical codes\\LP 1\\A3_Dynamic Link Library\\JNI\\src\\JNI\\libJNI.dll");}
	public static void main(String[] args)throws Exception {
		 int no1,no2;
		 Scanner in=new Scanner(System.in);
		 JNI MJ=new JNI();
		 System.out.println("JNI using C");
		 System.out.print("Enter first number: ");
		 no1=in.nextInt(); 
		 double no1f=no1;
		 System.out.print("Enter second number: ");
		 no2=in.nextInt();
		 MJ.JniAdd(no1,no2);
		 MJ.JniSub(no1,no2);
		 MJ.JniMult(no1,no2);
		 MJ.JniDiv((double)no1,(double)no2);
		 MJ.JniPow(no1,no2);
		 MJ.JniSqrt(no2);
		 MJ.JniMod(no1,no2);

	}

}
