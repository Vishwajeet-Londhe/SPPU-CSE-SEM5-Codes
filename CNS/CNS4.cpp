#include <bits/stdc++.h>
#include <ctime>
#define ll long long int
using namespace std;

void Transmission(ll & i, ll & Window_Size, ll & Total_Frame, ll & Total_Transmitted)
{
    while(i <= Total_Frame)
    {
        int z=0;
        for(int k = i; k<i+Window_Size && k<=Total_Frame; k++)
        {
            cout<<"Sending Frame "<<k<<"..."<<endl;
            Total_Transmitted++;
        }
        for(int k = i; k<i+Window_Size && k<=Total_Frame; k++)
        {
            int Frame = rand() % 2;
            if(!Frame)
            {
                cout<<"Acknowledgement of Frame : "<<k<<"..."<<endl;
                z++;
            }
            else
            {
                cout<<"Time is out!! Frame number  "<<k<<" Not Received "<<endl;
                cout<<"Retransmitting Window...";
                break;
            }
        }
        cout<<"\n";
        i=i+z;
    }
}

int main()
{
    ll Total_Frame, Window_Size, Total_Transmitted=0;
    srand(time(NULL));
    cout<<"Enter total number of frames : ";
    cin>>Total_Frame;
    cout<<"Enter the window size : ";
    cin>>Window_Size;
    ll i=1;
    Transmission(i, Window_Size, Total_Frame, Total_Transmitted);
    cout<<"Total number of the frames which were sent and resent are : "<<Total_Transmitted<<endl;
    return 0;
}