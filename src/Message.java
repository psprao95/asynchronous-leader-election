
public class Message {
	private int maxSoFar;
	private int travelTime;
	String type;
	public Message(int uid, String type)
	{
		this.maxSoFar=uid;
		this.type=type;
		this.travelTime=(int)(Math.random()*10)+1;
	}
	
	public int getId()
	{
		return this.maxSoFar;
	}
	
	public int getTravelTime()
	{
		return this.travelTime;
	}
	
	public void decrementTravelTime()
	{
		this.travelTime--;
	}

}
