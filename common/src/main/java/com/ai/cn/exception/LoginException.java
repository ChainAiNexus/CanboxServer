package com.ai.cn.exception;

/**
 * @author  leigq
 * @date  2019/7/1 09:26
 * @description     
 */
public class LoginException extends RuntimeException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
