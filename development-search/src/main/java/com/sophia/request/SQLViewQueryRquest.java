package com.sophia.request;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sophia.vo.ConditionVo;

/**
 * 视图查询
 * @author zkning
 *
 */
public class SQLViewQueryRquest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SQLID
	 */
	private String sqlId;
	
	/**
	 * 分页数据
	 */
	private Integer pageSize = 20;

	/**
	 * 当前页
	 */
	private Integer pageNo = 0;
	
	/**
	 * 查询条件
	 */
	private List<ConditionVo> condition;
	
	/**
	 * 选中节点
	 */
	private JSONObject treeNode;

	public JSONObject getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(JSONObject treeNode) {
		this.treeNode = treeNode;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo - 1;
	}

	public List<ConditionVo> getCondition() {
		return condition;
	}

	public void setCondition(List<ConditionVo> condition) {
		this.condition = condition;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}
}
