package com.fcr.resource;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fcr.modelo.enums.Funcao;
import com.fcr.modelo.to.Usuario;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UsuarioResource {

	private List<Usuario> usuarios = new ArrayList<>();
	
	public UsuarioResource() {

		usuarios.add((new Usuario(1L, "administrador", "senha1", "João", Funcao.USUARIO_ADMINISTRADOR)));
		usuarios.add((new Usuario(2L, "triador", "senha2", "Gabriela", Funcao.USUARIO_TRIADOR)));
		usuarios.add((new Usuario(3L, "finalizador", "senha3", "Maria", Funcao.USUARIO_FINALIZADOR)));
		
	}
	
	@GET
	public Response list() {
		return Response.ok(usuarios).build();
	}
}
