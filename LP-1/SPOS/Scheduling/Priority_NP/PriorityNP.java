package Scheduling.Priority_NP;
import java.util.*;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int priority;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    boolean isCompleted = false;

    Process(String pid, int ar, int br, int pr) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        priority = pr;
    }
}

public class PriorityNP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();

        Process[] process_queue = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + (i + 1) + " : ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time for Process " + (i + 1) + " : ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + " : ");
            int br = sc.nextInt();
            System.out.print("Enter Priority for Process " + (i + 1) + " : ");
            int pr = sc.nextInt();

            process_queue[i] = new Process(pid, ar, br, pr);
        }

        // Sort processes based on arrival time
        Arrays.sort(process_queue, Comparator.comparingInt(p -> p.arrival_time));

        int currentTime = 0;
        int completedProcesses = 0;
        float total_tat = 0, total_wt = 0;

        // Continue until all processes are completed
        while (completedProcesses < n) {
            // Find process with highest priority from arrived processes
            Process currentProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process p : process_queue) {
                if (!p.isCompleted && p.arrival_time <= currentTime && p.priority < highestPriority) {
                    highestPriority = p.priority;
                    currentProcess = p;
                }
            }

            if (currentProcess != null) {
                // Process found, execute it
                currentProcess.completion_time = currentTime + currentProcess.burst_time;
                currentProcess.turn_around_time = currentProcess.completion_time - currentProcess.arrival_time;
                currentProcess.waiting_time = currentProcess.turn_around_time - currentProcess.burst_time;

                total_tat += currentProcess.turn_around_time;
                total_wt += currentProcess.waiting_time;
                currentProcess.isCompleted = true;

                completedProcesses++;
                currentTime = currentProcess.completion_time;
            } else {
                // If no process is ready, increment time
                currentTime++;
            }
        }

        // Print process details
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        System.out.print("\nProcess\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnaround Time\t\tWaiting Time\n");
        System.out.print("-------------------------------------------------------------------------------------------------------------------");
        for(int  i = 0 ; i< n; i++)
        {
            System.out.print("\n  " + process_queue[i].id + "\t\t\t" + process_queue[i].arrival_time + "\t\t\t\t" + process_queue[i].burst_time + "\t\t\t\t" + process_queue[i].completion_time + " \t\t\t\t" + process_queue[i].turn_around_time + "\t\t\t\t\t" + process_queue[i].waiting_time + "\n");
        }
        System.out.println("Average Waiting Time = " + (total_wt / n));
        System.out.println("Average Turn Around Time = " + (total_tat / n));

        sc.close();
    }
}