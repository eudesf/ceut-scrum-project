package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class ItemBacklog extends Model {

	private static final long serialVersionUID = 5956536793054137692L;

	@Id
	public Long id;

	@ManyToOne
	public Produto produto;
	
	@Constraints.Required
	@Constraints.Max(60)
	public String nome;
	
	public String descricao;
	
	public String notas;
	
	public Integer importancia;
	
	public Integer estimativa;
	
	public static Finder<Long, ItemBacklog> find = new Finder<Long, ItemBacklog>(
			Long.class, ItemBacklog.class);

}
