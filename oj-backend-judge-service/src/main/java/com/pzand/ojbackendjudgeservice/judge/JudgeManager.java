package com.pzand.ojbackendjudgeservice.judge;

import com.pzand.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.pzand.ojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    JudgeStrategy judgeStrategy;

    JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getQuestionSubmit().getLanguage();
        if (applicationContext.containsBean(language)) {
            judgeStrategy = applicationContext.getBean(language, JudgeStrategy.class);
        }

        return judgeStrategy.doJudge(judgeContext);
    }
}
