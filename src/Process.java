import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class Process implements Runnable {
	private int uid;
	private int max_so_far;
	private ArrayList<Integer> neighbors;
	private static Map<Integer,Queue<Message>> receiveMap=new HashMap<Integer,Queue<Message>>();
	
	private static boolean leader_found=false;
	private static int leaderId;
	
	public Process(int uid,ArrayList<Integer> neighbors)
	{
		this.uid=uid;
		
		
		this.neighbors=neighbors;
		send(this.uid);
		this.max_so_far=uid;
	}
	
	public void run() 
	{
		try
		{
			
			
			while(leader_found==false) {
				Queue<Message> queue=receiveMap.get(this.uid);
				if(queue.peek()!=null)
				{
					int r=queue.poll().getId();
					
					if(r==this.uid)
					{
						leader_found=true;
						leaderId=r;
					}
					else if(r>max_so_far)
					{
						send(r);
					}
				}
				
		}
		System.out.println("Process "+uid+" : "+leaderId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void send(int id)
	{
		Message m=new Message(id);
		for(int i:this.neighbors)
		{
			
			if(receiveMap.get(i)!=null)
			{
				Queue<Message> q=receiveMap.get(i);
				q.add(m);
				receiveMap.replace(i,q);
				
			}
			else
			{
				Queue<Message> q=new LinkedList<Message>();
				q.add(m);
				receiveMap.put(i,q);
			}
			
		}
	}
	
	
	
	

}
