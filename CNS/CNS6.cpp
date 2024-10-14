#include <iostream>
using namespace std;

struct node 
{
    int Distance[15];
    int From[15];
} 
Route[10];
int main()
{
    int Distance_Matrix[15][15], Nodes;
    cout << "Enter No. of Nodes : ";
    cin >> Nodes;
    cout << "Enter Distance Matrix : " << endl;
    for (int i = 0; i < Nodes; i++) 
    {
        for (int j = 0; j < Nodes; j++) 
        {
            cin >> Distance_Matrix[i][j];
/* Set distance from i to i as 0 */
            Distance_Matrix[i][i] = 0;
            Route[i].Distance[j] = Distance_Matrix[i][j];
            Route[i].From[j] = j;
        }
    }
    int flag;
    do 
    {
        flag = 0;
        for (int i = 0; i < Nodes; i++) 
        {
            for (int j = 0; j < Nodes; j++) 
            {
                for (int k = 0; k < Nodes; k++) 
                {
                    if ((Route[i].Distance[j]) > (Route[i].Distance[k] + Route[k].Distance[j])) 
                    {
                        Route[i].Distance[j] = Route[i].Distance[k] + Route[k].Distance[j];
                        Route[i].From[j] = k;
                        flag = 1;
                    }
                }
            }
        }
    }
    while (flag);
    for (int i = 0; i < Nodes; i++) 
    {
        cout << "Router Info for router : " << i + 1 << endl;
        cout << "Destination\tNext Hop\tDistance" << endl;
        for (int j = 0; j < Nodes; j++)
        printf("%d\t%d\t\t%d\n", j+1, Route[i].From[j]+1, Route[i].Distance[j]);
    }
    return 0;
}