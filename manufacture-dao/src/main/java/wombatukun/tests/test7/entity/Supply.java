package wombatukun.tests.test7.entity;

import wombatukun.tests.test7.entity.enums.Result;

import javax.persistence.*;
import java.util.Date;

/**
 * Закупка материала
 */
@Entity
public class Supply {

	@Id	@GeneratedValue
	private Long id;

	/** дата-время операции */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	/** закупаемый материал */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "stuff_id", nullable = false)
	private Stuff stuff;

	/** количество материала */
	@Column(nullable = false)
	private Integer amount;

	/** закупочная стоимость единицы материала */
	@Column(nullable = false)
	private Double cost;

	/** результат обработки запроса */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Result result;

	public Supply() {
	}

	public Supply(Date date, Stuff stuff, Integer amount, Double cost, Result result) {
		this.date = date;
		this.stuff = stuff;
		this.amount = amount;
		this.cost = cost;
		this.result = result;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }

	public Stuff getStuff() { return stuff; }

	public void setStuff(Stuff stuff) { this.stuff = stuff; }

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	public Double getCost() { return cost; }

	public void setCost(Double cost) { this.cost = cost; }

	public Result getResult() { return result; }

	public void setResult(Result result) { this.result = result; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Supply{");
		sb.append("id=").append(id);
		sb.append(", date=").append(date);
		sb.append(", stuff=").append(stuff);
		sb.append(", amount=").append(amount);
		sb.append(", cost=").append(cost);
		sb.append(", result=").append(result);
		sb.append('}');
		return sb.toString();
	}

}
