package drizzt.sf.dao.support;

public class MsgException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public MsgException(String msg) {
		super(msg);
	}

	public MsgException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public String toString(){
		return getMessage();
	}
}
