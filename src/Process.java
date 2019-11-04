import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class Process implements Runnable {
	private int uid;
	private int max_so_far;
	
	private Status status;
	private int round;
	private Process parent;
	private int grandParent;
	ArrayList<Integer> children;
	ArrayList<Channel> channels;
	ArrayList<Message> receivedMessages;
	
	
	
	private int pendingAck;
	private boolean ackReturned;
	
	private boolean leader_found;
	private volatile boolean isTerminated;
	private volatile boolean canBeginRound;
	
	
	public Process(int uid)
	{
		this.uid=uid;
		this.status=Status.UNKNOWN;
		
		this.round=1;
		this.channels=new ArrayList<Channel>();
		this.isTerminated=false;
		this.leader_found=false;
		this.canBeginRound=false;
		//this.neighbors=neighbors;
		this.parent=null;
		this.grandParent=-1;
		this.children=new ArrayList<Integer>();
		this.max_so_far=uid;
	}
	
	public int getId()
	{
		return uid;
	}
	
	
	public boolean getCanExecuteRound()
	{
		return this.canBeginRound;
	}
	
	public boolean getIsTerminated()
	{
		return this.isTerminated;
	}
	
	public void setCanExecuteRound(boolean b)
	{
		this.canBeginRound=b;
	}
	
	public void addChannel(Process neighbor)
	{
		channels.add(new Channel(neighbor));
	}
	
	
	public void flood(Message message)
	{
		for(Channel channel:channels)
		{
			if(!channel.getProcess().equals(this.parent))
			{
				transmitMessage(channel.getProcess(),message);
			}
		}
	}
	
	public void transmitMessage(Process receiver,Message message)
	{
		int travelTime=(int)(Math.random()*10)+1;
		int time=round+travelTime;
		message.setTime(time);
		System.out.println("Message type: "+message.getMessageType()+" Content: "+message.getId()+" From: Process "+this.uid+" To: Process "+receiver.getId()+" Time STamp: "+message.getTime());
		receiver.putMessage(message);
		
	}
	
	public void putMessage(Message message)
	{
		int sender=message.getSender();
		Channel channel=getChannel(sender);
		if(this.getChannel(sender)!=null)
		{
			channel.addMessage(message);
		}
	}
	
	public Channel getChannel(int id)
	{
		for(Channel channel:channels)
		{
			if(channel.getProcess().getId()==id)
			{
				return channel;
			}
		}
		return null;
	}
			
	@Override
	public void run() 
	{
		try
		{
			
			
			while(true) {
				while(canBeginRound==false)
				{
					try
					{
						Thread.sleep(10);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				
				receivedMessages=new ArrayList<Message>();
				for(Channel channel:channels)
				{
					receivedMessages.addAll(channel.getMessages(round));
				}
				
				setCanExecuteRound(false);
				
				// Wait till all processes read their received messages
				while(canBeginRound==false)
				{
					try
					{
						Thread.sleep(10);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				//System.out.println("Process "+this.uid+" : I am participating in round"+round);
				
				
				
				if(round==1)
				{
					Message message=new Message(this.uid,this.uid,-1,MessageType.EXPLORE);
					pendingAck=channels.size();
					ackReturned=false;
					flood(message);
					
				}
				else {
				/* check for leader found message */
					for(Message message:receivedMessages)
					{
						if(message.getMessageType().equals(MessageType.LEADER)&& this.status.equals(Status.UNKNOWN))
						{
							this.status=Status.NON_LEADER;
							//this.parent=getChannel(message.getSender()).getProcess();
							message.setSender(this.uid);
							flood(message);
							leader_found=true;
							System.out.println("Process "+this.uid+" Process "+message.getId()+" is the leader");
							
						}
					}
					
					if(!leader_found)
					{
						boolean newInfo=false;
						for(Message message:receivedMessages)
						{
							if(message.getMessageType().equals(MessageType.EXPLORE) && message.getId()>this.max_so_far)
							{
								newInfo=true;
								this.max_so_far=message.getId();
								
								this.ackReturned=false;
								this.parent=getChannel(message.getSender()).getProcess();
								this.grandParent=message.getSenderParent();
								
							}
						}
						
						if(newInfo)
						{
							Message message=new Message(max_so_far,uid,this.parent.getId(), MessageType.EXPLORE);
							//this.ackReturned=false;
							if(this.parent!=null)
							{
								this.pendingAck=channels.size()-1;
							}
							else
							{
								this.pendingAck=channels.size();
							}
							flood(message);
						}
						
						for(Message m:receivedMessages)
						{
							if(m.getMessageType().equals(MessageType.EXPLORE) && m.getId()<=max_so_far)
							{
								int sender=m.getSender();
								Channel channel=getChannel(sender);
								Process neighbor=channel.getProcess();
								Message reject=new Message(this.max_so_far,this.uid,m.getSenderParent(),MessageType.NACK);
								transmitMessage(neighbor,reject);
								
							}
							
							if(m.getMessageType().equals(MessageType.NACK) || m.getMessageType().equals(MessageType.ACK))
							{
								if(this.parent!=null && this.parent.getId()==m.getSenderParent())
								{
									this.pendingAck--;
								}
								else if(parent==null && m.getSenderParent()==-1)
								{
									this.pendingAck--;
								}
								
								
								
								
								
								if(!ackReturned && pendingAck==0 && this.status.equals(Status.UNKNOWN) && parent!=null)
								{
									ackReturned=true;
									Message accept=new Message(this.max_so_far,this.uid,this.grandParent,MessageType.ACK);
									transmitMessage(this.parent,accept);
									System.out.println("Process "+this.uid+ " children: ");
									
								}
							}

						}
						
						if(pendingAck==0 && this.parent==null &&this.status.equals(Status.UNKNOWN))
						{
							this.status=Status.LEADER;
							this.leader_found=true;
							Message leader_broadcast=new Message(this.uid,this.uid,-1,MessageType.LEADER);
							System.out.println("Process "+this.uid+": I am leader");
							flood(leader_broadcast);
						}
						
					}
				}
				
				round++;
				
				if(leader_found)
				{
					this.isTerminated=true;
					setCanExecuteRound(false);
					System.out.println("Process "+this.uid+" is done");
					break;
				}
				
				setCanExecuteRound(false);
						
			}
							
						
					
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
					
				
				
		
	}
	
	
	
	
	
	


