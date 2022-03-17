package com.wangguangwu.client.startup;

import org.junit.jupiter.api.Test;

/**
 * @author wangguangwu
 */
public class TestBootStrap {

    @Test
    public void test_start() {
        String responseBody = new Bootstrap()
                .url("https://www.zhipin.com/c101210100/?query=java&page=2&ka=page-1")
                .cookies("__zp_stoken__=5f92dWGVWJFlcLzUkEwZhZ1AEVUFlaTVoF3snLx8/fjIrN2ErKHlraUJxKlcJZix0fR9fS2cTOgkUdH9kH0onNwgZcjcUSBAkZlsHKyg7WA9JeFVmFGFPciQQb3kWTRxxPyZtP31nRXQKOiU=")
                .start();
        System.out.println(responseBody);
    }

}
