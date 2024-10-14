import java.util.Scanner;

public class SubnetMask
{
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        String IP = new String();
        int Mask=0,Default=0;
        System.out.print("Enter IP_Address : ");
        IP = scan.nextLine();
        System.out.print("Enter Mask : ");
        Mask = scan.nextInt();
        String[] Split_IP = IP.split("\\.");
        System.out.println(Split_IP[0]+" "+Split_IP[1]+" "+Split_IP[2]+" "+Split_IP[3]+" ");
        int First = Integer.parseInt(Split_IP[0]);
        if(First>=0&&First<=127)
        {
            System.out.println("CLASS A");
            Default = 8;
        }
        else if(First>127&&First<=191)
        {
            System.out.println("CLASS B");
            Default=16;
        }
        else if(First>191&&First<=223)
        {
            System.out.println("CLASS C");
            Default=24;
        }
        else if(First>223)
        {
            System.out.println("CLASS D");
            Default=32;
        }
        String Binary_IP = new String();
        String Default_Mask = new String();
        for(int i=0;i<4;i++)
        {
            Binary_IP = Binary_IP + appendZeroes(Integer.toBinaryString(Integer.parseInt(Split_IP[i])));
        }
        System.out.println("IP in binary : "+Binary_IP);
        System.out.print("Default Mask : "+Default_Mask);
        for(int i=0;i<32;i++)
        {
            if(i<Mask)
            {
                Default_Mask = Default_Mask + "1";
            }
            else
            {
                Default_Mask = Default_Mask + "0";
            }
        }
        System.out.println(Default_Mask);
        String Net_ID = new String();
        for(int i=0;i<32;i++)
        {
            Net_ID = Net_ID + (Integer.parseInt(""+Binary_IP.charAt(i))&Integer.parseInt(""+Default_Mask.charAt(i)));
        }
        int p=-1;
        System.out.println(Net_ID);
        String[] Net = new String[4];
        String[] Def = new String[4];
        for(int i=0;i<32;i++)
        {
            if(i%8==0)
            {
                p++;
                Net[p] = "";
                Def[p]="";
                Net[p] = Net[p] + Net_ID.charAt(i);
                Def[p] = Def[p] + Default_Mask.charAt(i);
            }
            else
            {
                Net[p] = Net[p] + Net_ID.charAt(i);
                Def[p] = Def[p] + Default_Mask.charAt(i);
            }
        }
        System.out.println("Given IP : "+IP);
        System.out.print("Subnet Mask :");
        for(int i=0;i<4;i++)
        {
            System.out.print(Integer.parseInt(Def[i],2));
            if(i!=3)
            System.out.print(".");
        }
        System.out.println();
        System.out.print("Net_Id : ");
        for(int i=0;i<4;i++)
        {
            System.out.print(Integer.parseInt(Net[i],2));
            if(i!=3)
            System.out.print(".");
        }
    }
    private static String appendZeroes(String binaryString) 
    {
        String temp = new String("00000000");
        return temp.substring(binaryString.length())+ binaryString;
    }
}