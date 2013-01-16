package pl.edu.pjwstk.jps.parser;

/**
 * User: pawel
 * Date: 12.01.13
 * Time: 09:43
 */
public class ParserException extends Exception {
	public ParserException() {
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}
}
