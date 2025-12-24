package org.lcy.flowable.model;

import java.io.Serializable;
import lombok.Data;
import org.lcy.flowable.enums.AssigneeTypeEnum;

/**
 * 办理人Props.
 */
@Data
public class AssigneeProps implements Serializable {

    /**
     * 办理人类型.
     */
    private AssigneeTypeEnum assigneeType;

    /**
     * 候选办理人类型.
     */
    private AssigneeTypeEnum candidateType;
}
