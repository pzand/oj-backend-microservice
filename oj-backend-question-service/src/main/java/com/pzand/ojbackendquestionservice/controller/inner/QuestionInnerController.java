package com.pzand.ojbackendquestionservice.controller.inner;

import com.pzand.ojbackendmodel.model.entity.Question;
import com.pzand.ojbackendmodel.model.entity.QuestionSubmit;
import com.pzand.ojbackendquestionservice.service.QuestionService;
import com.pzand.ojbackendquestionservice.service.QuestionSubmitService;
import com.pzand.ojbackendserviceclient.service.QuestionFeignClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 该服务仅内部调用，不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
