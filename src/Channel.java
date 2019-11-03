import java.util.Queue;
import java.util.LinkedList;

public class Channel {
	
	private Queue<Message> messages;
	private int src;
	private int dest;
	
	public Channel(int src,int dest)
	{
		this.src=src;
		this.dest=dest;
		messages=new LinkedList<Message>();
	}
	
	public synchronized void send(Message message)
	{
		messages.add(message);
	}
	
	public synchronized Message receive()
	{
		Message m=messages.peek();
		
		if(m!=null &&m.getTravelTime()==0)
		{
			return messages.poll();
		}
		return null;
		
	}
	
	public synchronized boolean isEmpty()
	{
		return messages.isEmpty();
	}
	
	public synchronized void decrementTime()
	{
		for(Message message:messages)
		{
			if(message.getTravelTime()>0)
			{
				message.decrementTravelTime();
			}
		}
	}

}
