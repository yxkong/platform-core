package com.github.platform.core.standard.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用分页返回实体,使用ResultBean<PageBean<T>>
 *   使用SuperBuilder解决Builder 无法继承的问题
 * @author yxkong
 * @param <T>
 */
//@Schema(description = "分页data，作为ResultBean的泛型")
@Data
@SuperBuilder
@NoArgsConstructor
public class PageBean<T>  {
	public static Long EMPTY_TOTAL_SIZE = 0L;
	public static Integer EMPTY_PAGES = 0;
	public static Integer EMPTY_PAGE_NUM = 1;
	public static Integer EMPTY_PAGE_SIZE = 20;

	private static final long serialVersionUID = -7602280271453240278L;
	@Schema(description = "当前页")
	private Integer pageNum;
	@Schema(description = "总条数")
	private Long totalSize;
	@Schema(description = "总页数")
	private Integer pages;
	@Schema(description = "每页的数量")
	private Integer  pageSize;
	@Schema(description = "分页数据")
	private List<T> data;

	public static PageBean defaultPage(){
		PageBean pageBean=new PageBean();
		pageBean.setPages(PageBean.EMPTY_PAGES);
		pageBean.setTotalSize(PageBean.EMPTY_TOTAL_SIZE);
		pageBean.setPageNum(PageBean.EMPTY_PAGE_NUM);
		pageBean.setPageSize(PageBean.EMPTY_PAGE_SIZE);
		pageBean.setData(new ArrayList());
		return pageBean;
	}
	public PageBean(PageBean pageBean,List<T> data){
		this.pageNum = pageBean.getPageNum();
		this.totalSize = pageBean.getTotalSize();
		this.pages = pageBean.getPages();
		this.pageSize = pageBean.getPageSize();
		this.data = data;
	}

	public PageBean(Integer pageNum, Long totalSize, Integer pages, Integer pageSize, List<T> data) {
		this.pageNum = pageNum;
		this.totalSize = totalSize;
		this.pages = pages;
		this.pageSize = pageSize;
		this.data = data;
	}
	public PageBean(Integer pageNum, Long totalSize, Integer pageSize, List<T> data) {
		this.pageNum = pageNum;
		this.totalSize = totalSize;
		this.pages = Math.toIntExact((totalSize / pageSize) + 1);
		this.pageSize = pageSize;
		this.data = data;
	}

	/**
	 * 特殊定制，前端不分页
	 * @param data
	 */
	public PageBean( List<Map<String,Object>> data) {
		this.pageNum = 1;
		this.pages = 1;
		if( null != data){
			this.data = (List<T>) data;
			this.pageSize = data.size();
			this.totalSize = Long.valueOf(data.size());
		}

	}
}
