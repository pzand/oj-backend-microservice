package com.pzand.ojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pzand.ojbackendcommon.common.ErrorCode;
import com.pzand.ojbackendcommon.exception.BusinessException;
import com.pzand.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 实际本项目需要远程调用的代码沙箱
 */
@Component("remote")
public class RemoteCodeSandbox implements CodeSandbox {

    // 简单的流量染色 请求头
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();

        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
//        System.out.println("远程代码沙箱");
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
