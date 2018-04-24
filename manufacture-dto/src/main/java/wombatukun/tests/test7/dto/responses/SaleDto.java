package wombatukun.tests.test7.dto.responses;

import java.util.Date;

public class SaleDto {

	private Date date;
	private String productName;
	private Integer amount;
	private Double cost;
	private Double totalCost;
	private String result;

	public SaleDto() {
	}

	public SaleDto(Date date, String productName, Integer amount, Double cost, String result) {
		this.date = date;
		this.productName = productName;
		this.amount = amount;
		this.cost = cost;
		this.result = result;
		this.totalCost = cost * amount;
	}

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }

	public String getProductName() { return productName; }

	public void setProductName(String productName) { this.productName = productName; }

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	public Double getCost() { return cost; }

	public void setCost(Double cost) { this.cost = cost; }

	public Double getTotalCost() { return totalCost; }

	public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }

	public String getResult() { return result; }

	public void setResult(String result) { this.result = result; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SaleDto{");
		sb.append("date=").append(date);
		sb.append(", productName='").append(productName);
		sb.append("', amount=").append(amount);
		sb.append(", cost=").append(cost);
		sb.append(", totalCost=").append(totalCost);
		sb.append(", result='").append(result).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
