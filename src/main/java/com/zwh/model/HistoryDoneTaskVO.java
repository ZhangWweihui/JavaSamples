package com.zwh.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yyy
 * @date 2019/8/24 15:41
 */
@Getter
@Setter
@Alias("HistoryDoneTaskVO")
public class HistoryDoneTaskVO implements Serializable {

    private static final long serialVersionUID = 6240510164709999642L;
    private String id;
    private String url;
    private String key;
    private int version;
    private String name;
    private String description;
    private String tenantId;
    private String deploymentId;
    private String deploymentUrl;
    private String resource;
    private String diagramResource;
    private String category;
    private boolean graphicalNotationDefined = false;
    private boolean suspended = false;
    private boolean startFormDefined = false;
    private String taskDefinitionKey;
    private String processInstanceId;
    private Date startTime;
    private Date endTime;

}
