package com.maheshd.account.response;

public class SuccessReponse<T> extends AccountReponse {

	T result;

	public SuccessReponse(String message, T result) {
		super(message);
		this.result = result;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
