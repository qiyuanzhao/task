package com.lavector.crawlers.tasks;

import com.lavector.crawlers.tasks.controller.TaskController;
import com.lavector.crawlers.tasks.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(TaskController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {

//    @Autowired
//    WebApplicationContext webApplicationContext;

//    @MockBean
//    TaskService taskService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {

//        MockitoAnnotations.initMocks(mvc);
//        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .param("userId", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
