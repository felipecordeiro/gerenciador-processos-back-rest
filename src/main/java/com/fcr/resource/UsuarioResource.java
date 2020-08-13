package com.fcr.resource;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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
	public Response listAll() {
		return Response.ok(usuarios).build();
	}

	@GET
	@Path("/{id}")
	public Response getUsuarioById(@PathParam(value = "id") Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		return usuarios.contains(usuario) ? Response.ok(usuarios.get(usuarios.indexOf(usuario))).build()
				: Response.status(Status.NOT_FOUND).build();
	}

	@POST
	public Response save(Usuario usuario) {
		long id = usuarios.get(usuarios.size() - 1).getId() + 1;
		usuario.setId(id);
		usuarios.add(usuario);
		return Response.status(Status.CREATED).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam(value = "id") Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		if (usuarios.contains(usuario)) {
			usuarios.remove(usuario);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	public Response update(Usuario usuario) {
		if (usuarios.contains(usuario)) {
			usuarios.get(usuarios.indexOf(usuario)).setLogin(usuario.getLogin());
			usuarios.get(usuarios.indexOf(usuario)).setNome(usuario.getNome());
			usuarios.get(usuarios.indexOf(usuario)).setPassword(usuario.getPassword());
			usuarios.get(usuarios.indexOf(usuario)).setFuncao(usuario.getFuncao());
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
