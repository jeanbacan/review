package br.com.vivo.bcm.business.dto.filter;

import java.util.Map;

import br.com.vivo.bcm.business.search.SortType;

public class FilterDTO {

	public static final String FILTER_UF = "FILTER_UF";
	public static final String FILTER_OBRA = "FILTER_OBRA";
	public static final String FILTER_OWNER = "FILTER_OWNER";
	public static final String FILTER_FLUXO = "FILTER_FLUXO";
	public static final String FILTER_CIDADE = "FILTER_CIDADE";
	public static final String FILTER_TASK_NAME = "FILTER_TASK_NAME";
	public static final String FILTER_DIRETORIA = "FILTER_DIRETORIA";
	public static final String FILTER_OWNER_GROUP = "FILTER_OWNER_GROUP";
	public static final String FILTER_TECNOLOGIA = "FILTER_TECNOLOGIA";
	public static final String FILTER_BUSINESSKEY = "FILTER_BUSINESSKEY";
	public static final String FILTER_GROUPS = "FILTER_GROUPS";	
	
	private Page page = new Page();

	private Map<String, Constraint> constraints;

	private SortType sortType;

	private boolean onlyLastVersions;

	public boolean isOnlyLastVersions() {
		return onlyLastVersions;
	}

	public void setOnlyLastVersions(boolean onlyLastVersions) {
		this.onlyLastVersions = onlyLastVersions;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	public Map<String, Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(Map<String, Constraint> constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Get Constant value based on constants of FilterDTO
	 * @param key
	 * @return
	 */
	public String getConstantValue(String key) {
		if (this.getConstraints() != null){
			if (this.getConstraints().get(key) != null) {
				return this.getConstraints().get(key).getValue();
			}	
		}
		return null;
	}

}
