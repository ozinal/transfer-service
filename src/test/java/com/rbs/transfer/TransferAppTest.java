package com.rbs.transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferAppTest {
    @Test
    public void contextLoads() {
    }

    @Test
    public void contextLoads_with_custom_params() {
        TransferApp.main(new String[] {
                "--spring.main.banner-mode=off"
        });
    }
}
