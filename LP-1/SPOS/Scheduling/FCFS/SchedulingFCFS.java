package Scheduling.FCFS;
import java.util.*;

class Process{
    String id;
    int arrival_time;
    int burst_time;
    int completion_time;
    int turn_around_time;
    int waiting_time;

    Process(){}
    Process(String pid, int ar, int br){
        id = pid;
        arrival_time = ar;
        burst_time = br;
    }
}

public class SchedulingFCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes : ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];
        Process temp = new Process();
        String pid;
        int ar,br;
        float avgwt=0,avgtat=0;
        for(int i=0;i<n;i++){
            System.out.print("Enter ID for Process " + (i+1) + " : ");
            pid = sc.next();
            System.out.print("Enter Arrival Time for Process " + (i+1) + " : ");
            ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i+1) + " : ");
            br = sc.nextInt();
            process_queue[i] = new Process(pid,ar,br);
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<(n-i-1);j++){
                if(process_queue[j].arrival_time > process_queue[j+1].arrival_time){
                    temp = process_queue[j];
                    process_queue[j] = process_queue[j+1];
                    process_queue[j+1] = temp;
                }
            }
        }
        for(int i=0;i<n;i++){
            if(i==0){
                process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
            }
            else{
                if(process_queue[i].arrival_time > process_queue[i-1].completion_time){
                    process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
                }else{
                    process_queue[i].completion_time = process_queue[i-1].completion_time + process_queue[i].burst_time;
                }
            }
            process_queue[i].turn_around_time = process_queue[i].completion_time - process_queue[i].arrival_time;
            process_queue[i].waiting_time = process_queue[i].turn_around_time - process_queue[i].burst_time;
            avgwt += process_queue[i].waiting_time;
            avgtat += process_queue[i].turn_around_time;
        }
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        System.out.print("\nProcess\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnaround Time\t\tWaiting Time\n");
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        for(int  i = 0 ; i< n; i++)
        {
            System.out.print("\n  " + process_queue[i].id + "\t\t\t" + process_queue[i].arrival_time + "\t\t\t\t" + process_queue[i].burst_time + "\t\t\t\t" + process_queue[i].completion_time + " \t\t\t\t" + process_queue[i].turn_around_time + "\t\t\t\t\t" + process_queue[i].waiting_time + "\n");
        }
        System.out.println("Average Waiting Time = " + (avgwt/n));
        System.out.println("Average Turn Around Time = " + (avgtat/n));
        sc.close();
    }
}