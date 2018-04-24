package wombatukun.tests.test7.entity;

import wombatukun.tests.test7.entity.enums.Result;

import javax.persistence.*;
import java.util.Date;

/**
 * Продажа продукции
 */
@Entity
public class Sale {

	@Id	@GeneratedValue
	private Long id;

	/** дата-время операции */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	/** продаваемый продукт */
	@ManyToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	/** количество продукта	*/
	@Column(nullable = false)
	private Integer amount;

	/** результат обработки запроса */
	@Enumerated(EnumType.STRING)
	private Result result;

	public Sale() {
	}

	public Sale(Date date, Product product, Integer amount, Result result) {
		this.date = date;
		this.product = product;
		this.amount = amount;
		this.result = result;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }

	public Product getProduct() { return product; }

	public void setProduct(Product product) { this.product = product; }

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	public Result getResult() { return result; }

	public void setResult(Result result) { this.result = result; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Sale{");
		sb.append("id=").append(id);
		sb.append(", date=").append(date);
		sb.append(", product=").append(product);
		sb.append(", amount=").append(amount);
		sb.append(", result=").append(result);
		sb.append('}');
		return sb.toString();
	}

}
