package Assembler.AssemblerPass2;

import java.io.*;
import java.util.*;

public class PassII {
    public static void main(String[] args) throws IOException {
        //Creating file object to read data from 3 txt files
        BufferedReader br = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler\\AssemblerPass2\\intermediate.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler\\AssemblerPass2\\symtab.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("E:\\Coding\\TE_SEM_V\\SPOS\\TE_SPOS\\src\\Assembler\\AssemblerPass2\\littab.txt"));

        //Creating file writer object
        FileWriter fw = new FileWriter("P2.txt");

        //Map the pointer and the symbol/literal addresses
        HashMap<Integer, String> symSymbol = new HashMap<Integer, String>();
        HashMap<Integer, String> litSymbol = new HashMap<Integer, String>();
        HashMap<Integer, String> litAddr = new HashMap<Integer, String>();
        int symtabPointer=1,littabPointer=1;
        String line,s;

        //Mapping symbol addresses with symbol ptr
        while((s=b2.readLine())!=null)
        {
            String word[]=s.split("\t\t\t");
            symSymbol.put(symtabPointer++,word[1]);
        }

        //Mapping literal addresses with literal ptr
        while((s=b3.readLine())!=null)
        {
            String word[]=s.split("\t\t");
            litSymbol.put(littabPointer,word[0]);
            litAddr.put(littabPointer++,word[1]);
        }

        //Reading intermediate code
        while((line=br.readLine())!=null){
            //Skipping processing of Assembler Directives
            if(line.contains("AD")) {
                fw.write("\n");
                continue;
            }
            //Processing imperative statements
            else if(line.contains("IS")){
                if(line.contains("IS,00"))
                    fw.write("+ 00  0  000\n");
                else
                    fw.write("+ " + line.substring(4,6) + "  " + line.charAt(8) + "  ");
                if(line.contains("(S,")){
                    fw.write(symSymbol.get(Integer.parseInt(line.substring(13,14)))+"\n");
                }
                else if(line.contains("(L,")){
                    fw.write(litAddr.get(Integer.parseInt(line.substring(13,14)))+"\n");
                }
            }
            //Process DS statement
            else if(line.contains("DL,01")){
                fw.write("+ 00  0  00" + line.charAt(10) + "\n");
            }
            else if(line.contains("DL,02")){
                fw.write("\n");
            }
        }
        fw.close();
        br.close();
        b2.close();
        b3.close();
    }
}