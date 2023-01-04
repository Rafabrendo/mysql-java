package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			conn = DB.getConnection();
			
			//Vou fazer com que o programa execute tudo ou nada.
			conn.setAutoCommit(false); //ou seja, não vai confirmar as operações automaticamente, ou seja
			//todas as operações vão ficar pendentes de uma confirmação explicita do programador.
			
			st = conn.createStatement();
			
			//Todo o vendedor que pertencer ao departamento 1, eu vou jogar o salario dele para 2090
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			//a galera do departamento 1, teve o salario atualizado. A do 2, não. Porque o programa
			//quebrou aqui.
			//int x = 1;
			//if (x < 2) {
			//	throw new SQLException("Fake error");
			//}
			
			//Todo vendedor do departamento 2 vai passar a ganhar 3090
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			//dps que eu fizer as duas operações(rows1 e rows2) eu chamo o commit();
			conn.commit();//o bloco acima vai estar protegido por uma logica de transação
			
			System.out.println("rows1 "+ rows1);
			System.out.println("rows2 "+ rows2);
			
		}
		//Caso aconteça algum erro, eu vou colocar uma lógica para voltar a aplicação, ao invés de quebrar
		//o meu programa.
		catch(SQLException e) {
			//e.printStackTrace();
			try {
				conn.rollback();
				//dps daqui eu lanço uma nova DbException
				throw new DbException("Transaction rolled back! Caused by: "+ e.getMessage());
			} catch (SQLException e1) {//Aqui é quando dá um erro no rollback, ou seja, eu tentei voltar
				//a aplicação e aconteceu um erro.
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
				//e1.printStackTrace();
			} //vai fazer com que volte a aplicação, volta ao estado inicial do banco
			//pode lançar uma exceção então eu corrigi com um try/catch
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
   }
}
