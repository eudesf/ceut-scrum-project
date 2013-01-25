package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ItemBacklog;
import models.Produto;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.backlog.breakItemForm;
import views.html.backlog.editForm;
import views.html.backlog.index;

public class ItensBacklog extends Controller {

	public static Form<ItemBacklog> formItem = form(ItemBacklog.class);
	public static String lastOrderBy = "nome asc";
	public static final String BREAK_ITEMS_KEY = "break-items";
	public static List<ItemBacklog> itensToAdd = new ArrayList<ItemBacklog>();

	private static List<ItemBacklog> listaItensBacklog(Long produtoId) {
		Produto produto = Produto.find.ref(produtoId);
		return ItemBacklog.find.where().eq("produto", produto).orderBy(lastOrderBy).findList();
	}
	
	public static Result index(Long produtoId, String orderBy) {
		lastOrderBy = orderBy;
		List<ItemBacklog> itens = listaItensBacklog(produtoId);
		return ok(index.render(produtoId, formItem, itens));
	}
	
	public static Result create(Long produtoId) {
		Form<ItemBacklog> formAux = formItem.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(index.render(produtoId, formAux, listaItensBacklog(produtoId)));
		} else {
			ItemBacklog item = formAux.get();
			item.produto = Produto.find.ref(produtoId);
			item.save();
			return ok(index.render(produtoId, formItem, listaItensBacklog(produtoId)));
		}
	}
	
	public static Result delete(Long id) {
		ItemBacklog item = ItemBacklog.find.ref(id);
		Long produtoId = item.produto.id;
		item.delete();
		return index(produtoId, lastOrderBy);
	}
	
	public static Result editForm(Long id) {
		return ok(editForm.render(id, formItem.fill(ItemBacklog.find.ref(id))));
	}
	
	public static Result update(Long id) {
		Long produtoId = ItemBacklog.find.ref(id).produto.id;
		Form<ItemBacklog> formAux = formItem.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(editForm.render(id, formAux));
		} else {
			ItemBacklog item = formAux.get();
			item.produto = Produto.find.ref(produtoId);
			item.update(id);
			return index(produtoId, lastOrderBy);
		}
	}
	
	public static Result breakItemForm(Long id) {
		ItemBacklog item = ItemBacklog.find.byId(id);
		return ok(breakItemForm.render(item, formItem));
	}

	public static Result breakItem(Long id) {
		ItemBacklog itemBacklog = ItemBacklog.find.byId(id);
		Produto produto = itemBacklog.produto;
		BreakItemsForm formItens = form(BreakItemsForm.class).bindFromRequest().get();
		for (ItemBacklog item : formItens.itens) {
			item.produto = produto;
			item.save();
		}
		itemBacklog.delete();
		return ItensBacklog.index(produto.id, lastOrderBy);
	}
	
	public static Result fake() {
		return TODO;
	}
}
