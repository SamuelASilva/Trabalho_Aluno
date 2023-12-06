var valores = [];
var idaluno = 0;

function novo() {
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

	//mostra o formulário
    form.style.display = "block";
    //esconde lista
    lista.style.display = "none";

	//lista os inputs
	idaluno = 0 ;
    var nome = document.getElementById("nome");
    var matricula = document.getElementById("matricula");
    var curso = document.getElementById("curso");
    var idade = document.getElementById("idade");
    nome.value="";
    matricula.value = "";
    curso.value = "";
    idade.value = "";

    nome.focus();
}

function alterar(i) {
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

	//mostra o formulário
    form.style.display = "block";
    //esconde lista
    lista.style.display = "none";

	//lista os inputs
	idaluno = valores[i].idaluno;
    var nome = document.getElementById("nome");
    var matricula = document.getElementById("matricula");
    var curso = document.getElementById("curso");
    var idade = document.getElementById("idade");
    nome.value = valores[i].nome;
    matricula.value = valores[i].matricula;
    curso.value = valores[i].curso;
    idade.value = valores[i].idade;

    nome.focus();
}

function salvar() {
	//valida os campos obrigatorios
	if (document.getElementById("nome").value == "") {
		alert("campo Nome é obrigatório");
		return;
	}
	if (document.getElementById("matricula").value == "") {
		alert("campo matricula é obrigatório");
		return;
	}
	if (document.getElementById("curso").value == "") {
		alert("campo curso é obrigatório");
		return;
	}
	if (document.getElementById("idade").value == "") {
		alert("campo idade é obrigatório");
		return;
	}
    var a = {
		idaluno: idaluno,
		nome: document.getElementById("nome").value,
	    matricula: document.getElementById("matricula").value,
	    curso: document.getElementById("curso").value,
	    idade: document.getElementById("idade").value
	}
	
	//define se o método será para inserir ou alterar
	if (idaluno == 0) {
		metodo = "POST";
	}else {
		metodo = "PUT";
	}
	
    //envia os dados para o servidor
    fetch("http://localhost:8080/Aluno/Aluno",
    	{method: metodo,
    	body: JSON.stringify(a)
    	}
    ) .then(resp => resp.json())
    .then(function (retorno ) {
		alert(retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");
    	
    	form.style.display = "none";
    	lista.style.display = "block";
    	
    	listar();
		});
	}
	
function excluir(i) {
	idaluno = valores[i].idaluno;
    //envia os dados para o servidor
    fetch("http://localhost:8080/Aluno/Aluno/" + idaluno,
    	{method: "DELETE",
    	}
    ) .then(resp => resp.json())
    .then(function (retorno ) {
		alert(retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");
    	
    	form.style.display = "none";
    	lista.style.display = "block";
    	
    	listar();
		});
	}
function cancelar() {
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    form.style.display = "none";
    lista.style.display = "block";
}

function listar() {
	var lista = document.getElementById("dados");
	lista.innerHTML = "<tr><td colspan=4>Aguarde, carregando...</td></tr>";
    fetch("http://localhost:8080/Aluno/Aluno")
    .then(resp => resp.json())
    .then(dados => mostrar(dados));
}
function mostrar(dados) {
	valores = dados;
    var  lista = document.getElementById("dados");
    lista.innerHTML = "";
    for (var i in dados) {
        lista.innerHTML += "<tr>"
                        +  "<td> " + dados[i].idaluno + "</td>"
                        +  "<td>" + dados[i].nome + "</td>"
                        +  "<td>" + dados[i].matricula + "</td>"
                        +  "<td>" + dados[i].curso + "</td>"
                        +  "<td>" + dados[i].idade + "</td>"
                        +  "<td>"
                        + "<button type='button' class='btn btn-primary rounded float-start' onclick='alterar(" + i + ")'>"
						+ "<span class='bi bi-pencil'></span> Alterar </button>"
						+ "<button type='button' class='btn btn-danger' onclick='excluir(" + i + ")'>"
						+ "<span class='bi bi-x-square'></span> Excluir </button>";
                        +  "</td>"
                        +  "</tr>";
    }
}
listar();