package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	//SQLException é derivado da classe exception e se é obrigado a tratar. A DbException é derivada
	//da RuntimeException, então o programa não vai ficar, toda hora, colocando o try catch. Vou
	//colocar o try catch somente quando eu achar necessario. Por isso a exceção personalizada.
	
	
	//Esse connection vai ser o objeto de conexão com o banco de dados do jdbc
	//Escolha o connection do java.sql
	private static Connection conn = null;
	
	//Essa é a implementação completa do nosso metodo para gerar uma conexão com o banco de dados:
	
	//Metodo para conectar o banco de dados
	public static Connection getConnection() {
		//Se esse objeto ainda estiver valendo null, terei que escrever um codigo aqui para conectar
		//com o banco:
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
	//arquilo file -> db.properties
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
}
