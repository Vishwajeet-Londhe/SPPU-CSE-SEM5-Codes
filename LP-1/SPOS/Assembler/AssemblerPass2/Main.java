package Assembler.AssemblerPass2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class Main
{
    public static void main(String[] Args) throws IOException
    {
        BufferedReader b1 = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler.AssemblerPass2\\intermediate.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler.AssemblerPass2\\symtab.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler.AssemblerPass2\\littab.txt"));
        FileWriter f1 = new FileWriter("Pass2.txt");
        HashMap<Integer, String> symSymbol = new HashMap<Integer, String>();
        HashMap<Integer, String> litSymbol = new HashMap<Integer, String>();
        HashMap<Integer, String> litAddr = new HashMap<Integer, String>();
        String s; int symtabPointer=1,littabPointer=1,offset;
        while((s=b2.readLine())!=null)
        { String word[]=s.split("\t\t\t");
            symSymbol.put(symtabPointer++,word[1]);
        }
        while((s=b3.readLine())!=null)
        { String word[]=s.split("\t\t");
            litSymbol.put(littabPointer,word[0]);
            litAddr.put(littabPointer++,word[1]);
        }
        while((s=b1.readLine())!=null)
        {
            if(s.substring(1,6).compareToIgnoreCase("IS,00")==0)
            { f1.write("+ 00 0 000\n"); }
            else if(s.substring(1,3).compareToIgnoreCase("IS")==0)
            { f1.write("+ "+s.substring(4,6)+" ");
                if(s.charAt(9)==')')
                {
                    f1.write(s.charAt(8)+" ");
                    offset=3;
                }
                else
                {
                    f1.write("0 ");
                    offset=0;
                }
                if(s.charAt(8+offset)=='S')
                    f1.write(symSymbol.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
                else f1.write(litAddr.get(Integer.parseInt(s.substring(10+offset,s.length()-1)))+"\n");
            }
            else if(s.substring(1,6).compareToIgnoreCase("DL,01")==0)
            {
                String s1=s.substring(10,s.length()-1),s2="";
                for(int i=0;i<3-s1.length();i++)
                    s2+="0";
                s2+=s1;
                f1.write("+ 00 0 "+s2+"\n");
            }
            else{
                f1.write("\n");
            }
        }
        f1.close();
        b1.close();
        b2.close();
        b3.close();
    }
}