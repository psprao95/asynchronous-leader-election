import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.*;
public class Channel {
	
	private Queue<Message> messages;
	private Process process;
	private int dest;
	
	public Channel(Process process)
	{
		this.process=process;
		
		messages=new ConcurrentLinkedQueue<Message>();
	}
	
	public  void addMessage(Message message)
	{
		messages.add(message);
	}
	
	public Process getProcess()
	{
		return process;
	}
	
	
	
	public ArrayList<Message> getMessages(int round)
	{
		ArrayList<Message> result=new ArrayList<Message>();
		while(messages.size()>0 && messages.peek().getTime()<=round)
			{
				result.add(messages.poll());
			}
		
		return result;
	}
	
	

}
