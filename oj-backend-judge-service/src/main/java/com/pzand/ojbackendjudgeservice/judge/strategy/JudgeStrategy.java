package com.pzand.ojbackendjudgeservice.judge.strategy;

import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import org.springframework.stereotype.Component;

@Component
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
