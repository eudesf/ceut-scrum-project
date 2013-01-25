package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.ItemBacklog;
import models.Produto;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.backlog.*;

public class ItensBacklog extends Controller {

	public static Form<ItemBacklog> formItem = form(ItemBacklog.class);
	public static String lastOrderBy = "nome asc";
	public static final String BREAK_ITEMS_KEY = "break-items";
	
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
		session().remove(BREAK_ITEMS_KEY);
		ItemBacklog item = ItemBacklog.find.ref(id);
		System.out.println(item);
		return ok(breakItemForm.render(item, formItem, obtemItensQuebrados()));
	}

	private static List<ItemBacklog> obtemItensQuebrados() {
		String itensSession = session(BREAK_ITEMS_KEY);
		if (itensSession == null) {
			return new ArrayList<ItemBacklog>();
		}
		List<String> itensId = Arrays.asList(itensSession.split(","));
		List<ItemBacklog> result = new ArrayList<ItemBacklog>();
		for (String id : itensId) {
			result.add(ItemBacklog.find.ref(Long.valueOf(id)));
		}
		return result;
	}
	
	public static Result breakItem(Long id) {
		ItemBacklog itemBacklog = ItemBacklog.find.ref(id);
		Long produtoId = itemBacklog.produto.id;
		Form<ItemBacklog> formAux = formItem.bindFromRequest();
		if (formAux.hasErrors()) {
			return badRequest(index.render(produtoId, formAux, obtemItensQuebrados()));
		} else {
			ItemBacklog item = formAux.get();
			item.produto = Produto.find.ref(produtoId);
			item.save();
			String itens = session(BREAK_ITEMS_KEY);
			if (itens == null) {
				session(BREAK_ITEMS_KEY, String.valueOf(item.id));
			} else {
				session(BREAK_ITEMS_KEY, itens + "," + item.id);
			}
			return ok(breakItemForm.render(itemBacklog, formItem, obtemItensQuebrados()));
		}
	}
}
