package com.sophia.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sophia.constant.DataSourceEnum;
import com.sophia.constant.SQLDefineConstant;
import com.sophia.domain.SQLDefine;
import com.sophia.exception.ServiceException;
import com.sophia.service.MultipleDataSouceService;
import com.sophia.service.SQLDefineService;
import com.sophia.service.SQLIDService;
import com.sophia.utils.SQLLimitFactory;
import com.sophia.vo.Grid;
import com.sophia.vo.Limit;

/**
 *  
 * @author zkning
 */
@Service
public class SQLIDServiceImpl implements SQLIDService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired SQLDefineService sqlDefineService;
	@Autowired MultipleDataSouceService multipleDataSouceService;
	
	public <T> List<T> queryForList(String SQLID ,Object[] args,Class<T> elementType)  {
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.queryForList(selDefine.getSelectSql(), args, elementType);
	}
	
	public Map<String, Object> queryForMap(String SQLID, Object... args){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.queryForMap(selDefine.getSelectSql(),args);
	}
	
	public List<Map<String, Object>> queryForList(String SQLID, Object... args){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.queryForList(selDefine.getSelectSql(),args);
	}
	
	public <T> T queryForObject(String SQLID, Class<T> requiredType,Object... args){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.queryForObject(selDefine.getSelectSql(),requiredType,args);
	}
	
	public Integer count(String SQLID,Object... args){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.queryForObject(SQLLimitFactory.buildCountSQL(selDefine.getSelectSql()),Integer.class,args);
	}
	
	public <T> T execute(String SQLID, CallableStatementCallback<T> action){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		return jdbcTemplate.execute(selDefine.getSelectSql(),action);
	}
	
	public <T> Grid<T> findAll(String SQLID ,Object[] args,Class<T> elementType,Limit limit){
		SQLDefine selDefine = get(SQLID);
		JdbcTemplate jdbcTemplate = getTemplate(selDefine.getDatasource());
		Grid<T> grid = new Grid<T>();
		  try {
			  grid.setContent(jdbcTemplate.queryForList(SQLLimitFactory.createLimit(selDefine.getSelectSql(),limit, jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName()), args, elementType));
			  grid.setTotalElements(jdbcTemplate.queryForObject(SQLLimitFactory.buildCountSQL(selDefine.getSelectSql()),Integer.class,args));
			  grid.setPageNo(limit.getPageNo());
			  grid.setPageSize(limit.getPageSize());
		  } catch (SQLException e) {
			logger.error("分页查询异常:{}",e);
			throw new ServiceException("分页查询异常");
		} 
		return grid;
	}
	
	private SQLDefine get(String SQLId){
		
		if(StringUtils.isBlank(SQLId)){
			throw new ServiceException("SQLId不能为空");
		}

		SQLDefine sqlDefine = sqlDefineService.getRepository().findBySqlId(SQLId);

		if(sqlDefine == null){
			throw new ServiceException("SQLId未定义");
		}
		
		if(SQLDefineConstant.SQLDEFINE_STATUS_ACTIVE != sqlDefine.getStatus()){
			throw new ServiceException("SQLId状态未发布");
		}
		return sqlDefine;
	}
	
	private JdbcTemplate getTemplate(String dataSource){
		return multipleDataSouceService.getDataSourceByBean(DataSourceEnum.getDataSource(dataSource));
	}
}
