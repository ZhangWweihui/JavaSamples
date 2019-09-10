package com.zwh.mapper;

import com.zwh.model.DeploymentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ZhangWeihui
 * @date 2019/9/10 15:09
 */
public interface DeploymentMapper {

    @Select("select * from ACT_RE_DEPLOYMENT")
    List<DeploymentVO> queryAll();
}
