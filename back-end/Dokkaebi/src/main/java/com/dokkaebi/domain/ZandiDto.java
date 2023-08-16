package com.dokkaebi.domain;

import lombok.Data;

@Data
public class ZandiDto {
	private int id;
	private String date;
	private String studyTime;
	public ZandiDto(int id, String date, String studyTime) {
		this.id = id;
		this.date = date;
		this.studyTime = studyTime;
	}
}
