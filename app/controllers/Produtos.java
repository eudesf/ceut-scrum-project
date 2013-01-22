package controllers;

import models.Produto;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.produtos.*;

public class Produtos extends Controller {

	static final Form<Produto> formProduto = form(Produto.class);
	
	public static Result blank() {
		return ok(form.render(formProduto, Produto.find.all()));
	}
	
	public static Result submit() {
		Form<Produto> formAux = formProduto.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(form.render(formAux, Produto.find.all()));
		} else {
			formAux.get().save();
			return ok(form.render(formProduto, Produto.find.all()));
		}
	}
	
	public static Result delete(Long id) {
		Produto.find.ref(id).delete();
		return blank();
	}
	
	public static Result edit(Long id) {
		return ok(form.render(formProduto.fill(Produto.find.ref(id)), Produto.find.all()));
	}
	
}
