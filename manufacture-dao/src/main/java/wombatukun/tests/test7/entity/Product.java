package wombatukun.tests.test7.entity;

import javax.persistence.*;
import java.util.Map;

/**
 * Продукт
 */
@Entity
public class Product {

	@Id	@GeneratedValue
	private Long id;

	/** название продукта */
	@Column(unique = true, nullable = false)
	private String name;

	/** состав продукта (используемые материалы и их количество) */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="COMPOSITION", joinColumns=@JoinColumn(name="PRODUCT"))
	@Column(name="AMOUNT")
	@MapKeyJoinColumn(name="STUFF", referencedColumnName="ID")
	private Map<Stuff, Integer> composition;

	public Product() {
	}

	public Product(String name, Map<Stuff, Integer> composition) {
		this.name = name;
		this.composition = composition;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Map<Stuff, Integer> getComposition() { return composition; }

	public void setComposition(Map<Stuff, Integer> composition) { this.composition = composition; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Product{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", composition=").append(composition);
		sb.append('}');
		return sb.toString();
	}
}
