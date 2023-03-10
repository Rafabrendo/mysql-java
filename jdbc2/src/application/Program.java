package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Connection conn =  null;
		Statement st = null;
		ResultSet rs = null;
		/*O Connection, o Statement e o ResultSet são recursos externos, que não são controlados 
		 * pela JVM do java, é interessante que feche esses recursos manualmente para evitar que
		 * o programa tenha algum tipo de vazamento de memória.
		 * */
		
		
		/*Esse programa foi no banco de dados, executou a consulta "select * from department", dps 
		 *retornou o resultado no resultset, dps eu percorri o resultset, que está como rs e imprimi
		 *os valores
		 * */
		try {
			conn = DB.getConnection();
			//Para conectar ao banco de dados
			
			st = conn.createStatement();
			//Instanciei um objeto do tipo statement. Statement serve para montar um comando SQL 
			//para ser executado, no caso, para recuperar os dados no banco de dados.
			
			rs = st.executeQuery("select * from department");
			//vai executar uma consulta ao banco de dados e vai trazer o resultado ao rs
			
			
			//o next vai retornar falso se estiver no último, por isso eu coloco o while
			while (rs.next()) {
				System.out.println(rs.getInt("Id")+ ", " + rs.getString("Name"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			//Pra fechar e não da nenhum problema de vazamento de dados.
			//rs.close(); 
			DB.closeResultSet(rs);
			//st.close(); //Ao invés de colocar esse comando, chama o metodo do DB
			DB.closeStatement(st);
			DB.closeConnection();
		}
		
	}

}
