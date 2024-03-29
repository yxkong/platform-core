package com.github.platform.core.loadbalancer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrayRuleEntity implements Serializable {

    private String rule;

    private String desc;

    private String lable;

    private String version;

}