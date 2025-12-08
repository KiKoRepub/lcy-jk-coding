package org.dee.entity.vo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bouncycastle.pqc.jcajce.provider.lms.LMSSignatureSpi;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLoginVo implements Serializable {


    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "用户令牌")
    private String token;
    @Schema(description = "用户角色列表")
    private List<String> roles;
    @Schema(description = "备注")
    private String note;

}
