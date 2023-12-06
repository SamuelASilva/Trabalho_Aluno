package org.libertas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.libertas.bd.AlunoDao;

import com.google.gson.Gson;
/**
 * Servlet implementation class Aluno
 */
public class Aluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Aluno() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//out.print("Executando método GET");
		AlunoDao pdao = new AlunoDao();
		List<org.libertas.bd.Aluno> lista = pdao.Listar();
		Gson gson = new Gson();
		out.print(gson.toJson(lista));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String body = sb.toString();
			
			//transforma o parâmetro do body em objeto JSON
			Gson gson = new Gson();
			org.libertas.bd.Aluno a = gson.fromJson(body,org.libertas.bd. Aluno.class);
			
			//insere objeto no banco de dados
			AlunoDao pdao = new AlunoDao();
			pdao.inserir(a);
			
			Retorno r = new Retorno(true, "registro inserido com sucesso");
			out.print(gson.toJson(r));
		}	catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			String body = sb.toString();
			
			Gson gson = new Gson();
			org.libertas.bd.Aluno a = gson.fromJson(body,org.libertas.bd. Aluno.class);
			
			AlunoDao pdao = new AlunoDao();
			pdao.alterar(a);
			Retorno r = new Retorno(true,"Registro alterado com sucesso");
			out.print(gson.toJson(r));
		}	catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
				//pega o id passado por parâmetro
				String idaluno = request.getRequestURI();
				idaluno = idaluno.substring(idaluno.lastIndexOf("/") + 1);
				
				AlunoDao pdao = new AlunoDao();
				org.libertas.bd.Aluno a = new org.libertas.bd.Aluno();
				a.setIdaluno(Integer.parseInt(idaluno));
				pdao.excluir(a);
				
				Retorno r = new Retorno(true,"Registro excluido com sucesso");
				Gson gson = new Gson();
				out.print(gson.toJson(r));
		}	catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
	}
}
