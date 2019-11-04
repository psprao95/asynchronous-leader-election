import java.util.ArrayList;
public class Message {
	
	
	private int id;
	private int sender;
	private int senderParent;
	private int travelTime;
	private MessageType messageType;
	
	
	public Message(int id, int sender,int lastExploreSenderParent,MessageType messageType)
	{
		this.id=id;
		this.sender=sender;
		this.senderParent=lastExploreSenderParent;
		this.messageType=messageType;
		this.travelTime=(int)(Math.random()*10)+1;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getSender()
	{
		return this.sender;
	}
	
	int getSenderParent()
	{
		return this.senderParent;
	}
	
	public MessageType getMessageType()
	{
		return this.messageType;
	}
	
	public int getTime()
	{
		return this.travelTime;
	}
	
	public void setTime(int time)
	{
		this.travelTime=time;
	}
	}
	
	


