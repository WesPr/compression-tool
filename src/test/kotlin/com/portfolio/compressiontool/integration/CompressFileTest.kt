package com.portfolio.compressiontool.integration

import com.portfolio.commons.InvalidFileTypeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class CompressFileTest : BaseIntegrationTest() {

    @Test
    fun testValidCompressFile() {
        val fileContent = "This is a simple text for testing compression..."
        val file = MockMultipartFile(
            "file",
            "largeFile.txt",
            "text/plain",
            fileContent.toByteArray()
        )

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/v1/fileuploads/compress")
                .file(file)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.header().string(
                    "Content-Disposition",
                    "attachment;filename=testDecompress.txt"
                )
            )
            .andExpect(
                MockMvcResultMatchers.content().string(
                    "01110000,0000\n" +
                            "01100011,00010\n" +
                            "EOF,00011\n" +
                            "01101101,0010\n" +
                            "01100110,00110\n" +
                            "01100001,001110\n" +
                            "01111000,001111\n" +
                            "01101001,010\n" +
                            "01110011,011\n" +
                            "01101111,1000\n" +
                            "00101110,1001\n" +
                            "00100000,101\n" +
                            "01100101,1100\n" +
                            "01110010,11010\n" +
                            "01101100,110110\n" +
                            "01100111,110111\n" +
                            "01110100,1110\n" +
                            "01010100,111100\n" +
                            "01101000,111101\n" +
                            "01101110,11111\n" +
                            "\n" +
                            "óÔêtêÑ\u0006Ù{\u000FêhÕì|¿½\u0014\u0010k\u001BQù\u0099\u0018"
                )
            )
    }

    @Test
    fun testInvalidFileType() {
        val file = MockMultipartFile(
            "file",
            "test.csv",
            "text/plain",
            "invalid".toByteArray()
        )

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/v1/fileuploads/compress")
                .file(file)
        )
            .andExpect { MockMvcResultMatchers.status().isBadRequest }
            .andExpect { result -> assertTrue(result.resolvedException is InvalidFileTypeException) }
            .andExpect { result -> assertEquals("Invalid file type", result.resolvedException!!.message) }
    }
}