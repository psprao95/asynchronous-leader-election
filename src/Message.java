import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;

public class Message implements Delayed{
	
	private final static int MIN_TIME=1;
	private final int FACTOR=10_000;
	private final static int MAX_TIME=10;
	private int id;
	private int sender;
	private int senderParent;
	private LocalDateTime deliveryTime;
	private LocalDateTime deliveryRound;
	private MessageType messageType;
	private Process receiver;
	
	
	public Message(int id, int sender,int lastExploreSenderParent,MessageType messageType)
	{
		super();
		this.id=id;
		this.sender=sender;
		this.senderParent=lastExploreSenderParent;
		this.messageType=messageType;
		
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
	
	
	
	
	
	public void setSender(int sender)
	{
		this.sender=sender;
	}
	
	
	
	public void generateTime()
	{
		int r=(int)(Math.random()*(MAX_TIME-MIN_TIME))+MIN_TIME;
		this.deliveryTime=LocalDateTime.now().plusNanos(r*FACTOR);
		
	}
	
	public LocalDateTime getDeliveryTime()
	{
		return this.deliveryTime;
	}
	
	@Override
	public long getDelay(TimeUnit unit)
	{
		Long diff=LocalDateTime.now().until(deliveryTime,ChronoUnit.MILLIS);
		return unit.convert(diff,TimeUnit.MILLISECONDS);
	}
	
	@Override
	  public int compareTo(Delayed that) {
	    
	    return 0;
	  }
	
	
	}
	
	


