package com.training.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.training.TestMvcConfig;
import com.training.dto.TicketDto;
import com.training.enums.State;
import com.training.enums.Urgency;
import com.training.model.Category;
import com.training.model.User;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestMvcConfig.class})
@WebAppConfiguration
public class TicketControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    private static User employee = new User()
            .setEmail("user1_mogilev@yopmail.com")
            .setPassword("P@ssword1");

    private static User engineer = new User()
            .setId(1L)
            .setEmail("engineer1_mogilev@yopmail.com")
            .setPassword("P@ssword1");

    private TicketDto ticketDto = new TicketDto()
            .setCategory(new Category().setId(1L))
            .setName("Test Ticket")
            .setUrgency(Urgency.CRITICAL)
            .setDescription("Test ticket description")
            .setAttachments(Lists.newArrayList());

    private static final Long TEST_TICKET_ID = 2L;
    private static final Long TEST_SINGLE_TICKET_ID = 3L;
    private static final String TEST_URL = "/api/tickets/";

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors
                .httpBasic(user.getEmail(), user.getPassword());
    }

    @Test
    public void testGetAllTickets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL)
                .with(userHttpBasic(employee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id", Is.is(TEST_TICKET_ID.intValue())));
    }

    @Test
    public void testGetMyTickets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + "my")
                .with(userHttpBasic(employee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id", Is.is(TEST_TICKET_ID.intValue())));
    }

    @Test
    public void testGetSingleTicket() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TEST_URL + "{id}", TEST_SINGLE_TICKET_ID)
                .with(userHttpBasic(employee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Is.is(TEST_SINGLE_TICKET_ID.intValue())))
                .andExpect(jsonPath("$.name", Is.is("task3")))
                .andExpect(jsonPath("$.createdOn", Is.is("31/03/2019")))
                .andExpect(jsonPath("$.desiredDate", Is.is("12/04/2019")))
                .andExpect(jsonPath("$.urgency", Is.is(Urgency.AVERAGE.name())))
                .andExpect(jsonPath("$.state", Is.is(State.NEW.name())))
                .andExpect(jsonPath("$.actions").isArray())
                .andExpect(jsonPath("$.category.id", Is.is(1)))
                .andExpect(jsonPath("$.owner.email", Is.is(employee.getEmail())))
                .andExpect(jsonPath("$.assignee", isEmptyOrNullString()))
                .andExpect(jsonPath("$.approver", isEmptyOrNullString()));
    }

    @Test
    public void testSaveTicketByUserIsCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(TEST_URL)
                .with(userHttpBasic(employee)).content(mapper.writeValueAsString(ticketDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveTicketByEngineerIsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(TEST_URL)
                .with(userHttpBasic(engineer)).content(mapper.writeValueAsString(ticketDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdateTicketByUserIsCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(TEST_URL + "{id}", TEST_TICKET_ID)
                .with(userHttpBasic(employee)).content(mapper.writeValueAsString(ticketDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateTicketByEngineerIsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(TEST_URL + "{id}", TEST_TICKET_ID)
                .with(userHttpBasic(engineer)).content(mapper.writeValueAsString(ticketDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isForbidden());
    }
}
