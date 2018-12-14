package cn.freeexchange.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qiuliujun on 2018/8/13
 */
@Setter
@Getter
public class ApiResponse<T> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ApiResponse.class);

    public static final String CODE_SUCCESS = "000";
    public static final String CODE_ERROR = "999";

    public static final String MSG_SUCCESS = "成功";
    public static final String MSG_ERROR = "系统异常";

    private String responseCode;
    private String responseMsg;
    private T data;

    public ApiResponse() {
        this(CODE_SUCCESS, MSG_SUCCESS);
    }

    public ApiResponse(String responseCode, String responseMsg) {
        this(responseCode, responseMsg, null);
    }

    public ApiResponse(T data) {
        this(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    public ApiResponse(String responseCode, String responseMsg, T data) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.data = data;
    }

    public static ApiResponse success() {
        return new ApiResponse();
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(data);
    }

    public static ApiResponse failure(String responseCode, String responseMsg) {
        return new ApiResponse(responseCode, responseMsg);
    }

    public static ApiResponse failure(String responseMsg) {
        return new ApiResponse(CODE_ERROR, responseMsg);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return CODE_SUCCESS.equals(responseCode);
    }
}