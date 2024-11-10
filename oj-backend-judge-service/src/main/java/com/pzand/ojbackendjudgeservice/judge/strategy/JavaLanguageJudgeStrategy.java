package com.pzand.ojbackendjudgeservice.judge.strategy;

import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.pzand.ojbackendmodel.model.dto.question.JudgeCase;
import com.pzand.ojbackendmodel.model.dto.question.JudgeConfig;
import com.pzand.ojbackendmodel.model.enums.JudgeInfoMessageEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("java")
public class JavaLanguageJudgeStrategy implements JudgeStrategy {

    private final long JAVA_PROGARM_COST = 10000L;

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeConfig expectedJudgeConfig = judgeContext.getExpectedJudgeConfig();
        JudgeInfo actualJudgeInfo = judgeContext.getJudgeInfo();

        // 判题
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(actualJudgeInfo.getTime());
        judgeInfoResponse.setMemory(actualJudgeInfo.getMemory());
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());

        // 答案比对
        if (outputList.size() != judgeCaseList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
//            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            judgeInfoResponse.setMessage("exp len: " + judgeCaseList.size() + ", act len:" + outputList.size());
            return judgeInfoResponse;
        }
        for (int i = 0;i < judgeCaseList.size();i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.output.equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
//                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                judgeInfoResponse.setMessage("exp content: " + judgeCase.output + ", act content:" + outputList.get(i));
                return judgeInfoResponse;
            }
        }

        // 限制判断
        if (actualJudgeInfo.memory > expectedJudgeConfig.memoryLimit * 1000_1000) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (actualJudgeInfo.time - JAVA_PROGARM_COST > expectedJudgeConfig.timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        return judgeInfoResponse;
    }
}
