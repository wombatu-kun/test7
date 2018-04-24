package wombatukun.tests.test7.dto.requests;

/**
 * Запрос на покупку продукта
 */
public class ConsumerRequest {

	/** название продукта */
	private String productName;

	/** количество единиц продукта */
	private Integer amount;

	public ConsumerRequest() {
	}

	public ConsumerRequest(String productName, Integer amount) {
		this.productName = productName;
		this.amount = amount;
	}

	public String getProductName() { return productName; }

	public void setProductName(String productName) { this.productName = productName; }

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ConsumerRequest{");
		sb.append("productName='").append(productName).append('\'');
		sb.append(", amount=").append(amount);
		sb.append('}');
		return sb.toString();
	}

}
