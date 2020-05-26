package com.zwh.mapper;

import com.zwh.model.ProcessDefinitionVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author ZhangWeihui
 * @date 2019/8/15 15:05
 */
public interface ProcessDefinitionMapper {

    List<ProcessDefinitionVO> query(Map<String, Object> params);

    @Select("select count(1) from ACT_RE_PROCDEF")
    int queryCount();

    @Select("select * from ACT_RE_PROCDEF where ID_ = #{id}")
    ProcessDefinitionVO queryById(@Param("id") String id);
}
