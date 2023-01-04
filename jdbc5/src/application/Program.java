package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//DELETAR DADOS
			//tenho que passar uma restrição(Obrigatorio!)
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE "
					+ "Id = ?");
			
			//primeiro eu passo qual '?' eu vou mudar, que vai ser o numero 1
			//dps eu escolho o valor //Eu escolhi o valor 5 que é o D1
			//st.setInt(1, 5);
			
			//vou tentar apagar o departamento de código 2
			st.setInt(1, 2); //Esse vai estourar o printStackTrace()
			
			
			//vai me dizer quantas linhas foram afetadas //tbm executei o camndo sql executeUpdate
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
			
		}
		catch(SQLException e) {
			//e.printStackTrace();
			throw new DbIntegrityException(e.getMessage()); //Minha exceção personalizada.
	/*Cannot delete or update a parent row: a foreign key constraint fails (`coursejdbc`.`seller`, CONSTRAINT `seller_ibfk_1` FOREIGN KEY (`DepartmentId`) REFERENCES `department` (`Id`))
	at application.Program.main(Program.java:43)*/
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
   }
}
