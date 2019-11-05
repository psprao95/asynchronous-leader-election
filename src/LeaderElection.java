import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;


public class LeaderElection {



    public static void main(String[] args) throws Exception {
        Scanner scanner=new Scanner(new File("connectivity.txt"));
        //Scanner scanner = new Scanner(new File("connectivity2.txt"));

        int n = scanner.nextInt();
        System.out.println("Number of processes: " + n);
        ArrayList < Integer > uids = new ArrayList < Integer > ();
        System.out.print("UIDs of processes: ");
        for (int i = 0; i < n; i++) {
            uids.add(scanner.nextInt());
            System.out.print(uids.get(i) + " ");
        }

        System.out.println("\n");

        Runnable processes[] = new Process[n];
        for (int i = 0; i < n; i++) {
            processes[i] = new Process(uids.get(i));

        }

        for (int i = 0; i < n; i++) {
            Process p = (Process) processes[i];
            System.out.print("Neighbors of process " + uids.get(i) + ": ");
            for (int j = 0; j < n; j++) {
                int k = scanner.nextInt();
                if (k == 1)
                {
                    System.out.print(uids.get(j) + " ");
                    p.addChannel((Process) processes[j]);
                }
            }
            System.out.print("\n");
        }
        
        scanner.close();

        // STart n threads
       Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(processes[i]);
            threads[i].start();
        }

        while (true) {
            /* check if any thread is alive */
            boolean flag = false;
            for (Thread t: threads) {
                if (t.isAlive()) {
                    flag = true;
                    break;
                }
            }

            /* no thread alive, exit */
            if (!flag) {
                System.out.println("Excution finished. Leader elected");
                break;
            }

            for (Runnable p: processes) {
                Process process = (Process) p;
                process.setGreenSignal(true);
            }

            /* wait till all processes have read their received messages */
            while (true) {
                flag = false;
                for (Runnable p: processes) {
                    Process process = (Process) p;
                    if (!process.getIsTerminated() && process.getCanExecuteRound()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    break;
                }
            }

            for (Runnable p: processes) {
                Process process = (Process) p;
                process.setGreenSignal(true);
            }

            while (true) {
                flag = false;
                for (Runnable r: processes) {
                    Process p = (Process) r;
                    if (!p.getIsTerminated() && p.getCanExecuteRound()) {
                        flag = true;
                        break;
                    }
                }

                /* no process is executing current round */
                if (!flag) {
                    break;
                }
            }
        }
        System.out.println("Total number of messages: ");

    }




}