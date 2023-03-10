package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	/*Vou acrescentar metodos para fechar o statement e o resultset para não precisar
	 * adicionar mais try/catch no Program.
	 * */
	private static Connection conn = null;
	

	public static Connection getConnection() {
		
		if(conn == null) {
			//coloquei um try porque o DriverManager pode dar um erro/exceção
			try {
				Properties props = loadProperties();//Peguei as propriedades do banco de dados
				//utilizando o loadProperties que eu havia feito abaixo.
				String url = props.getProperty("dburl");
				//é dburl porque é isso que está definido no arquivo db.properties //url do banco
				//ela vai vir pra uma variavel do tipo String
				conn = DriverManager.getConnection(url, props);
				//Vou passar a url do banco e as propriedades de conexão e vou atribuir isso tudo a 
				//minha variavel conn
				//Peguei as propriedades e a url e conectei ao banco de dados
				//Conectar com o banco de dados no JDBC é instanciar um objeto do tipo connection
				//Minha conexão está salva.
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		//tem que retornar o conn
		return conn;
	}
	
	//Implementação para fechar a conexão com o banco de dados
	
	public static void closeConnection() {
		//Tô testando se a minha conexão está instanciada, se sim: vou mandar fechar
		if(conn != null) {
			try { //Coloquei um try porque essa operação pode gerar um sqlException
				conn.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	
	
	//Vou criar um metodo auxiliar para carregar as propiedades que estão salvas no 
	//arquilo file -> dv.properties
	//basicamente eu terei que abrir o file db.properties, ler os dados e guardar no objeto
	//desse tipo aqui(Properties)
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			//recebeu como parametro o nome do arquivo. Como o arquivo está na pasta raiz do 
			//projeto, é só colocar o nome assim que vai dar certo
			Properties props = new Properties();
			props.load(fs);//Esse comando load vai fazer a leitura do arquivo, apontado pelo 
			//meu stream fs e vai guardar os dados dentro do objeto props
			//obs.: Essa operação pode dar algum erro.
			return props;
			
			
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
			//Minha exceção personalizada. Vai 'buscar' lá na minha classe de exceção que eu fiz
			
		}
	}
	//Fechamento do statement lançando uma exceção personalizada
	//Aceitei a correção do eclipse -> Sorround with try/catch
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage()) ;
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage()) ;
			}
		}
	}
	
}
