package controllers;

import models.Usuario;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.usuarios.*;

public class Usuarios extends Controller {
	
	static final Form<Usuario> formUsuario = form(Usuario.class);
	
	public static Result blank() {
		return ok(index.render(formUsuario, Usuario.find.all()));
	}
	
	public static Result submit() {
		
		Form<Usuario> formAux = formUsuario.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(index.render(formAux, Usuario.find.all()));
		} else {
			
			formAux.get().save();
			return ok(index.render(formUsuario, Usuario.find.all()));
		}
	}
	
	public static Result update(Long id) {
		Form<Usuario> formAux = formUsuario.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(index.render(formAux, Usuario.find.all()));
		} else {
			formAux.get().update(id);
			return ok(index.render(formUsuario, Usuario.find.all()));
		}
	}
	
	public static Result delete(Long id) {
		Usuario.find.ref(id).delete();
		return blank();
	}
	
	public static Result editForm(Long id) {
		return ok(editForm.render(id, formUsuario.fill(Usuario.find.ref(id)), Usuario.find.all()));
		
	}
}
