package Scheduling.RoundRobin;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int remaining_bt;
    int completion_time;
    int turnaround_time;
    int waiting_time;
    boolean is_completed;

    Process(String pid, int at, int bt) {
        id = pid;
        arrival_time = at;
        burst_time = bt;
        remaining_bt = bt; // Remaining burst time is initially equal to burst time
        is_completed = false;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes (maximum 10): ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];

        System.out.println("Enter the Arrival Time and Burst Time for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " (Arrival Time): ");
            int at = sc.nextInt();
            System.out.print("P" + (i + 1) + " (Burst Time): ");
            int bt = sc.nextInt();
            process_queue[i] = new Process("P" + (i + 1), at, bt);
        }

        System.out.print("Enter the quantum time: ");
        int quantum_time = sc.nextInt();

        // Sort processes by arrival time
        Arrays.sort(process_queue, Comparator.comparingInt(p -> p.arrival_time));

        // Initialize variables
        int current_time = 0;  // Tracks the current time
        int completed = 0;     // Number of completed processes
        int total_tat = 0, total_wt = 0;  // Total Turnaround Time and Waiting Time

        // Process execution using Round Robin
        while (completed < n) {
            boolean process_executed = false;

            for (int i = 0; i < n; i++) {
                Process p = process_queue[i];

                // Process can execute only if it's arrived and not yet completed
                if (p.arrival_time <= current_time && !p.is_completed) {
                    process_executed = true;

                    // If remaining burst time is more than quantum time, execute for quantum time
                    if (p.remaining_bt > quantum_time) {
                        current_time += quantum_time;
                        p.remaining_bt -= quantum_time;
                    } else {
                        // Process completes in this round
                        current_time += p.remaining_bt;
                        p.remaining_bt = 0;
                        p.completion_time = current_time;

                        // Calculate turnaround time and waiting time
                        p.turnaround_time = p.completion_time - p.arrival_time;
                        p.waiting_time = p.turnaround_time - p.burst_time;

                        total_tat += p.turnaround_time;
                        total_wt += p.waiting_time;

                        p.is_completed = true; // Mark process as completed
                        completed++;
                    }
                }
            }

            // If no process was executed, advance time to the next arriving process
            if (!process_executed) {
                current_time++;
            }
        }

        // Display process information
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnaround Time\t\tWaiting Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            Process p = process_queue[i];
            System.out.print("\n  " + process_queue[i].id + "\t\t\t" + process_queue[i].arrival_time + "\t\t\t\t" + process_queue[i].burst_time + "\t\t\t\t" + process_queue[i].completion_time + " \t\t\t\t" + process_queue[i].turnaround_time + "\t\t\t\t\t" + process_queue[i].waiting_time + "\n");
        }

        // Calculate and display average turnaround time and waiting time
        System.out.println("\nAverage Turnaround Time = " + (float) total_tat / n);
        System.out.println("Average Waiting Time = " + (float) total_wt / n);

        sc.close();
    }
}