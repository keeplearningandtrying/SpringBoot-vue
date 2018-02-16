package com.boylegu.springboot_vue;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.boylegu.springboot_vue.dao.PersonsRepository;
import com.boylegu.springboot_vue.entities.Persons;

/**
 * @author Boyle Gu
 * @version 0.0.1
 * @GitHub https://github.com/boylegu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SpringbootVueApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonsRepository personsRepository;
    
    //@Autowired
    //private TestEntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        //mvc = MockMvcBuilders.standaloneSetup( new MainController()).build();

        Persons persons = new Persons();
        persons.setZone("t1 zone");
        persons.setPhone("11111");
        persons.setEmail("t1@qq.com");
        persons.setUsername("t1");
        persons.setSex("male");
        personsRepository.save(persons);
        //this.entityManager.persist(persons);

    }

    @Test
    public void testUserController() throws Exception {
//  	Test MainController
        RequestBuilder request = null;

        request = get("/api/persons/sex");

        mvc.perform(request)
                .andExpect(status().isOk());
                //.andExpect(content().string(equalTo("[]")));

        request = get("/api/persons/")
                .param("sex", "male")
                .param("email", "test@qq.com");

        mvc.perform(request)
                .andExpect(status().isOk());
                //.andExpect(content().string(equalTo("[]")));

        request = get("/api/persons/detail/1");
        mvc.perform(request)
                .andExpect(status().isOk());
                //.andExpect(content().string(equalTo("[{\"id\":1,\"username\":\"test\",\"zone\":20}]")));

        request = put("/api/persons/detail/1")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content("{\"id\":1,\"create_datetime\":\"2018-02-16 00:00:00.0\",\"username\":\"gubaoer\",\"email\":\"gubaoer@hotmail.com\",\"phone\":\"8613000001111\",\"sex\":\"male\",\"zone\":\"TEST\"}" );
        		//.content("{\"phone\":\"8613000001111\",\"zone\":\"TEST\"}" );
        mvc.perform(request)
               .andExpect(content().string(containsString("TEST")));


        request = get("/api/persons")
                .param("sex", "male")
                .param("email", "test@qq.com");
        mvc.perform(request)
                .andExpect(status().isOk());
                //.andExpect(content().string(equalTo("[]")));

    }

}

