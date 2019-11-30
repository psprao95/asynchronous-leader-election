import java.util.Queue;
import java.util.ArrayList;
import java.util.concurrent.*;
public class Channel {
	
	private DelayQueue<Message> messages;
	private Process process;
	
	
	public Channel(Process process)
	{
		this.process=process;
		
		messages=new DelayQueue<Message>();
	}
	
	public  void addMessage(Message message)
	{
		messages.offer(message);
	}
	
	public Process getProcess()
	{
		return process;
	}
	
	
	
	public ArrayList<Message> getMessages(int round)
	{
		ArrayList<Message> result=new ArrayList<Message>();
		try
		{if(!messages.isEmpty()) {
			result.add(messages.take());}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return result;
	}
	
	

}
