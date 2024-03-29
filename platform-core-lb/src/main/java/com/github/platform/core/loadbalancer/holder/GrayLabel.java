package com.github.platform.core.loadbalancer.holder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 灰度标签定义
 * @author: yxkong
 * @date: 2023/2/23 6:55 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrayLabel {
    private String label;
    private String version;
    private String weight;
}
