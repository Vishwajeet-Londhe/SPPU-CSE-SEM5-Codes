package MacroPass;
import java.io.*;
class arglist {
	String argname,value;
	arglist(String argument) {
		this.argname=argument;
		this.value="";
	}
}

class mnt {
	String name;
	int addr;
	int arg_cnt;
	mnt(String nm, int address)
	{
		this.name=nm;
		this.addr=address;
		this.arg_cnt=0;
	}
	mnt(String nm, int address,int total_arg)
	{
		this.name=nm;
		this.addr=address;
		this.arg_cnt=total_arg;
	}
}


class mdt {
	String stmnt;
	public mdt() {
		stmnt="";
	}
}

public class PASS_1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br1=new BufferedReader(new FileReader("src\\MacroPass\\input.txt"));
		BufferedWriter bw1=new BufferedWriter(new FileWriter("src\\MacroPass\\Output1.txt"));
	 	String line;
	 	mdt[] MDT=new mdt[20];
	 	mnt[] MNT=new mnt[4];
	 	arglist[] ARGLIST = new arglist[10]; 
	 	boolean macro_start=false,macro_end=false,fill_arglist=false,first = true,start = false;
	 	int mdt_cnt=0,mnt_cnt=0,arglist_cnt=0;
	 	while((line = br1.readLine())!=null)
	 	{
	 		line=line.replaceAll(",", " ");
	 		String[] words=line.split("\\s+");
	 		MDT[mdt_cnt] = new mdt();
	 		String stmnt = "";
			 if(line.contains("START")) start = true;
	 		for(int i=0;i<words.length;i++)
	 		{
	 			if(line.contains("MEND"))
	 			{
	 				MDT[mdt_cnt++].stmnt = "\t"+words[i];
	 				macro_end = true;
	 			}
	 			if(line.contains("MACRO"))
	 			{
					 first = true;
	 				macro_start = true;
	 				macro_end = false;
	 			}
	 			else if(!macro_end)
	 			{
	 				if(macro_start)
		 			{
		 				MNT[mnt_cnt++]=new mnt(words[i],mdt_cnt);
						System.out.println(mnt_cnt);
		 				macro_start=false;
		 				fill_arglist=true;
		 			}
		 			if(fill_arglist)
		 			{
		 				while(i<words.length) {
							if(words[i].equals("=")){
								ARGLIST[arglist_cnt-1].value = words[i+1];
							}
							if (words[i].matches("&[a-zA-Z]+") || words[i].matches("&[a-zA-Z]+[0-9]+")) {

								if (first) {
									MDT[mdt_cnt].stmnt += "\t" + words[i];
									first = false;
								} else MDT[mdt_cnt].stmnt += "\t," + words[i];
								ARGLIST[arglist_cnt++] = new arglist(words[i]);
								MNT[mnt_cnt - 1].arg_cnt++;
							}

						    else MDT[mdt_cnt].stmnt = MDT[mdt_cnt].stmnt+ "\t" + words[i];
							stmnt +="\t"+ words[i];
		 					i++;
		 				}
		 				fill_arglist=false;
		 			}
		 			else {
		 				if(words[i].matches("[a-zA-Z]+") || words[i].matches("[a-zA-Z]+[0-9]+")||words[i].matches("[0-9]+")) {
		 					MDT[mdt_cnt].stmnt +="\t" + words[i];
		 					stmnt += "\t"+ words[i];
		 				}
		 				if(words[i].matches("&[a-zA-Z]+") || words[i].matches("&[a-zA-Z]+[0-9]+"))
		 				{
		 					for(int j=0;j<arglist_cnt;j++)
		 						if(words[i].equals(ARGLIST[j].argname)) {
									 if(i!=1)  MDT[mdt_cnt].stmnt += "\t,#"+(j+1);
									 else MDT[mdt_cnt].stmnt += "\t#"+(j+1);
		 							stmnt += "\t#"+(j+1);
		 						}
		 				}
		 			}
	 			}
				else if(!line.contains("MEND"))
					bw1.write(words[i]+"\t");
	 		}
			if(start) bw1.write("\n");
	 		if(stmnt!="" && !macro_end)
	 			mdt_cnt++;
	 	}
	 	br1.close();
		bw1.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter("src\\MacroPass\\MNT.txt"));
		for(int i=0;i<mnt_cnt;i++)
		{
			bw.write(MNT[i].name+"\t"+MNT[i].addr+"\t"+MNT[i].arg_cnt+"\n");
		}
		bw.close();

		
		bw1=new BufferedWriter(new FileWriter("src\\MacroPass\\ARG.txt"));
		for(int i=0;i<arglist_cnt;i++)
		{
			bw1.write(ARGLIST[i].argname+"\t"+ARGLIST[i].value + "\n");
		}
		bw1.close();

		bw1=new BufferedWriter(new FileWriter("src\\MacroPass\\MDT.txt"));

		for(int i=0;i<mdt_cnt;i++)
		{
			bw1.write(MDT[i].stmnt+"\n");
		}
		bw1.close();
	}
}