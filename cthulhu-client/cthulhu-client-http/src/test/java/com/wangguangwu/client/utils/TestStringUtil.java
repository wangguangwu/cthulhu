package com.wangguangwu.client.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

/**
 * @author wangguangwu
 */
public class TestStringUtil {

    @ParameterizedTest
    @ValueSource(strings = "/web/common/security-check.html?seed=kojQzFVl5ojLm3lM3PFWQkwoBo1uIZ97eizuI1P1V5s%3D&name=11ff6775&ts=1646915797750&callbackUrl=%2Fjob_detail%2F%3Fquery%3Djava%26city%3D101210100%26industry%3D%26position%3D&srcReferer=https%3A%2F%2Fwww.zhipin.com%2Fweb%2Fcommon%2Fsecurity-check.html%3Fseed%3DKZwFuez5EFZc0c%252F8bsInOLZkHG5%252FQWED4X%252FxzsvB8AY%253D%26name%3De5e75d46%26ts%3D1646817278645%26callbackUrl%3D%252Fjob_detail%252F%253Fquery%253Djava%2526city%253D101210100%2526industry%253D%2526position%253D%26srcReferer%3D\n")
    public void testParseQueryString(String str) {
        Map<String, String> queryString = StringUtil.parseQueryString(str);
        System.out.println(queryString);
    }

}
