package com.zwh.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.zwh.mapper.ProcessDefinitionMapper;
import com.zwh.model.ProcessDefinitionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author ZhangWeihui
 * @date 2019/9/9 16:03
 */
@Slf4j
public class SqlSessionUtils {

    public static void main(String[] args) {
        try {
            SqlSession sqlSession = create();
            ProcessDefinitionMapper processDefinitionMapper = sqlSession.getMapper(ProcessDefinitionMapper.class);
            List<ProcessDefinitionVO> processDefinitionVOS = processDefinitionMapper.query(new HashMap<>());
            log.info("processDefinitionVOS = {}", JSON.toJSONString(processDefinitionVOS));

            int count = processDefinitionMapper.queryCount();
            log.info("count = {}", count);

            ProcessDefinitionVO processDefinitionVO = processDefinitionMapper.queryById("HSHC_ZIYING_CAPITAL_KEY:1:2504");
            log.info("processDefinitionVO = {}", JSON.toJSONString(processDefinitionVO));
        } catch (Exception e) {
            log.error("程序异常", e);
        }
    }

    public static SqlSession createByXML() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    public static SqlSession create() throws SQLException,IOException,ClassNotFoundException{
        DataSource dataSource = getDruidDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.getTypeAliasRegistry().registerAliases("com.zwh.model");
        Set<Class<?>> mappers = FindClassUtils.getClassesInPackage("com.zwh.mapper");
        if(!mappers.isEmpty()) {
            mappers.forEach(m -> configuration.addMapper(m));
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory.openSession();
    }

    private static DataSource getDruidDataSource() throws SQLException,IOException {
        Properties properties = Resources.getResourceAsProperties("dbconfig.properties");
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(properties.getProperty("url"));
        ds.setUsername(properties.getProperty("username"));
        ds.setPassword(properties.getProperty("password"));
        ds.setDriverClassName(properties.getProperty("driver"));
        ds.setFilters("stat");
        ds.setMaxActive(20);
        ds.setInitialSize(1);
        ds.setMaxWait(60000);
        ds.setMinIdle(1);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("SELECT 'x'");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setPoolPreparedStatements(true);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
        return ds;
    }
}
