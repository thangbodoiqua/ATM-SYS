package com.demo.dto;

import java.math.BigDecimal;

public class AtmDTO {
	private Long atmId;
	private String location;
	private String atmStatus;
	private BigDecimal cashAvailable;

	public Long getAtmId() {
		return atmId;
	}

	public void setAtmId(Long atmId) {
		this.atmId = atmId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAtmStatus() {
		return atmStatus;
	}

	public void setAtmStatus(String atmStatus) {
		this.atmStatus = atmStatus;
	}

	public BigDecimal getCashAvailable() {
		return cashAvailable;
	}

	public void setCashAvailable(BigDecimal cashAvailable) {
		this.cashAvailable = cashAvailable;
	}
}
