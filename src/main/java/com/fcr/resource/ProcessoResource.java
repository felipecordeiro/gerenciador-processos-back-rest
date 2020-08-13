package com.fcr.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fcr.modelo.enums.Funcao;
import com.fcr.modelo.to.Processo;
import com.fcr.modelo.to.Usuario;

@Path("/processo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProcessoResource {

	private List<Processo> processos = new ArrayList<>();
	private List<Usuario> usuarios = new ArrayList<>();

	public ProcessoResource() {
		carregaProcessos();
	}

	private void carregaUsuarios() {
		usuarios.add((new Usuario(1L, "finalizador1", "senhafinalizador1", "João", Funcao.USUARIO_FINALIZADOR)));
		usuarios.add((new Usuario(2L, "finalizador2", "senhafinalizador2", "Gabriela", Funcao.USUARIO_FINALIZADOR)));
		usuarios.add((new Usuario(3L, "finalizador3", "senhafinalizador3", "Maria", Funcao.USUARIO_FINALIZADOR)));
	}
	
	private void carregaProcessos() {
		carregaUsuarios();
		processos.add((new Processo(1L, "Processo 1", "Descrição do Processo 1", true, usuarios)));
		processos.add((new Processo(2L, "Processo 2", "Descrição do Processo 2", false, null)));
		processos.add((new Processo(3L, "Processo 3", "Descrição do Processo 3", false, null)));
	}

	@GET
	public Response list() {
		return Response.ok(processos).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getProcessoById(@PathParam(value = "id") Long id) {
		Processo processo = new Processo();
		processo.setId(id);
		return processos.contains(processo) ? Response.ok(processos.get(processos.indexOf(processo))).build()
				: Response.status(Status.NOT_FOUND).build();
	}
	
	@POST
	public Response save(Processo processo) {
		long id = processos.get(processos.size() - 1).getId() + 1;
		processo.setId(id);
		processos.add(processo);
		return Response.status(Status.CREATED).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam(value = "id") Long id) {
		Processo processo = new Processo();
		processo.setId(id);
		if (processos.contains(processo)) {
			processos.remove(processo);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	public Response update(Processo processo) {
		if (processos.contains(processo)) {
			processos.get(processos.indexOf(processo)).setTitulo(processo.getTitulo());
			processos.get(processos.indexOf(processo)).setDescricao(processo.getDescricao());
			processos.get(processos.indexOf(processo)).setPendenteParecer(processo.getPendenteParecer());
			processos.get(processos.indexOf(processo)).setUsuarios(processo.getUsuarios());
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
