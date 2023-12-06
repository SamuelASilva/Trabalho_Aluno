package org.libertas.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class AlunoDao {
	public void inserir (Aluno a) {
		Conexao con = new Conexao();
		try {
			String sql = "INSERT INTO alunos(nome, matricula,curso, idade) VALUES (?,?,?,?)";
			PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setString(1, a.getNome());
			prep.setInt(2, a.getMatricula());
			prep.setString(3,a.getCurso());
			prep.setInt(4, a.getIdade());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.desconectar();		}
	}
	public void alterar(Aluno a) {
	    if (a == null) {
	        System.out.println("Erro: O objeto Aluno passado como parâmetro é nulo.");
	        return;
	    }
	    Conexao con = new Conexao();
	    try {
	        String sql = "UPDATE alunos SET nome = ?, matricula = ?, curso = ?, idade = ? WHERE idaluno = ?";
	        PreparedStatement prep = con.getConnection().prepareStatement(sql);
	        prep.setString(1, a.getNome());
	        prep.setInt(2, a.getMatricula());
	        prep.setString(3, a.getCurso());
	        prep.setInt(4, a.getIdade());
	        prep.setInt(5, a.getIdaluno());
	        prep.execute();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        con.desconectar();
	    }
	}

	public void excluir (Aluno a) {
		Conexao con = new Conexao();
		try {
			String sql = "DELETE from alunos WHERE idaluno = ?";
			PreparedStatement prep = con.getConnection().prepareStatement(sql);
			prep.setInt(1, a.getIdaluno());
			prep.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.desconectar();		}
		
	}
	public Aluno consultar (int id) {
		Aluno a = new Aluno();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * from alunos where idaluno = " + id;
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			
			if (res.next()) {
				a.setIdaluno(res.getInt("idpessoa"));
				a.setNome(res.getString("nome"));
				a.setMatricula(res.getInt("matricula"));
				a.setCurso(res.getString("curso"));
				a.setIdade(res.getInt("idade"));
			} 
			} catch (Exception e) {
				e.printStackTrace();
			
		} finally {
			
		}
		return a;
	}
	public List<Aluno> Listar() {
		List<Aluno> lista = new LinkedList<Aluno>();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM alunos ORDER BY nome";
			Statement sta = con.getConnection().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				Aluno a = new Aluno();
				a.setIdaluno(res.getInt("idaluno"));
				a.setNome(res.getString("nome"));
				a.setMatricula(res.getInt("matricula"));
				a.setCurso(res.getString("curso"));
				a.setIdade(res.getInt("idade"));
				lista.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return lista;
	}
}
