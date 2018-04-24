package wombatukun.tests.test7.dto.responses;

public class ErrorDto {

	private int code;
	private String message;

	public ErrorDto() {
	}

	public ErrorDto(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() { return code; }

	public void setCode(int code) { this.code = code; }

	public String getMessage() { return message; }

	public void setMessage(String message) { this.message = message; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ErrorDto{");
		sb.append("code=").append(code);
		sb.append(", message='").append(message).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
