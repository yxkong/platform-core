package com.github.platform.core.common.utils;

import com.github.platform.core.common.constant.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public final static Pattern PATTERN = Pattern.compile("[A-Z]");
    private static final char separator = '_';
    /**
     * 字符编码
     */
    public final static String ENCODING = "UTF-8";
    /**
     * 常用正则表达式：匹配非负整数（正整数 + 0）
     */
    public final static Pattern REGEXP_INTEGER_1 = Pattern.compile("^\\d+$");

    /**
     * 常用正则表达式：匹配正整数
     */
    public final static Pattern REGEXP_INTEGER_2 = Pattern.compile("^[0-9]*[1-9][0-9]*$");

    /**
     * 常用正则表达式：匹配非正整数（负整数 + 0）
     */
    public final static Pattern REGEXP_INTEGER_3 = Pattern.compile("^((-\\d+) ?(0+))$");

    /**
     * 常用正则表达式：匹配负整数
     */
    public final static Pattern REGEXP_INTEGER_4 = Pattern.compile("^-[0-9]*[1-9][0-9]*$");

    /**
     * 常用正则表达式：匹配整数
     */
    public final static Pattern REGEXP_INTEGER_5 = Pattern.compile("^-?\\d+$");

    /**
     * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
     */
    public final static Pattern REGEXP_FLOAT_1 = Pattern.compile("^\\d+(\\.\\d+)?$");

    /**
     * 常用正则表达式：匹配正浮点数
     */
    public final static Pattern REGEXP_FLOAT_2 =
            Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$");

    /**
     * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
     */
    public final static Pattern REGEXP_FLOAT_3 = Pattern.compile("^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$");

    /**
     * 常用正则表达式：匹配负浮点数
     */
    public final static Pattern REGEXP_FLOAT_4 =
            Pattern.compile("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*)))$");

    /**
     * 常用正则表达式：匹配浮点数
     */
    public final static Pattern REGEXP_FLOAT_5 = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");

    /**
     * 常用正则表达式：匹配由26个英文字母组成的字符串
     */
    public final static Pattern REGEXP_LETTER_1 = Pattern.compile("^[A-Za-z]+$");

    /**
     * 常用正则表达式：匹配由26个英文字母的大写组成的字符串
     */
    public final static Pattern REGEXP_LETTER_2 = Pattern.compile("^[A-Z]+$");

    /**
     * 常用正则表达式：匹配由26个英文字母的小写组成的字符串
     */
    public final static Pattern REGEXP_LETTER_3 = Pattern.compile("^[a-z]+$");

    /**
     * 常用正则表达式：匹配由数字和26个英文字母组成的字符串
     */
    public final static Pattern REGEXP_LETTER_4 = Pattern.compile("^[A-Za-z0-9]+$");

    /**
     * 常用正则表达式：匹配由数字、26个英文字母或者下划线组成的字符串
     */
    public final static Pattern REGEXP_LETTER_5 = Pattern.compile("^\\w+$");
    /**
     * 正则表达式：匹配email地址
     */
    public final static Pattern REGEXP_EMAIL =
            Pattern.compile("^([a-z0-9A-Z-_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 常用正则表达式：匹配url
     */
    public final static Pattern REGEXP_URL_1 = Pattern.compile("^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$");

    /**
     * 常用正则表达式：匹配url
     */
    public final static Pattern REGEXP_URL_2 = Pattern.compile("[a-zA-z]+://[^\\s]*");

    /**
     * 正则表达式：验证URL
     */
    public final static Pattern REGEXP_URL_3 = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
    /**
     * 常用正则表达式：匹配中文字符
     */
    public final static Pattern REGEXP_CHINESE_1 = Pattern.compile("[\\u4e00-\\u9fa5]");

    /**
     * 常用正则表达式：匹配双字节字符(包括汉字在内)
     */
    public final static Pattern REGEXP_CHINESE_2 = Pattern.compile("[^\\x00-\\xff]");

    /**
     * 常用正则表达式：匹配空行
     */
    public final static Pattern REGEXP_LINE = Pattern.compile("\\n[\\s ? ]*\\r");

    /**
     * 常用正则表达式：匹配HTML标记
     */
    public final static Pattern REGEXP_HTML_1 = Pattern.compile("/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/");

    /**
     * 常用正则表达式：匹配首尾空格
     */
    public final static Pattern REGEXP_STARTENDEMPTY = Pattern.compile("(^\\s*) ?(\\s*$)");

    /**
     * 常用正则表达式：匹配帐号是否合法(字母数字都有,总共8位)
     */
    public final static Pattern REGEXP_ACCOUNTNUMBER = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{8}$");

    /**
     * 常用正则表达式：密码强度是否合法，至少包含一个数字，
     * 至少包含一个数字 [0-9]
     * 至少包含一个小写字母 [a-z]
     * 至少包含一个大写字母 [A-Z]
     * 至少包含一个特殊字符 [!@#$%^&*_+=]
     * 不包含空格 \\S+
     * 长度至少为 6 个字符 .{6,}
     */
    public static final Pattern PASSWORD_PATTERN1 = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*&_+=])(?=\\\\S+$).{6,}$");
    /**
     * 常用正则表达式：密码强度是否合法，至少包含一个数字，一个字母，一个特殊字符
     * 至少包含一个数字 [0-9]
     * 至少包含一个小写字母 [a-zA-Z]
     * 至少包含一个特殊字符 [!@#$%^&*_+=]
     * 不包含空格 \\S+
     * 长度至少为 6 个字符 .{6,}
     */
    public static final Pattern PASSWORD_PATTERN2 = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^*&_+=])(?=\\S+$).{6,}$");

    /**
     * 常用正则表达式：匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
     */
    public final static Pattern REGEXP_TELEPHONE = Pattern.compile("\\d{3}-\\d{8} ?\\d{4}-\\d{7}");
    /**
     * 正则表达式：验证手机号
     */
    public static final Pattern REGEXP_MOBILE = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
    /**
     * 常用正则表达式：腾讯QQ号, 腾讯QQ号从10000开始
     */
    public final static Pattern REGEXP_QQ = Pattern.compile("[1-9][0-9]{4,}");

    /**
     * 常用正则表达式：匹配中国邮政编码
     */
    public final static Pattern REGEXP_POSTBODY = Pattern.compile("[1-9]\\d{5}(?!\\d)");

    /**
     * 常用正则表达式：匹配身份证, 中国的身份证为15位或18位
     */
    public final static Pattern REGEXP_IDCARD = Pattern.compile("\\d{15} ?\\d{18}");

    /**
     * 正则表达式：验证IP地址
     */
    public final static Pattern REGEXP_IP_ADDR = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");

    // @.+?[\\s:]
    public final static Pattern REFERER_PATTERN = Pattern.compile("@([^@^\\s^:]{1,})([\\s\\:\\,\\;]{0,1})");

    private static Random randGen = new Random();
    private static char[] numbers = ("0123456789").toCharArray();
    private static char[] numbersAndLetters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*").toCharArray();


    /**
     * 验证字符串是否匹配指定正则表达式
     *
     * @param content
     * @param regExp
     * @return
     */
    public static boolean matchExp(String content, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        return pattern.matcher(content).matches();
    }

    public static boolean matchPattern(String content, Pattern pattern) {
        return pattern.matcher(content).matches();
    }


    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str == null || strs == null) {
            return false;
        }
        for (String s : strs) {
            if (str.equalsIgnoreCase(trim(s))) {
                return true;
            }
        }
        return false;
    }

    /**
     * double精度调整
     *
     * @param doubleValue 需要调整的值123.454
     * @param formatStr   目标样式".##"
     * @return
     */
    public static String decimalFormat(double doubleValue, String formatStr) {
        DecimalFormat format = new DecimalFormat(formatStr);
        return format.format(doubleValue);
    }

    /**
     * 首字母转大写（确保一定是小写字母）
     *
     * @param str
     * @return
     */
    public static String upperFirstChar(String str) {
        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        char[] cs = str.toCharArray();
        cs[0] = (char) (cs[0] - 32);
        return String.valueOf(cs);
    }

    /**
     * 首字母转小写（确保一定是大写字母）
     *
     * @param str
     * @return
     */
    public static String lowerFirstChar(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        char[] cs = str.toCharArray();
        cs[0] = (char) (cs[0] + 32);
        return String.valueOf(cs);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        boolean upperCase = false;
        StringBuilder sb = new StringBuilder(s.length());

        for (char c : s.toLowerCase().toCharArray()) {
            if (c == separator) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 生成随机码
     *
     * @return
     */
    public static final String randomUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成随机码，去掉-，共32位
     *
     * @return
     */
    public static final String uuidRmLine() {
        return randomUuid().replaceAll("-", "");
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        Matcher matcher = PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);

        int i = 0;
        while (matcher.find()) {
            int start = matcher.start() + i;
            int end = matcher.end() + i;

            builder.replace(start, end, "_" + matcher.group().toLowerCase());
            i += "_".length();
        }

        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }

        return builder.toString();

    }

    /**
     * 将下划线风格替换为驼峰风格
     *
     * @param str
     * @return
     * @author yxkong
     * @createDate 2015年12月29日
     * @updateDate
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = PATTERN.matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
        }
        return builder.toString();
    }

    /**
     * 指定位数模糊字符串
     *
     * @param str        原始字符串
     * @param startIndex 开始索引
     * @param len        模糊长度
     * @param vague      替换字符
     * @return 返回对应位置替换为对应字符的字符串
     * @author yxkong
     * @createDate 2016年2月23日
     * @updateDate
     */
    public static String getVague(String str, int startIndex, int len, String vague) {
        if (Objects.isNull(str)) {
            return null;
        }
        if (str.length() < startIndex + len) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, startIndex));
        for (int i = 0; i < len; i++) {
            sb.append(vague);
        }
        sb.append(str, startIndex + len, str.length());
        return sb.toString();
    }

    /**
     * 手机号脱敏
     *
     * @param mobile
     * @return
     * @author yxkong
     * @createDate 2016年5月17日
     * @updateDate
     */
    public static String getMobileVague(String mobile) {
        return getVague(mobile, 3, 4, "*");
    }

    /**
     * 身份证号脱敏
     *
     * @param certId
     * @return
     * @author yxkong
     * @createDate 2016年5月17日
     * @updateDate
     */
    public static String getCertIdVague(String certId) {
        return getVague(certId, 4, 10, "*");
    }

    /**
     * 银行卡脱敏
     *
     * @return
     * @author yxkong
     * @createDate 2016年5月17日
     * @updateDate
     */
    public static String getcardNoVague(String cardNo) {
        if (isNotEmpty(cardNo)) {
            return getVague(cardNo, 0, cardNo.length() - 4, "*");
        }
        return "";
    }

    /**
     * 返回指定范围的随机字符串
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    public static String randomStr(int minLength, int maxLength) {
        int number = randGen.nextInt(maxLength - minLength) + minLength;
        return randomStr(number);
    }

    /**
     * 返回指定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String randomStr(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            sb.append(numbersAndLetters[randGen.nextInt(numbersAndLetters.length)]);
        }
        return sb.toString();
    }

    /**
     * 返回指定长度的随机数字
     *
     * @param length
     * @return
     */
    public static final String randomNumber(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < length; i++) {
            randBuffer[i] = numbers[randGen.nextInt(9)];
        }
        return new String(randBuffer);
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * 空字符串，null,"null"都为true
     *
     * @param str
     * @return
     * @author yxkong
     * @createDate 2018年1月26日
     * @updateDate
     */
    public static boolean isNull(String str) {
        if (StringUtils.isBlank(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 移除字符串中的特殊字符 / \ + =
     *
     * @param str
     * @return
     * @author yxkong
     * @createDate 2016年5月27日
     * @updateDate
     */
    public static String getReplaceStr(String str) {
        if (isNotEmpty(str)) {
            return str.replaceAll("/", "").replaceAll("\\\\", "").replaceAll("\\+", "").replaceAll("=", "");
        }
        return str;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return REGEXP_MOBILE.matcher(mobile).matches();
    }

    /**
     * 是否密码格式
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdPattern(String pwd) {
        return PASSWORD_PATTERN2.matcher(pwd).matches();
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return REGEXP_EMAIL.matcher(email).matches();
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return REGEXP_CHINESE_1.matcher(chinese).matches();
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return REGEXP_URL_3.matcher(url).matches();
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIpAddr(String ipAddr) {
        return REGEXP_IP_ADDR.matcher(ipAddr).matches();
    }

    /**
     * 获取字符串最后一位
     *
     * @param str
     * @return
     */
    public static String getLastIndexStr(String str) {
        return str.substring(str.length() - 1, str.length());

    }

    /**
     * url Decoder 编码
     *
     * @param str
     * @return
     */
    public static String urlDecoder(String str) {
        try {
            return URLDecoder.decode(str, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * url Encoder 编码
     *
     * @param str
     * @return
     */
    public static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String subStringOfLength(String body, int len) {
        if (body.length() > len) {
            return body.substring(0, len);
        } else {
            return body;
        }
    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }


    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str(str);
        }
        final String str2 = str.toString();
        return str2.startsWith(prefix.toString()) ? subSuf(str2, prefix.length()) : str2;
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 切割指定位置之后部分的字符串
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     */
    public static String subSuf(CharSequence string, int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 "" <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     *
     * @param str              String
     * @param fromIndexInclude 开始的index（包括）
     * @param toIndexExclude   结束的index（不包括）
     * @return 字串
     */
    public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        if (isEmpty(str)) {
            return str(str);
        }

        int len = str.length();

        fromIndexInclude = Math.max(0, Math.min(len, fromIndexInclude < 0 ? len + fromIndexInclude : fromIndexInclude));
        toIndexExclude = Math.max(0, Math.min(len, toIndexExclude < 0 ? len + toIndexExclude : toIndexExclude));

        if (fromIndexInclude > toIndexExclude) {
            int tmp = fromIndexInclude;
            fromIndexInclude = toIndexExclude;
            toIndexExclude = tmp;
        }

        return str.toString().substring(fromIndexInclude, toIndexExclude);
    }

}
