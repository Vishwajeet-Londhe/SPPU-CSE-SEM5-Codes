#include <iostream>
using namespace std;

int main()
{
    int i,j,k,l;
    int Frame_Size;
    cout<<"Enter the frame size : ";
    cin>>Frame_Size;
    
    int Frame[20];
    cout<<"\nEnter the Frame : ";
    for(i=0;i<Frame_Size;i++)
    {
        cin>>Frame[i];
    }
    
    int Generator_Size;
    cout<<"Enter the generator size : ";
    cin>>Generator_Size;
    
    int Generator[20];
    cout<<"Enter the Generator : ";
    for(i=0;i<Generator_Size;i++)
    {
        cin>>Generator[i];
    }
    
    cout<<"\n**************Sender's Side*************";
    cout<<"\nFrame : ";
    for(i=0;i<Frame_Size;i++)
    {
        cout<<Frame[i];
    }
    
    cout<<"\nGenerator : ";
    for(i=0;i<Generator_Size;i++)
    {
        cout<<Generator[i];
    }
    
    int Remainder_Size = Generator_Size - 1;
    cout<<"\nNumber of 0's to be appended : "<<Remainder_Size;
    for(i=Frame_Size;i<Frame_Size+Remainder_Size;i++)
    {
        Frame[i] = 0;
    }
    
    int temp[20];
    for(i=0;i<20;i++)
    {
        temp[i] = Frame[i];
    }
    
    cout<<"\nMessage after appending 0's : ";
    for(i=0;i<Frame_Size+Remainder_Size;i++)
    {
        cout<<temp[i];
    }
    
    for(i=0;i<Frame_Size;i++)
    {
        j=0;
        k=i;
        if(temp[k] >= Generator[j])
        {
            for(j=0,k=i;j<Generator_Size;j++,k++)
            {
                if((temp[k] == 1 && Generator[j] == 1) || (temp[k] == 0 && Generator[j] == 0))
                {
                    temp[k] = 0;
                }
                else
                {
                    temp[k] = 1;    
                }
            }
        }
    }
    
    int CRC[15];
    for(i=0,j=Frame_Size;i<Remainder_Size;i++,j++)
    {
        CRC[i] = temp[j];
    }
    
    cout<<"\nCRC bits are : ";
    for(i=0;i<Remainder_Size;i++)
    {
        cout<<CRC[i];
    }
    
    cout<<"\nTransmitted Frames are : ";
    int Transmitted_Frame[15];
    for(i=0;i<Frame_Size;i++)
    {
        Transmitted_Frame[i] = Frame[i];
    }
    
    for(i=Frame_Size,j=0;i<Frame_Size+Remainder_Size;i++,j++)
    {
        Transmitted_Frame[i] = CRC[i];
    }
    
    for(i=0;i<Frame_Size+Remainder_Size;i++)
    {
        cout<<Transmitted_Frame[i];
    }
    
    cout<<"\nReceiver Side : ";
    cout<<"\nReceiver Frame : ";
    for(i=0;i<Frame_Size+Remainder_Size;i++)
    {
        cout<<Transmitted_Frame[i];
    }
    
    for(i=0;i<Frame_Size+Remainder_Size;i++)
    {
        temp[i] = Transmitted_Frame[i];
    }
    
    for(i=0;i<Frame_Size+Remainder_Size;i++)
    {
        j=0;
        k=i;
        if(temp[k] >= Generator[j])
        {
            for(j=0,k=i;j<Generator_Size;j++,k++)
            {
                if((temp[k] == 1 && Generator[j] == 1) || (temp[k] == 0 && Generator[j] == 0))
                {
                    temp[k] = 0;
                }
                else
                {
                    temp[k] = 1;    
                }
            }
        }
    }
    
    cout<<"\nRemainder : ";
    int Receiver_Remainder[15];
    
    for(i=Frame_Size,j=0;i<Frame_Size+Remainder_Size;i++,j++)
    {
        Receiver_Remainder[j] = temp[i];
    }
    
    for(i=0;i<Remainder_Size;i++)
    {
        cout<<Receiver_Remainder[i];
    }
    
    int flag=0;
    for(i=0;i<Remainder_Size;i++)
    {
        if(Receiver_Remainder[i]!=0)
        {
            flag=1;
        }
    }
    
    if(flag==0)
    {
        cout<<"\nAs Remainder is 0, MEssage transmitted from sender to receiver is CORRECT! ";
    }
    else
    {
        cout<<"As Remainder is NOT 0, Message transmitted from sender to receiver contains ERROR! ";
    }
   return 0;
}
