package wombatukun.tests.test7.dto.requests;

/**
 * Запрос на поставку материала
 */
public class SupplierRequest {

	/** название материала */
	private String stuffName;

	/** количество единиц материала */
	private Integer amount;

	/** стоимость единицы материала */
	private Double cost;

	public SupplierRequest() {
	}

	public SupplierRequest(String stuffName, Integer amount, Double cost) {
		this.stuffName = stuffName;
		this.amount = amount;
		this.cost = cost;
	}

	public String getStuffName() { return stuffName; }

	public void setStuffName(String stuffName) { this.stuffName = stuffName; }

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	public Double getCost() { return cost; }

	public void setCost(Double cost) { this.cost = cost; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SupplierRequest{");
		sb.append("stuffName='").append(stuffName).append('\'');
		sb.append(", amount=").append(amount);
		sb.append(", cost=").append(cost);
		sb.append('}');
		return sb.toString();
	}

}
