/**
 * MYLLException class.
 * @author emilylemonier
 * @uni eql2001
 * 
 * Custom exception class that descends from Java's exception class
 * Goal: Express IP address not contained within list.
 * Checks if IP address is contained within list. If not- exception is thrown.
 *
 */
public class MYLLException extends Exception{
	
	String message;
	
	/**
	 * Default constructor
	 * Goal: initializes instance varibable to unknown
	 */
	public MYLLException()
	{
		super();
		message = "unknown";
	}
	
	/**
	 * Constructor receives some kind of emssage that is saved in an 
	 * instance variable.
	 * 
	 * @param err - message to be saved.
	 */
	public MYLLException(String err) {
		super(err);
		message = err;
	}
	
	/**
	 * Public method, callable by exception catcher.
	 * @return - error message.
	 */
	public String getError()
	{
		return message;
	}

}
