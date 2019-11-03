import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class LeaderElection {

	
	
	public static void main(String[] args) throws Exception
	{
		Scanner scanner=new Scanner(new File("/users/psprao/downloads/connectivity.txt"));
		
		int n=scanner.nextInt();
		System.out.println("Number of processes: "+n);
		ArrayList<Integer> uids=new ArrayList<Integer>();
		System.out.print("UIDs of processes: ");
		for(int i=0;i<n;i++)
		{
			uids.add(scanner.nextInt());
			System.out.print(uids.get(i)+" ");
		}
		
		System.out.println("\n");
		
		
		ArrayList<ArrayList<Integer>> neighborsList=new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<n;i++)
		{
			ArrayList<Integer> arr=new ArrayList<Integer>();
			System.out.print("Neighbors of process "+i+": ");
			for(int j=0;j<n;j++)
			{
				int k=scanner.nextInt();
				
				if(k==1)
				
				{
				System.out.print(uids.get(j)+" ");
				arr.add(uids.get(j));
			}
				}
			System.out.print("\n");
			neighborsList.add(arr);
				
		}
		
		
		/*for(int i=0;i<n;i++)
		{
			Process process=new Process(uids.get(i),neighborsList.get(i));
			new Thread(process).start();
		}*/
		
		
		scanner.close();
	}
}
