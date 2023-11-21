package com.portfolio.compressiontool.integration

import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class DecompressFileTest : BaseIntegrationTest() {

    @Test
    fun testValidDecompressFile() {
        val fileToDecompress = requireNotNull(javaClass.classLoader.getResourceAsStream("testDecompress.txt"))
        val file = MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            fileToDecompress
        )

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/v1/fileuploads/decompress")
                .file(file)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.header().string(
                    "Content-Disposition",
                    "attachment;filename=decompressedFile.txt"
                )
            )
            .andExpect(
                MockMvcResultMatchers.content().string("This is a simple text for testing compression...")
            )
    }
}