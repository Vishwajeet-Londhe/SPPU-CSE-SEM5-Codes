package Scheduling;

import java.util.*;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    int remaining_time;
    int priority;
    boolean isCompleted;

    Process() {}

    Process(String pid, int ar, int br) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        remaining_time = br; // For SJF Preemptive
        isCompleted = false; // For SJF Preemptive
    }

    Process(String pid, int ar, int br, int pr) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        priority = pr;
        remaining_time = br;
        isCompleted = false;
    }
}

public class Merged {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];

        // Input the processes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for Process " + (i + 1) + ": ");
            String pid = sc.next();
            System.out.print("Enter Arrival Time for Process " + (i + 1) + ": ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
            int br = sc.nextInt();
            System.out.print("Enter Priority for Process " + (i + 1) + "(Enter -1 if priority not needed): ");
            int pr = sc.nextInt();
            process_queue[i] = new Process(pid, ar, br, pr);
        }

        // Call different scheduling algorithms
        Merged obj = new Merged();


        obj.FCFS(process_queue.clone(), n);
        obj.SJF_Preemptive(process_queue.clone(), n);
        obj.Priority_NP(process_queue.clone(), n);
        //obj.RoundRobin(process_queue.clone(), n);
    }

    // FCFS Scheduling
    public void FCFS(Process[] process_queue, int n) {
        System.out.println("\nFirst Come First Serve (FCFS) Scheduling:");
        Process temp = new Process();
        int avgwt = 0, avgtat = 0;

        // Sorting by arrival time
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (process_queue[j].arrival_time > process_queue[j + 1].arrival_time) {
                    temp = process_queue[j];
                    process_queue[j] = process_queue[j + 1];
                    process_queue[j + 1] = temp;
                }
            }
        }

        // Completion, Turnaround, and Waiting Time Calculation
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
            } else {
                if (process_queue[i].arrival_time > process_queue[i - 1].completion_time) {
                    process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
                } else {
                    process_queue[i].completion_time = process_queue[i - 1].completion_time + process_queue[i].burst_time;
                }
            }
            process_queue[i].turn_around_time = process_queue[i].completion_time - process_queue[i].arrival_time;
            process_queue[i].waiting_time = process_queue[i].turn_around_time - process_queue[i].burst_time;
            avgwt += process_queue[i].waiting_time;
            avgtat += process_queue[i].turn_around_time;
        }

        // Output
        displayProcessDetails(process_queue, n, avgwt, avgtat);
    }

    // Preemptive SJF Scheduling
    public void SJF_Preemptive(Process[] process_queue, int n) {
        System.out.println("\nPreemptive Shortest Job First (SJF) Scheduling:");
        int total_completed = 0, current_time = 0;
        float avgwt = 0, avgtat = 0;

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

        // Output
        displayProcessDetails(process_queue, n, avgwt, avgtat);
    }

    // Non-Preemptive Priority Scheduling
    public void Priority_NP(Process[] process_queue, int n) {
        System.out.println("\nPriority Scheduling (Non-Preemptive):");
        float total_tat = 0, total_wt = 0;

        // Sort by priority
        for (int i = 0; i < n; i++) {
            int pos = i;
            for (int j = i + 1; j < n; j++) {
                if (process_queue[j].priority < process_queue[pos].priority) {
                    pos = j;
                }
            }
            Process temp = process_queue[i];
            process_queue[i] = process_queue[pos];
            process_queue[pos] = temp;
        }

        // Completion, Turnaround, and Waiting Time Calculation
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
            } else {
                if (process_queue[i].arrival_time > process_queue[i - 1].completion_time) {
                    process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
                } else {
                    process_queue[i].completion_time = process_queue[i - 1].completion_time + process_queue[i].burst_time;
                }
            }
            process_queue[i].turn_around_time = process_queue[i].completion_time - process_queue[i].arrival_time;
            process_queue[i].waiting_time = process_queue[i].turn_around_time - process_queue[i].burst_time;
            total_tat += process_queue[i].turn_around_time;
            total_wt += process_queue[i].waiting_time;
        }

        // Output
        displayProcessDetails(process_queue, n, total_wt, total_tat);
    }

    // Round Robin Scheduling
    public void RoundRobin(Process[] process_queue, int n) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the quantum time: ");
        int quantum_time = sc.nextInt();
        System.out.println("\nRound Robin Scheduling:");

        Arrays.sort(process_queue, Comparator.comparingInt(p -> p.arrival_time));

        int current_time = 0, completed = 0, total_tat = 0, total_wt = 0;

        while (completed < n) {
            boolean process_executed = false;

            for (int i = 0; i < n; i++) {
                Process p = process_queue[i];

                if (p.arrival_time <= current_time && !p.isCompleted) {
                    process_executed = true;

                    if (p.remaining_time > quantum_time) {
                        current_time += quantum_time;
                        p.remaining_time -= quantum_time;
                    } else {
                        current_time += p.remaining_time;
                        p.remaining_time = 0;
                        p.completion_time = current_time;
                        p.turn_around_time = p.completion_time - p.arrival_time;
                        p.waiting_time = p.turn_around_time - p.burst_time;
                        total_tat += p.turn_around_time;
                        total_wt += p.waiting_time;
                        p.isCompleted = true;
                        completed++;
                    }
                }
            }

            if (!process_executed) {
                current_time++;
            }
        }

        // Output
        displayProcessDetails(process_queue, n, total_wt, total_tat);
    }

    // Helper function to display process details
    private void displayProcessDetails(Process[] process_queue, int n, float total_wt, float total_tat) {
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");

        for (int i = 0; i < n; i++) {
            System.out.println(process_queue[i].id + "\t\t" + process_queue[i].arrival_time + "\t\t" + process_queue[i].burst_time
                    + "\t\t" + process_queue[i].completion_time + "\t\t" + process_queue[i].turn_around_time
                    + "\t\t" + process_queue[i].waiting_time);
        }

        System.out.println("\nAverage Turnaround Time: " + (total_tat / n));
        System.out.println("Average Waiting Time: " + (total_wt / n));
    }
}