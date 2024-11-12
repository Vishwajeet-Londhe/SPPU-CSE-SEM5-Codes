/******************************************************************************

Design Suitable data structure and implements Pass-I and Pass-II of a two pass macro processor. The output of Pass-I
(MNT,MDT and Intermediate code file without any macro definitions) should be input for Pass-II.

*******************************************************************************/
import java.io.*;

class MDT {
    String value;                       //Store Macro Definition

    MDT(String value) {
        this.value = value;
    }
}

class MNT {
    String macName;                    //To Store The Macro Name
    int mdtc;                          //Macro Definition Table Counter

    MNT(String macName, int mdtc) {
        this.macName = macName;
        this.mdtc = mdtc;
    }
}

class ArgList {                         //Keeps track of arguments passed to Macro
    String formalParam;

    ArgList(String formalParam) {
        this.formalParam = formalParam;
    }
}

public class Main {
    public static void main(String[] args) {
        BufferedReader br1 = null;
        BufferedWriter bw1 = null;
        try {
            br1 = new BufferedReader(new FileReader("input.txt"));
            bw1 = new BufferedWriter(new FileWriter("Output1.txt"));

            // Define fixed sizes for arrays
            int maxSize = 100; // Assuming a maximum of 100 entries for simplicity

            //Initialisation of arrays
            MDT[] mdtArray = new MDT[maxSize];
            MNT[] mntArray = new MNT[maxSize];
            ArgList[] argArray = new ArgList[maxSize];

            String line;
            boolean macroStart = false, macroEnd = false, fillArgList = false;
            int mdtCnt = 0, mntCnt = 0, argListCnt = 0;         //Points to the next location where values should be stored

            while ((line = br1.readLine()) != null) {
                line = line.replaceAll(",", " ");
                String[] tokens = line.split("\\s+");
                MDT currentMDT = new MDT("");

                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equalsIgnoreCase("MEND")) {
                        currentMDT.value += "\t" + tokens[i];
                        mdtArray[mdtCnt] = currentMDT;
                        macroEnd = true;
                        mdtCnt++;
                    } 
                    else if (tokens[i].equalsIgnoreCase("MACRO")) {
                        macroStart = true;
                        macroEnd = false;
                    } 
                    else if (!macroEnd) {
                        if (macroStart) {                                        //True if it's start of macro
                            mntArray[mntCnt] = new MNT(tokens[i], mdtCnt);       //Passing macro name and index as arguments
                            mntCnt++;
                            macroStart = false;
                            fillArgList = true;
                        } 
                        else if (fillArgList) {
                            while (i < tokens.length) {
                                currentMDT.value += "\t" + tokens[i];
                                if (tokens[i].matches("&[a-zA-Z]+") || tokens[i].matches("&[a-zA-Z]+[0-9]+")) {
                                    argArray[argListCnt] = new ArgList(tokens[i]);
                                    argListCnt++;
                                }
                                i++;
                            }
                            fillArgList = false;
                        } else {
                            if (tokens[i].equalsIgnoreCase("START") || tokens[i].matches("[0-9]+")) {
                                continue;
                            }

                            if (tokens[i].matches("[a-zA-Z]+") || tokens[i].matches("[a-zA-Z]+[0-9]+") || tokens[i].matches("[0-9]+")) {
                                currentMDT.value += "\t" + tokens[i];
                            }
                            if (tokens[i].matches("&[a-zA-Z]+") || tokens[i].matches("&[a-zA-Z]+[0-9]+")) {
                                for (int j = 0; j < argListCnt; j++) {
                                    if (tokens[i].equals(argArray[j].formalParam)) {
                                        currentMDT.value += "\t&" + (j + 1);
                                    }
                                }
                            }
                        }
                    } else {
                        bw1.write(tokens[i] + "\t"+"\n");
                    }
                }
                if (!currentMDT.value.isEmpty() && !macroEnd) {
                    mdtArray[mdtCnt] = currentMDT;
                    mdtCnt++;
                }
            }

            // Close the input file
            br1.close();

            // Output MNT
            bw1.close();
            bw1 = new BufferedWriter(new FileWriter("MNT.txt"));
            System.out.println("\n\t********MACRO NAME TABLE**********");
            System.out.println("\n\tINDEX\tNAME\tMDTC");
            for (int i = 0; i < mntCnt; i++) {
                System.out.println("\t" + i + "\t" + mntArray[i].macName + "\t" + mntArray[i].mdtc);
                bw1.write("\t" + i + "\t" + mntArray[i].macName + "\t" + mntArray[i].mdtc + "\n");
            }
            bw1.close();

            // Output ARGUMENT LIST
            bw1 = new BufferedWriter(new FileWriter("ARG.txt"));
            System.out.println("\n\n\t********ARGUMENT LIST**********");
            System.out.println("\n\tINDEX\tNAME");
            for (int i = 0; i < argListCnt; i++) {
                System.out.println("\t" + i + "\t" + argArray[i].formalParam);
                bw1.write("\t" + i + "\t" + argArray[i].formalParam + "\n");
            }
            bw1.close();

            // Output MDT
            bw1 = new BufferedWriter(new FileWriter("MDT.txt"));
            System.out.println("\n\t********MACRO DEFINITION TABLE**********");
            System.out.println("\n\tINDEX\t\tSTATEMENT");
            for (int i = 0; i < mdtCnt; i++) {
                System.out.println("\t" + i + "\t" + mdtArray[i].value);
                bw1.write("\t" + i + "\t" +mdtArray[i].value + "\n");
            }
            bw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br1 != null) br1.close();
                if (bw1 != null) bw1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}