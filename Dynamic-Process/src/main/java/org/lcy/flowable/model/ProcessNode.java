package org.lcy.flowable.model;

import lombok.Data;
import org.lcy.flowable.enums.NodeCategoryEnum;

import java.io.Serializable;
import java.util.List;

/**
 * 流程节点.
 */
@Data
public class ProcessNode implements Serializable {


    /**
     * 节点名称.
     */
    private String name;

    /**
     * 节点类型.
     */
    private NodeCategoryEnum category;

    /**
     * 是否启用 0否 1是.
     */
    private Integer enable;

    /**
     * 办理人属性.
     */
    private AssigneeProps assigneeProps;

    /**
     * 表单列表.
     */
    private List<FormProp> formProps;

    /**
     * 子节点.
     */
    private ProcessNode children;

}
