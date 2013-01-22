package controllers;

import java.util.List;

import models.ItemBacklog;
import models.Produto;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.backlog.*;

public class ItensBacklog extends Controller {

	public static Form<ItemBacklog> formItem = form(ItemBacklog.class);
	
	public static Result index(Long produtoId) {
		Produto produto = Produto.find.ref(produtoId);
		List<ItemBacklog> itens = ItemBacklog.find.where().eq("produto", produto).findList();
		
		return ok(index.render(produtoId, formItem, itens));
	}
	
	public static Result create(Long produtoId) {
		return TODO;
	}
}
