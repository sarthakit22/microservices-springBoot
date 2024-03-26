package com.account.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class ApiResponseDto {

	private final int statusCode;
	private final HttpStatus status;
	private final String message;
	private final Object data;

	private ApiResponseDto(ApiResponseDtoBuilder builder) {
		this.status = builder.status;
		this.statusCode = builder.status.value();
		this.message = builder.message;
		this.data = builder.data;

	}

	public static class ApiResponseDtoBuilder {
		private HttpStatus status;
		private String message;
		private Object data;

		public ApiResponseDtoBuilder() {
			super();
		}

		public ApiResponseDtoBuilder withMessage(Object object) {
			this.message = (String) object;
			return this;
		}

		public ApiResponseDtoBuilder withStatus(HttpStatus status) {
			this.status = status;
			return this;

		}

		public ApiResponseDtoBuilder withData(Object data) {
			this.data = data;
			return this;
		}

		public ApiResponseDto build() {
			return new ApiResponseDto(this);
		}

		public String getMessage() {
			return message;
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

}
