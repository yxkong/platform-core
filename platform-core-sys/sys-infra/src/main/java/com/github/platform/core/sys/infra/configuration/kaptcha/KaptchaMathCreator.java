package com.github.platform.core.sys.infra.configuration.kaptcha;

import com.github.platform.core.standard.constant.SymbolConstant;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 数学验证码生成器
 * @author: yxkong
 * @date: 2022/4/27 3:17 PM
 * @version: 1.0
 */
public class KaptchaMathCreator extends DefaultTextCreator {
    private static final String[] CNUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(SymbolConstant.comma);

    @Override
    public String getText() {
        Integer result = 0;
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomoperands = (int) Math.round(Math.random() * 2);
        if (randomoperands == 0) {
            result = x * y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append(SymbolConstant.multiply);
            suChinese.append(CNUMBERS[y]);
        } else if (randomoperands == 1) {
            if (x != 0 && y % x == 0) {
                result = y / x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append(SymbolConstant.divide);
                suChinese.append(CNUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append(SymbolConstant.plus);
                suChinese.append(CNUMBERS[y]);
            }
        } else if (randomoperands == 2) {
            if (x >= y) {
                result = x - y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append(SymbolConstant.minus);
                suChinese.append(CNUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append(SymbolConstant.minus);
                suChinese.append(CNUMBERS[x]);
            }
        } else {
            result = x + y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append(SymbolConstant.plus);
            suChinese.append(CNUMBERS[y]);
        }
        suChinese.append("=?@" + result);
        return suChinese.toString();
    }
}
