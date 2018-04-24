package wombatukun.tests.test7.dto.responses;

import java.util.Date;

/**
 * Информация о закупке материала
 */
public class SupplyDto {

	/** дата-время действия */
	private Date date;

	/** название закупаемого материала */
	private String stuffName;

	/** количество материала */
	private Integer amount;

	/** закупочная стоимость единицы материала */
	private Double cost;

	/** общая стоимость сделки */
	private Double totalCost;

	/** результат SUCCESS/FAILURE */
	private String result;

	public SupplyDto() {
	}

	public SupplyDto(Date date, String stuffName, Integer amount, Double cost, String result) {
		this.date = date;
		this.stuffName = stuffName;
		this.amount = amount;
		this.cost = cost;
		this.result = result;
		this.totalCost = cost * amount;
	}

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }

	public String getStuffName() { return stuffName; }

	public void setStuffName(String stuffName) { this.stuffName = stuffName; }

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
		final StringBuilder sb = new StringBuilder("SupplyDto{");
		sb.append("date=").append(date);
		sb.append(", stuffName='").append(stuffName);
		sb.append("', amount=").append(amount);
		sb.append(", cost=").append(cost);
		sb.append(", totalCost=").append(totalCost);
		sb.append(", result='").append(result).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
