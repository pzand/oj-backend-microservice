package com.pzand.ojbackendjudgeservice.controller.inner;

import com.pzand.ojbackendjudgeservice.judge.JudgeService;
import com.pzand.ojbackendmodel.model.entity.QuestionSubmit;
import com.pzand.ojbackendserviceclient.service.JudgeFeignClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该服务仅内部调用，不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
