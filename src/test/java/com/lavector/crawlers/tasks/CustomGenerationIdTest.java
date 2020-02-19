package com.lavector.crawlers.tasks;

import com.lavector.crawlers.tasks.utlis.CustomGenerationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class CustomGenerationIdTest {

    @Autowired
    private CustomGenerationId customGenerationId;

    @Test
    public void testId(){
        String id = customGenerationId.getId();
        System.out.println(id);
    }



}
