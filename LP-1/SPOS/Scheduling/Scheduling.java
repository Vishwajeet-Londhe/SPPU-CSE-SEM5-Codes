import java.util.*;

class Process {
    int id, arrivalTime, burstTime, remainingTime, priority, waitingTime, turnaroundTime, completionTime;

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}

public class Scheduling {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time, burst time and priority (for Priority Scheduling) for Process " + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            int priority = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime, priority);
        }

        // Menu for scheduling algorithms
        System.out.println("Choose a CPU Scheduling Algorithm:");
        System.out.println("1. FCFS\n2. SJF (Preemptive)\n3. Priority (Non-preemptive)\n4. Round Robin (Preemptive)");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                FCFS(processes);
                break;
            case 2:
                SJFPreemptive(processes);
                break;
            case 3:
                PriorityNonPreemptive(processes);
                break;
            case 4:
                System.out.print("Enter time quantum for Round Robin: ");
                int quantum = sc.nextInt();
                RoundRobin(processes, quantum);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        sc.close();
    }

    // First Come First Serve (FCFS)
    public static void FCFS(Process[] processes) {
        int currentTime = 0;
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.waitingTime = currentTime - p.arrivalTime;
            currentTime += p.burstTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            p.completionTime = currentTime;
        }
        printResults(processes, "FCFS");
    }

    // Shortest Job First (Preemptive)
    public static void SJFPreemptive(Process[] processes) {
        int currentTime = 0, completed = 0;
        Process current = null;
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));

        while (completed < processes.length) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    pq.add(p);
                }
            }

            if (!pq.isEmpty()) {
                current = pq.poll();
                current.remainingTime--;
                currentTime++;

                if (current.remainingTime == 0) {
                    completed++;
                    current.completionTime = currentTime;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                }
            } else {
                currentTime++;
            }
        }

        printResults(processes, "SJF (Preemptive)");
    }

    // Priority Scheduling (Non-preemptive)
    public static void PriorityNonPreemptive(Process[] processes) {
        int currentTime = 0, completed = 0;
        Process current = null;
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));

        while (completed < processes.length) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    pq.add(p);
                }
            }

            if (!pq.isEmpty()) {
                current = pq.poll();
                current.waitingTime = currentTime - current.arrivalTime;
                currentTime += current.burstTime;
                current.turnaroundTime = current.waitingTime + current.burstTime;
                current.completionTime = currentTime;
                current.remainingTime = 0;
                completed++;
            } else {
                currentTime++;
            }
        }

        printResults(processes, "Priority (Non-preemptive)");
    }

    // Round Robin (Preemptive)
    public static void RoundRobin(Process[] processes, int quantum) {
        int currentTime = 0, completed = 0;
        Queue<Process> queue = new LinkedList<>();
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        
        int i = 0;
        while (completed < processes.length) {
            while (i < processes.length && processes[i].arrivalTime <= currentTime) {
                queue.add(processes[i]);
                i++;
            }

            if (!queue.isEmpty()) {
                Process current = queue.poll();
                int execTime = Math.min(current.remainingTime, quantum);
                current.remainingTime -= execTime;
                currentTime += execTime;

                if (current.remainingTime == 0) {
                    completed++;
                    current.completionTime = currentTime;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                } else {
                    queue.add(current);
                }
            } else {
                currentTime++;
            }
        }

        printResults(processes, "Round Robin");
    }

    // Helper method to print the results
    public static void printResults(Process[] processes, String algorithm) {
        System.out.println("\n" + algorithm + " Scheduling Results:");
        System.out.println("Process ID\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
        double totalWaitingTime = 0, totalTurnaroundTime = 0;
        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
            System.out.println(p.id + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.priority + "\t\t" +
                    p.completionTime + "\t\t" + p.turnaroundTime + "\t\t" + p.waitingTime);
        }
        System.out.println("Average Waiting Time: " + (totalWaitingTime / processes.length));
        System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processes.length));
    }
}