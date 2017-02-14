<#assign text="${param}" />
<#assign vars=text?eval />
package com.${vars.packageName}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.${vars.packageName}.domain.${vars.beanName};

/**
 * ${vars.comment} Repository
 * @author ${vars.author}
 */
@Repository
public interface ${vars.beanName}Repository extends JpaRepository<${vars.beanName}, String>{

}