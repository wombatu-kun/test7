package wombatukun.tests.test7.dto.responses;

import java.util.Date;

/**
 * Ответ о состоянии счёта
 */
public class AccountResponse {

	/** сумма на счёте */
	private Double sum;

	/** дата-время */
	private Date date;

	public AccountResponse() {
	}

	public AccountResponse(Double sum, Date date) {
		this.sum = sum;
		this.date = date;
	}

	public Double getSum() { return sum; }

	public void setSum(Double sum) { this.sum = sum; }

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AccountResponse{");
		sb.append("sum=").append(sum);
		sb.append(", date=").append(date);
		sb.append('}');
		return sb.toString();
	}

}
