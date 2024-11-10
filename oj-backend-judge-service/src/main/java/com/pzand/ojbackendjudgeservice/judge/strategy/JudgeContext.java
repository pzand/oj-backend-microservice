package com.pzand.ojbackendjudgeservice.judge.strategy;

import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.pzand.ojbackendmodel.model.dto.question.JudgeCase;
import com.pzand.ojbackendmodel.model.dto.question.JudgeConfig;
import com.pzand.ojbackendmodel.model.entity.Question;
import com.pzand.ojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {
    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private JudgeConfig expectedJudgeConfig;

    private JudgeInfo judgeInfo;

    private Question question;

    private QuestionSubmit questionSubmit;
}
