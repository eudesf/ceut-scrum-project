package models;

import javax.persistence.Id;
import javax.persistence.Entity;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Usuario extends Model{
	
	
	private static final long serialVersionUID = -4756474749281865286L;

	@Id
	public Long id;
	
	@Constraints.Required
	public String nome;
	
	
	public String grupo;
	
	public static Finder<Long, Usuario> find = new Finder<Long, Usuario>(
			Long.class, Usuario.class);
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", Grupo="
				+ grupo + "]";
	}
	
	
}
