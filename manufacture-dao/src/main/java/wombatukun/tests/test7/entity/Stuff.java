package wombatukun.tests.test7.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Материал
 */
@Entity
public class Stuff {

	@Id	@GeneratedValue
	private Long id;

	/** название материала */
	@Column(unique = true, nullable = false)
	private String name;

	/** стоимость единицы материала при продаже продукта */
	@Column(nullable = false)
	private Double cost;

	/** общее количество материала в наличии */
	@Column(nullable = false)
	private Long amount;

	public Stuff() {
	}

	public Stuff(String name, Double cost, Long amount) {
		this.name = name;
		this.cost = cost;
		this.amount = amount;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Double getCost() { return cost; }

	public void setCost(Double cost) { this.cost = cost; }

	public Long getAmount() { return amount; }

	public void setAmount(Long amount) { this.amount = amount; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Stuff stuff = (Stuff) o;

		return id != null ? id.equals(stuff.id) : stuff.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Stuff{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", cost=").append(cost);
		sb.append(", amount=").append(amount);
		sb.append('}');
		return sb.toString();
	}
}
