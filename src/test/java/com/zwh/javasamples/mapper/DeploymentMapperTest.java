package com.zwh.javasamples.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.zwh.mapper.DeploymentMapper;
import com.zwh.model.DeploymentVO;
import com.zwh.utils.SqlSessionUtils;
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
        SqlSession sqlSession = SqlSessionUtils.create();
        mapper = sqlSession.getMapper(DeploymentMapper.class);
    }

    @Test
    public void testQueryAll() {
        List<DeploymentVO> vos = mapper.queryAll();
        System.out.println(JSON.toJSONString(vos, true));
    }
}
