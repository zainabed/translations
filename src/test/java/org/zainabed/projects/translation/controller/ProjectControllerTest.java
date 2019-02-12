package org.zainabed.projects.translation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.zainabed.projects.translation.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ProjectControllerTest {

	@Autowired
	MockMvc mvc;

	MockMultipartFile stringXmlFile;

	@BeforeEach
	public void setup() {
		stringXmlFile = new MockMultipartFile("strings", "strings.xml", "text/xml", "<resources><string name=\"xmlkey\">xml string</string></resources>".getBytes());
	}

	@DisplayName("Should import string xml translations.")
	@Test
	public void shouldImportStringXmlTranslations() throws Exception {
		mvc.perform(MockMvcRequestBuilders.multipart("/projects/4/locales/1/export/string-xml").file(stringXmlFile))
				.andExpect(status().isOk());
	}
}
