package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;

import br.com.leandrocolevati.bancodedados.GenericDao;
import br.com.leandrocolevati.bancodedados.Sgbd;

public class LoginDao {

	Connection c;
	
	public LoginDao() throws SQLException{
		GenericDao gDao = new GenericDao("localhost", "sa", "1234", "testesession", true, Sgbd.SQLSERVER);
		c = gDao.getConnection();
	}
	
	public boolean validaLogin(Login l) throws SQLException {
		String sql = "SELECT COUNT(*) AS qtd FROM usuario WHERE nome = ? AND senha = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, l.getNome());
		ps.setString(2, l.getSenha());
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			if (rs.getInt("qtd") == 1){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Login retornaLogin(Login l) throws SQLException {
		String sql = "SELECT nome, senha, perfil FROM usuario WHERE nome = ? AND senha = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, l.getNome());
		ps.setString(2, l.getSenha());
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			l.setPerfil(rs.getInt("perfil"));
		} 
		return l;
	}
}
