package Scheduling.SJF_Preemptive;
import java.util.Scanner;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    int remaining_time;
    boolean isCompleted;

    Process(String pid, int ar, int br) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        remaining_time = br;
        isCompleted = false;
    }
}

public class PreemptiveSJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];
        int total_completed = 0, current_time = 0;
        float avgwt = 0, avgtat = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + (i + 1) + " : ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time for Process " + (i + 1) + " : ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + " : ");
            int br = sc.nextInt();
            process_queue[i] = new Process(pid, ar, br);
        }

        while (total_completed < n) {
            int min_burst_index = n;
            int min_burst_time = Integer.MAX_VALUE;

            // Find the process with the minimum remaining burst time that has arrived
            for (int i = 0; i < n; i++) {
                if (process_queue[i].arrival_time <= current_time && !process_queue[i].isCompleted && process_queue[i].remaining_time < min_burst_time) {
                    min_burst_time = process_queue[i].remaining_time;
                    min_burst_index = i;
                }
            }

            if (min_burst_index == n) {
                current_time++;
            } else {
                process_queue[min_burst_index].remaining_time--;
                current_time++;

                if (process_queue[min_burst_index].remaining_time == 0) {
                    process_queue[min_burst_index].completion_time = current_time;
                    process_queue[min_burst_index].turn_around_time = process_queue[min_burst_index].completion_time - process_queue[min_burst_index].arrival_time;
                    process_queue[min_burst_index].waiting_time = process_queue[min_burst_index].turn_around_time - process_queue[min_burst_index].burst_time;
                    process_queue[min_burst_index].isCompleted = true;
                    total_completed++;
                    avgwt += process_queue[min_burst_index].waiting_time;
                    avgtat += process_queue[min_burst_index].turn_around_time;
                }
            }
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnaround Time\t\tWaiting Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.println(process_queue[i].id + "\t\t\t\t" + process_queue[i].arrival_time + "\t\t\t\t" + process_queue[i].burst_time + "\t\t\t\t" + process_queue[i].completion_time + "\t\t\t\t" + process_queue[i].turn_around_time + "\t\t\t\t" + process_queue[i].waiting_time);
        }

        System.out.println("\nAverage Turn Around Time: " + (avgtat / n));
        System.out.println("Average Waiting Time: " + (avgwt / n));
        sc.close();
    }
}