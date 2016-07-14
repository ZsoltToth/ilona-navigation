package uni.miskolc.ips.ilona.tracking.persist.exception;

/**
 * Base class for the tracking exceptions.
 * @author Patryk
 *
 */
public class TrackingDAOException extends Exception {

	/**
	 * A generate value for serializaton.
	 */
	private static final long serialVersionUID = -2109907583812109430L;

	public TrackingDAOException() {
		
	}
	
	public TrackingDAOException(String message) {
		super(message);
	}
	
	public TrackingDAOException(Throwable exception, String message) {
		super(message, exception);
	}
}