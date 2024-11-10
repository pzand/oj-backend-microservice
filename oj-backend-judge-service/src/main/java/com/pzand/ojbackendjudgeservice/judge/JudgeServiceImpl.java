package com.pzand.ojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.pzand.ojbackendcommon.common.ErrorCode;
import com.pzand.ojbackendcommon.exception.BusinessException;
import com.pzand.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.pzand.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.pzand.ojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.pzand.ojbackendmodel.model.codesandbox.JudgeInfo;
import com.pzand.ojbackendmodel.model.dto.question.JudgeCase;
import com.pzand.ojbackendmodel.model.dto.question.JudgeConfig;
import com.pzand.ojbackendmodel.model.entity.Question;
import com.pzand.ojbackendmodel.model.entity.QuestionSubmit;
import com.pzand.ojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.pzand.ojbackendserviceclient.service.QuestionFeignClient;
import com.pzand.ojbackendserviceclient.service.QuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;


    @Autowired
    @Qualifier("remote")
    private CodeSandbox codeSandbox;

    @Value("${codesandbox.type:example}")
    private String sandboxName;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1) 传入题目的提交 id，获取到对应的题目、提交信息 (包括代码，编程语言等)
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }

        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目不在等待状态中");
        }
        // 修改数据库的判题状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).toList();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        if (applicationContext.containsBean(sandboxName)) {
            codeSandbox = applicationContext.getBean(sandboxName, CodeSandbox.class);
        }
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        // 根据沙箱执行结果，判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        JudgeConfig expectedJudgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        judgeContext.setExpectedJudgeConfig(expectedJudgeConfig);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo res = judgeManager.doJudge(judgeContext);


        // 更改数据的执行状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(res));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }

        return questionFeignClient.getQuestionSubmitById(questionId);
    }
}
