package message;

public class KafkaClientException extends RuntimeException {
	private static final long serialVersionUID = 9006233203346699846L;
	
	public KafkaClientException() {
	}

	public KafkaClientException(String message) {
		super(message);
	}

	public KafkaClientException(Throwable cause) {
		super(cause);
	}

	public KafkaClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
