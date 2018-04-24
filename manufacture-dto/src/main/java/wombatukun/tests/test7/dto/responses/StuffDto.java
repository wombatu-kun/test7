package wombatukun.tests.test7.dto.responses;

public class StuffDto {

	private String stuffName;
	private Double cost;
	private Long amount;

	public StuffDto() {
	}

	public StuffDto(String stuffName, Double cost, Long amount) {
		this.stuffName = stuffName;
		this.cost = cost;
		this.amount = amount;
	}

	public String getStuffName() { return stuffName; }

	public void setStuffName(String stuffName) { this.stuffName = stuffName; }

	public Double getCost() { return cost; }

	public void setCost(Double cost) { this.cost = cost; }

	public Long getAmount() { return amount; }

	public void setAmount(Long amount) { this.amount = amount; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("StuffDto{");
		sb.append("stuffName='").append(stuffName).append('\'');
		sb.append(", cost=").append(cost);
		sb.append(", amount=").append(amount);
		sb.append('}');
		return sb.toString();
	}

}
