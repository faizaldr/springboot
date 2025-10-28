package id.eduparx.social;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import id.eduparx.social.controller.Hallo;

@WebMvcTest(Hallo.class)
public class HalloControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void halloEndPoint() throws Exception{
        mockMvc.perform(get("/api/hallo")).andExpect(status().isOk()).andExpect(content().string("Selamat Malam"));
    }
    
}
