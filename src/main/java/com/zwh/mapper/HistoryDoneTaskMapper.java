package com.zwh.mapper;

import com.zwh.model.HistoryDoneTaskVO;

import java.util.List;
import java.util.Map;

/**
 * 查询任务
 * @author yyy
 * @date 2019/8/24 15:34
 */
public interface HistoryDoneTaskMapper {

    /**
     * 查询已办任务列表
     * @param requestParams
     * @return
     */
    List<HistoryDoneTaskVO> queryDoneTaskList(Map<String, String> requestParams);

    /**
     * 查询已结任务列表
     * @param requestParams
     * @return
     */
    List<HistoryDoneTaskVO> queryCompleteProcessTaskList(Map<String, String> requestParams);
}
