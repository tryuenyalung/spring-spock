package com.yuen.springapi.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.yuen.springapi.configs.SpockSpringTestConfig
import com.yuen.springapi.utilities.UsersUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@Import([SpockSpringTestConfig])
class UsersControllerTest extends Specification{

    ObjectMapper objectMapper = new ObjectMapper()

    @Autowired MockMvc mvc

    def "should be able to fetch all users"() {
        setup: 'instanciate createUser'
        UsersUtils usersUtils = new UsersUtils()
        String request = objectMapper.writeValueAsString(usersUtils.createUser())


        when: 'Calling getRank for a known seed data entity'
        MvcResult result = mvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(request))
        //response
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()

        then: 'the result contains a mix of mocked service data and actual wired component data'
        mvc.perform(get("/api/v1/users/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())

    }

    def "should be able to add users"() {

        setup: 'instanciate createUser'
        UsersUtils usersUtils = new UsersUtils()

        when: 'we make a request'
        String request = objectMapper.writeValueAsString(usersUtils.createUser())

        then: 'we send the request to /api/v1/users'
        MvcResult result = mvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(request))
                //response
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()




//        and: 'inspecting the contents'
//        def resultingJson = result.response.contentAsString
//
//        then: 'the result contains a mix of mocked service data and actual wired component data'
//        resultingJson ==  'asd'
    }
}
