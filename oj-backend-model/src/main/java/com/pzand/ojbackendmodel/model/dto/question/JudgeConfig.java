package com.pzand.ojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制 (ms)
     */
    public Long timeLimit;

    /**
     * 内存限制 (kb)
     */
    public Long memoryLimit;

    /**
     * 堆栈限制 (kb)
     */
    public Long stackLimit;
}
