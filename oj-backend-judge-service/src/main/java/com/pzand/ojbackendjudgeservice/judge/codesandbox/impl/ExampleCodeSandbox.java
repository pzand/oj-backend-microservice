package com.pzand.ojbackendjudgeservice.judge.codesandbox.impl;

import com.pzand.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.pzand.ojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.pzand.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 仅仅为了跑同业务流程
 */
@Component("example")
@Primary
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试用例执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
