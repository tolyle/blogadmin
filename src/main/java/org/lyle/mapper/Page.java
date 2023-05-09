package org.lyle.mapper;

import lombok.Data;

import java.util.List;

@Data
public class Page {
	private Long startRow;
	private Long currentPage;
	private Long pageSize;
	private Long recordsTotal;
	private List data;

}
