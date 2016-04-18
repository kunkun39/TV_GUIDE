package com.changhong.client.domain;

/**
 * User: Jack Wang
 * Date: 16-4-1
 * Time: 上午8:16
 */
public enum HandleMessage {

    MESSAGE_OK("处理成功"),
    MESSAGE_FAIL("处理失败"),
    MESSAGE_DATA_ERROR("数据有误"),
    MESSAGE_DATA_NOTFOUND("数据未找到"),
    MESSAGE_NO_AUTHORITY("没有权限");

    private String description;

    HandleMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
