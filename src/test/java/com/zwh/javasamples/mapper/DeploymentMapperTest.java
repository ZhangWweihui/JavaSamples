package com.zwh.javasamples.mapper;

import com.alibaba.fastjson.JSON;
import com.zwh.mapper.DeploymentMapper;
import com.zwh.model.DeploymentVO;
import com.zwh.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2019/9/10 15:12
 */
public class DeploymentMapperTest {

    private static DeploymentMapper mapper;

    @BeforeClass
    public static void setup() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.create().openSession();
        mapper = sqlSession.getMapper(DeploymentMapper.class);
    }

    @Test
    public void testQueryAll() {
        List<DeploymentVO> vos = mapper.queryAll();
        System.out.println(JSON.toJSONString(vos, true));
    }
}
