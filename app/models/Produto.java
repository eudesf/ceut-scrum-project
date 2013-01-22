package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Produto extends Model {

	private static final long serialVersionUID = -3340486701161383872L;

	@Id
	public Long id;
	
	@Constraints.Required
	public String nome;
	
	public String descricao;

	public static Finder<Long, Produto> find = new Finder<Long, Produto>(
			Long.class, Produto.class);

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + "]";
	}

}
