package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		//Vou fazer uma operação para inserir um novo vendedor(seller)
		
		//Para instanciar uma data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = null;
		PreparedStatement st = null;
		//É um objeto que permite montar a consulta SQL deixando os paramêtros para colocar dps.
		
		try {
			//Primeiro tem que conectar ao banco
			conn = DB.getConnection();
			/*
			
			//Vai receber uma string que vai ser o comando sql. Vou colocar o comando de inserção
			//de dados e dps colorar os campos a serem preenchidos.
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES"
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					); //Coloquei o Statement.ReturnGeneratedKeys para recuperar o id do novo objeto inserido
			//Isso é uma sobrecarga do prepareStatement. Não é mais inserção simples.
			//(?, ?, ?, ?, ?) -> vai ser como placeholders e indicar onde deve ser colocado cada dado.
			
			//vou substituir cada uma das interrogações por valor q vou passar
			st.setString(1, "Carl Purple"); //Vou trocar a minha primeira ? , que vai ser representada
			//com o numero 1 e passar o valor, que nesse caso é Carl Purple
			st.setString(2, "cal@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime())); //Pra dar certo com o Date do sql, tem que colocar o .getTime()
			//para instanciar uma data para o JDBC tem que colocar o java.sql.Date
			//Estou acostumado a usar o java.util.Date para o resto, que envolda Data.
			//tem que usar o getTime
			
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			*/
			
			//Passando mais de um objeto. Vou inserir dois departamentos(D1 e D2) da tabela de departamentos:
			st = conn.prepareStatement(
					"insert into department (Name) values ('D1'),('D2')",
					Statement.RETURN_GENERATED_KEYS);
			//inseriu o Id 5 e 6.
			
			int rowsAffected = st.executeUpdate();
			//O resultado da operação é um numero inteiro indicando quantas linhas foram alteradas
			//ação para alterar os dados
			
			//System.out.println("Done! Rows affected: " + rowsAffected);
			
			if(rowsAffected > 0){
				//getGeneratedKeys retorna um objeto do tipo ResultSet
				ResultSet rs = st.getGeneratedKeys();
				//Esse resultset pode ter mais de um valor. Nesse caso eu estou inserindo apenas um 
				//vendedor(seller) mas eu posso inserir vários objetos ao mesmo tempos
				while(rs.next()) {
					int id = rs.getInt(1); //vai pegar o id do rs, do novo vendedor inserido
					//1 indica o valor da primeira coluna -> lá tem 6 colunas.
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No rows affected!");
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace(); //Imprime na tela a msg com qual erro pode ter dado
		}
		/*catch(ParseException e) {
			e.printStackTrace();
		}*/
		finally{
			DB.closeStatement(st);
			DB.closeConnection(); //Sempre fecha a conexão por último
	 }
   }
}
