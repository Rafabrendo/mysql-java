package db;

public class DbException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DbException(String msg) {
		super(msg);
		//um construtor que vai informar uma mensagem. Essa msg vai ser transmitida para a
		//super classe que é o RuntimeException
	}
	
}
