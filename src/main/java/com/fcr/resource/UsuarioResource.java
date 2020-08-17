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

import org.apache.commons.codec.digest.DigestUtils;

import com.fcr.modelo.enums.Funcao;
import com.fcr.modelo.to.Usuario;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UsuarioResource {

	private List<Usuario> usuarios = new ArrayList<>();

	public UsuarioResource() {
		carregaUsuarios();
	}
	private void carregaUsuarios() {
		// usado SHA256 para a password
		// password do administrador é "senha1"
		// password do triador é "senha2"
		usuarios.add((new Usuario(1L, "administrador", "a991e84c62a25c5a972f67c47cd81f31063c2dde905a8428977b0458073465cd", "João", Funcao.USUARIO_ADMINISTRADOR)));
		usuarios.add((new Usuario(2L, "triador", "02a3e1fc659a693124e09cc25a8b49249e126cbfa0dddf8f45d4dee4895bf81e", "Gabriela", Funcao.USUARIO_TRIADOR)));
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
		if (usuarios.size() > 0) {
			for (Usuario obj : usuarios) {
				if (usuario.getLogin().equals(obj.getLogin())) {
					return Response.status(Status.CONFLICT).build();
				}
			}
			long id = usuarios.get(usuarios.size() - 1).getId() + 1;
			usuario.setId(id);
		}
		else {
			usuario.setId(1L);
		}
		usuario.setPassword(DigestUtils.sha256Hex(usuario.getPassword()));
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
	
	@POST
	@Path("/validation")
	public Response verificaUsuarioValido(Usuario usuarioParam) {
		Response response = Response.status(Status.NOT_FOUND).build();
		String sha256hex = DigestUtils.sha256Hex(usuarioParam.getPassword());
		usuarioParam.setPassword(sha256hex);
		for (Usuario usuario : usuarios) {
			if (usuario.getLogin().equals(usuarioParam.getLogin()) && 
					usuario.getPassword().equals(usuarioParam.getPassword())) {
				Usuario obj = new Usuario();
				obj.setId(usuario.getId());
				obj.setLogin(usuario.getLogin());
				obj.setFuncao(usuario.getFuncao());
				obj.setNome(usuario.getNome());
				// volta usuário sem o hash da password, o hash permanece apenas no back
				response = Response.ok(obj).build();
				break;
			}
		}
		return response;
	}

}
