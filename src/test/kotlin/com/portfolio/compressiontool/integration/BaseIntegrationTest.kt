package com.portfolio.compressiontool.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
abstract class BaseIntegrationTest {
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    protected val mockMvc: MockMvc by lazy {
        MockMvcBuilders
            .webAppContextSetup(this.webApplicationContext)
            .build()
    }
}
