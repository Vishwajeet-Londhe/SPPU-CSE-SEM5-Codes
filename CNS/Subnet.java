import java.util.Scanner;
import java.net.InetAddress;

class Subnet
{
    public static void main(String args[])
    {
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter IP Address : ");
        String IP=sc.nextLine();
        
        String Split_IP[] = IP.split("\\.");
        
        String Split_Binary_IP[]= new String[4];
        String Binary_IP = "";
        for(int i=0;i<4;i++)
        {
            Split_Binary_IP[i]=appendZeroes(Integer.toBinaryString(Integer.parseInt(Split_IP[i])));
            Binary_IP+=Split_Binary_IP[i];
        }
        System.out.println("The Binary Ip Address : "+Binary_IP);
        
        System.out.print("Enter Number of Address : ");
        int No_Bits=sc.nextInt();
        int Bits=(int)Math.ceil(Math.log(No_Bits)/Math.log(2));
        System.out.println("The Number of Bits Required : "+Bits);
        int Mask=32-Bits;
        int Total_Address=(int)Math.pow(2,Bits);
        System.out.println("Subnet Mask : "+Mask);
        
        int First_Binary_IP[]=new int[32];
        for(int i=0;i<32;i++)
        {
            
            First_Binary_IP[i]=(int)Binary_IP.charAt(i)-48;
        }
        for(int i=31;i>31-Bits;i--)
        {
            
            First_Binary_IP[i] &=0;
        }
        String First_IP[]={"","","",""};
        for(int i=0;i<32;i++)
        {
            First_IP[i/8]=new String(First_IP[i/8]+First_Binary_IP[i]);
        }
        int First_Offset=0;
        int IP_Address[]=new int[4]; ;
        System.out.print("GROUP-1 \n The First Address : ");
        for(int i=0;i<4;i++)
        {
            System.out.print(IP_Address[i]=First_Offset=Integer.parseInt(First_IP[i],2));
            if(i!=3)
            System.out.print(".");
        }
        System.out.println();

        int Last_Binary_IP[]=new int [32];
        for(int i=0;i<32;i++)
        {
            
            Last_Binary_IP[i]=(int)Binary_IP.charAt(i)-48;
        }
        for(int i=31;i>31-Bits;i--)
        {
            
            Last_Binary_IP[i]|= 1;
        }
        String Last_IP[]={"","","",""};
        for(int i=0;i<32;i++)
        {
            Last_IP[i/8]=new String(Last_IP[i/8]+Last_Binary_IP[i]);
        }
        int IP_Last[]=new int[4];
        System.out.print("The Last Address : ");
        for(int i=0;i<4;i++)
        {
            System.out.print(IP_Last[i]=Integer.parseInt(Last_IP[i],2));
            if(i!=3)
            System.out.print(".");
        }
        System.out.println();
        System.out.print("How many Subnets you need to form : ");
        int Scount=sc.nextInt();
        for(int j=1;j<Scount;j++)
        {
            System.out.print(" GROUP "+ (j+1)+" First Address : ");
            for(int i=0;i<4;i++)
            {
                if(i<3)
                {
                    System.out.print(IP_Address[i]+".");
                }
                else
                System.out.println(IP_Address[i]=IP_Address[i]+Total_Address);
            }
            System.out.print(" GROUP "+ (j+1)+" Last Address : ");
            for(int i=0;i<4;i++)
            {
                if(i<3)
                {
                    System.out.print(IP_Last[i]+".");
                }
                else
                System.out.println(IP_Last[i]=IP_Last[i]+Total_Address);
            }
            System.out.println();
        }
        try
        {
            System.out.print("Enter IP Address to Ping : ");
            Scanner s=new Scanner(System.in);
            String IPAddress=s.nextLine();
            InetAddress Inet = InetAddress.getByName(IPAddress);
            if(Inet.isReachable(5000))
            {
                System.out.print("The IP Address is Reachable "+IPAddress);
            }
            else
            {
                System.out.print("The IP Address is NOT Reachable "+IPAddress);
            }
        }
        catch( Exception e)
        {
            System.out.println("Exception : "+e.getMessage());
        }
    }
static String appendZeroes(String s)
{
String temp= new String("00000000");
return temp.substring(s.length())+ s;
}
}