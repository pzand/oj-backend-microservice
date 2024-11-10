package com.pzand.ojbackendjudgeservice.judge.codesandbox.impl;

import com.pzand.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * 第三方提供的代码沙箱
 */
@Component("thirdParty")
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
