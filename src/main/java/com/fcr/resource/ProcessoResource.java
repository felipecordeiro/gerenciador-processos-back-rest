package com.fcr.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		carregaUsuarios();
		processos.add((new Processo(1L, "Processo 1", "Descrição do Processo 1", true, usuarios)));
		processos.add((new Processo(2L, "Processo 2", "Descrição do Processo 2", false, null)));
		processos.add((new Processo(3L, "Processo 3", "Descrição do Processo 3", false, null)));

	}

	private void carregaUsuarios() {
		usuarios.add((new Usuario(1L, "finalizador1", "senhafinalizador1", "João", Funcao.USUARIO_FINALIZADOR)));
		usuarios.add((new Usuario(2L, "finalizador2", "senhafinalizador2", "Gabriela", Funcao.USUARIO_FINALIZADOR)));
		usuarios.add((new Usuario(3L, "finalizador3", "senhafinalizador3", "Maria", Funcao.USUARIO_FINALIZADOR)));
	}

	@GET
	public Response list() {
		return Response.ok(processos).build();
	}
}
