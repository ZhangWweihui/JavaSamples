package com.zwh.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhangWeihui
 * @date 2019/9/10 15:04
 */
@Getter
@Setter
public class DeploymentVO implements Serializable {

    private static final long serialVersionUID = 4757500394751130678L;
    private String id;
    private String name;
    private String category;
    private String key;
    private String tenantId;
    private Date deployTime;
    private String engineVersion;
}
