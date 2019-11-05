

public class Message {
	
	private final static int MIN_TIME=1;
	private final static int MAX_TIME=10;
	private int id;
	private int sender;
	private int senderParent;
	private int travelTime;
	private int deliveryRound;
	private MessageType messageType;
	
	
	public Message(int id, int sender,int lastExploreSenderParent,MessageType messageType)
	{
		this.id=id;
		this.sender=sender;
		this.senderParent=lastExploreSenderParent;
		this.messageType=messageType;
		generateTravelTime();
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
	
	public MessageType getType()
	{
		return this.messageType;
	}
	
	public int getDeliveryRound()
	{
		return this.deliveryRound;
	}
	
	public int getTravelTime()
	{
		return this.travelTime;
	}
	
	public void setSender(int sender)
	{
		this.sender=sender;
	}
	
	public void setTime(int time)
	{
		this.travelTime=time;
	}
	
	public void generateTravelTime()
	{
		this.travelTime=(int)(Math.random()*(MAX_TIME-MIN_TIME))+MIN_TIME;
		
	}
	
	public void setDeliveryRound(int deliveryRound)
	{
		this.deliveryRound=deliveryRound;
	}
	}
	
	


