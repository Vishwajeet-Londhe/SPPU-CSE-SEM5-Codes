import java.util.Arrays;
import java.util.Scanner;

public class NextFit {

	static void NextFit(int blockSize[], int m, int processSize[], int n, int remblockSize[]) {

		int allocation[] = new int[n], j = 0;
		Arrays.fill(allocation, -1);
		for (int i = 0; i < n; i++) {
			int count = 0;
			while (count < m) {
				count++;
				if (blockSize[j] >= processSize[i]) {
					allocation[i] = j;
					blockSize[j] -= processSize[i];
					remblockSize[i] = blockSize[j];
					break;
				}
				j = (j + 1) % m;
				count += 1;
			}
		}

		System.out.println("\nProcess No.\tProcess Size\tBlock no.\tRemaninig Block Size");
		for (int i = 0; i < n; i++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if (allocation[i] != -1) {
				System.out.print((allocation[i] + 1) + "\t\t" + remblockSize[i]);
			} else {
				System.out.print("Not Allocated" + "\t" + remblockSize[i]);
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		int m, n, num;
		Scanner in = new Scanner(System.in);
		System.out.print("Enter how many number of blocks you want to enter:");
		m = in.nextInt();
		int blockSize[] = new int[m];
		int remblockSize[] = new int[m];
		for (int i = 0; i < m; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();
			blockSize[i] = num;
		}
		System.out.print("Enter how many number of process you want to enter:");
		n = in.nextInt();
		int processSize[] = new int[n];
		for (int i = 0; i < n; i++) {
			System.out.print("Enter Data " + (i + 1) + ":");
			num = in.nextInt();
			processSize[i] = num;
		}
		NextFit(blockSize, m, processSize, n, remblockSize);
		in.close();
	}

}
