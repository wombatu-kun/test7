package wombatukun.tests.test7.dto.responses;

import java.util.List;

/**
 * Информация о продукте
 */
public class ProductDto {

	/** название продукта */
	private String productName;

	/** состав продукта (какие материалы и в каком количестве нужны для производства единицы продукта)*/
	private List<StuffDto> composition;

	/** общая стоимость продукта (исходя из количества материалов и их цен)*/
	private Double totalCost;

	public ProductDto() {
	}

	public ProductDto(String productName, List<StuffDto> composition) {
		this.productName = productName;
		this.composition = composition;
		this.totalCost = composition.stream().mapToDouble(s -> s.getCost() * s.getAmount()).sum();
	}

	public String getProductName() { return productName; }

	public void setProductName(String productName) { this.productName = productName; }

	public List<StuffDto> getComposition() { return composition; }

	public void setComposition(List<StuffDto> composition) { this.composition = composition; }

	public Double getTotalCost() { return totalCost; }

	public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ProductDto{");
		sb.append("productName='").append(productName).append('\'');
		sb.append(", composition=").append(composition);
		sb.append(", totalCost=").append(totalCost);
		sb.append('}');
		return sb.toString();
	}

}
