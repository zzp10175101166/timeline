package com.timeline.error;

public enum EmBussinessError implements CommonError{
    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(20001,"未知错误"),
    //10000开头为用户信息相关错误定义
    USER_NOT_EXIST(30001,"用户不存在"),
    USER_LOGIN_FAIL(30002,"用户邮箱或者密码不正确"),
    USER_NOT_LOGIN(30003,"用户还未登录"),
    USER_REGISTER_FAIL(40001,"该邮箱已经注册，请直接登录"),
    MESSAGE_SEND_FAIL(50001,"消息发送失败，内容不能为空")
    ;

    private int errorCode;
    private String errorMessage;

    EmBussinessError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public CommonError setErrorMessage(String message) {
        this.errorMessage = message;
        return this;
    }
}
