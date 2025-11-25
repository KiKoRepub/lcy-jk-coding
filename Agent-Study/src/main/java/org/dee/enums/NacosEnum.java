package org.dee.enums;

public enum NacosEnum {

    MCP("nacos-mcp-router")

    ;
    public final String value;
    NacosEnum(String value){
        this.value = value;
    }
}
