package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//vou incrementar o salario -> BaseSalary + ?
			//tem que sempre colocar uma restrição na atualização
			//vou passar dentro do prepareStatement
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET BaseSalary =  BaseSalary + ? "
					+ "WHERE "
					+ "(DepartmentId = ?)");
			//vou atualizar o salario onde o DepartmentId for igaul a um valor que eu infoormar -> ?
			//no caso, vai atualizar o salario de quem for do departamento 2
			//atualizando o salario. O setDouble indica que o q vai mudar é um valor Double
			//o 1 indica que é o primeiro interrogação, que eu vou mudar.
			//vou colocar 200.0 a mais do que já estava
			st.setDouble(1, 200.0);
			
			//vou mudar a 2a interrogação
			st.setInt(2, 2);
			
			//vai me dizer quantas linhas foram afetadas //tbm executei o camndo sql executeUpdate
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
   }
}
