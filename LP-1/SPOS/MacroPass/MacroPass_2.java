package MacroPass;
import java.io.*;


public class MacroPass_2 {

    public static void main(String[] args) throws IOException {
        mdt[] MDT = new mdt[20];
        mnt[] MNT = new mnt[4];
        arglist[] formal_parameter = new arglist[10];
        int macro_addr = -1;

        boolean macro_start=false,macro_end=false;
        int macro_call = -1;
        int mdt_cnt=0,mnt_cnt=0,formal_arglist_cnt=0,actual_arglist_cnt=0,temp_cnt=0,temp_cnt1=0;

        BufferedReader br1=new BufferedReader(new FileReader("src\\MacroPass\\MNT.txt"));
        String line;
        while((line = br1.readLine())!=null)
        {
            String[] parts=line.split("\\s+");
            MNT[mnt_cnt++] = new mnt(parts[0],Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
        }
        br1.close();
        System.out.println("\n\t********MACRO NAME TABLE**********");
        System.out.println("\n\tINDEX\tNAME\tADDRESS\tTOTAL ARGUMENTS");
        for(int i=0;i<mnt_cnt;i++)
            System.out.println("\t"+i+"\t\t"+MNT[i].name+"\t\t"+MNT[i].addr+"\t\t"+MNT[i].arg_cnt);

        br1=new BufferedReader(new FileReader("src\\MacroPass\\ARG.txt"));
        while((line = br1.readLine())!=null)
        {
            String[] parameters=line.split("\\s+");
            formal_parameter[formal_arglist_cnt++]=new arglist(parameters[0]);
            if(parameters.length>1)
                formal_parameter[formal_arglist_cnt-1].value = parameters[1];
        }
        br1.close();

        System.out.println("\n\n\t********FORMAL ARGUMENT LIST**********");
        System.out.println("\n\tINDEX\tNAME\tADDRESS");
        for(int i=0;i<formal_arglist_cnt;i++)
            System.out.println("\t"+i+"\t\t"+formal_parameter[i].argname+"\t"+formal_parameter[i].value);

        br1=new BufferedReader(new FileReader("src\\MacroPass\\MDT.txt"));
        while((line = br1.readLine())!=null)
        {
            MDT[mdt_cnt]=new mdt();
            MDT[mdt_cnt++].stmnt=line;
        }
        br1.close();

        System.out.println("\n\t********MACRO DEFINITION TABLE**********");
        System.out.println("\n\tINDEX\t\tSTATEMENT");
        for(int i=0;i<mdt_cnt;i++)
            System.out.println("\t"+i+"\t"+MDT[i].stmnt);

        br1=new BufferedReader(new FileReader("src\\MacroPass\\input.txt"));
        arglist[] actual_parameter=new arglist[10];
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("src\\MacroPass\\Output.txt"));
        while((line = br1.readLine())!=null)
        {
            line=line.replaceAll(",", " ");
            String[] tokens=line.split("\\s+");
            temp_cnt1=0;
            for(String current_token:tokens)
            {
                if(current_token.equalsIgnoreCase("macro"))
                {
                    macro_start=true;
                    macro_end=false;
                }
                if(macro_end && !macro_start)
                {
                    if(macro_call != -1 && temp_cnt<formal_arglist_cnt-1)
                    {
                        if(formal_parameter[actual_arglist_cnt].value != "")
                            actual_parameter[actual_arglist_cnt++]=new arglist(formal_parameter[actual_arglist_cnt-1].value);

                        actual_parameter[actual_arglist_cnt++]=new arglist(current_token);

                        if(formal_parameter[actual_arglist_cnt].value != "")
                            actual_parameter[actual_arglist_cnt++]=new arglist(formal_parameter[actual_arglist_cnt-1].value);

                    }

                    for(int i=0;i<mnt_cnt;i++)
                    {
                        if(current_token.equals(MNT[i].name))
                        {
                            macro_call=i;
                            temp_cnt1 = temp_cnt1 +MNT[i].arg_cnt;
                            break;
                        }
                        temp_cnt1 = temp_cnt1 + MNT[i].arg_cnt;
                    }
                    if(macro_call == -1)
                        bw1.write("\t" + current_token);
                }
                if(current_token.equalsIgnoreCase("mend"))
                {
                    macro_end=true;
                    macro_start=false;
                }
            }
            if(macro_call != -1)
            {
                macro_addr=MNT[macro_call].addr+1;
                while(true)
                {
                    if(MDT[macro_addr].stmnt.contains("mend") || MDT[macro_addr].stmnt.contains("MEND"))
                    {
                        macro_call = -1;
                        break;
                    }
                    else
                    {
                        bw1.write("\n");
                        String[] temp_tokens=MDT[macro_addr++].stmnt.split("\\s+");

                        for(String temp:temp_tokens)
                        {
                            if(temp.matches("#[0-9]+") || temp.matches(",#[0-9]+"))
                            {
                                int num = Integer.parseInt(temp.replaceAll("[^0-9]+", ""));
                                bw1.write(actual_parameter[num-1].argname+"\t");
                            }
                            else
                                bw1.write(temp + "\t");
                        }
                    }
                }
            }
            if(!macro_start )
                bw1.write("\n");
            macro_call= -1;
        }
        br1.close();
        bw1.close();

        System.out.println("\n\n\t********ACTUAL ARGUMENT LIST**********");
        System.out.println("\n\tINDEX\tNAME");
        for(int i=0;i<actual_arglist_cnt;i++)
            System.out.println("\t"+i+"\t"+actual_parameter[i].argname);
    }
}