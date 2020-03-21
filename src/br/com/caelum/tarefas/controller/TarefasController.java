package br.com.caelum.tarefas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {
    
    private final JdbcTarefaDao dao;
    
    @Autowired
    public TarefasController(JdbcTarefaDao dao) {
        this.dao = dao;
    }

	// http://localhost:8080/fj21-tarefas/novaTarefa
	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}

	// http://localhost:8080/fj21-tarefas/adicionaTarefa
	@RequestMapping("adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		if (result.hasFieldErrors("descricao")) {
			return "tarefa/formulario";
		}
		dao.adiciona(tarefa);
		return "tarefa/adicionada";
	}

	// http://localhost:8080/fj21-tarefas/listaTarefas
	@RequestMapping("listaTarefas")
	public String lista(Model model) {
		model.addAttribute("tarefas", dao.lista());
		return "tarefa/lista";
	}

//	@RequestMapping("removeTarefa")
//	public String remove(Tarefa tarefa) {
//		JdbcTarefaDao dao = new JdbcTarefaDao();
//		dao.remove(tarefa);
//		return "redirect:listaTarefas";
//	}

	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/mostra";
	}

	@RequestMapping("alteraTarefa")
	public String altera(Tarefa tarefa) {
		dao.altera(tarefa);
		return "redirect:listaTarefas";
	}

	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model) {
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/finalizada";
	}

	@ResponseBody
	@RequestMapping("removeTarefa")
	public void remove(Long id) {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(id);
		dao.remove(tarefa);
	}
}
