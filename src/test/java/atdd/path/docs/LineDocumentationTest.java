package atdd.path.docs;

import atdd.AbstractDocumentationTest;
import atdd.path.application.LineService;
import atdd.path.domain.Line;
import atdd.path.repository.LineRepository;
import atdd.path.web.LineController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineController.class)
public class LineDocumentationTest extends AbstractDocumentationTest {
    @MockBean
    private LineRepository lineRepository;

    @MockBean
    private LineService lineService;

    @DisplayName("지하철역 노선 생성")
    @Test
    public void createLine() throws Exception {

        Line line = new Line(1L, "강남역", LocalTime.of(5, 0), LocalTime.of(23, 30), 30);

        given(lineRepository.save(any())).willReturn(line);

        String inputJson = "{\"name\":\"" + line.getName() + "\"," +
                "\"startTime\":\"" + line.getStartTime() + "\"," +
                "\"endTime\":\"" + line.getEndTime() + "\"," +
                "\"stationInterval\":\"" + line.getStationInterval() + "\"}";

        this.mockMvc
                .perform(post("/lines").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("lines/create",
                            requestFields(
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("The line name"),
                                    fieldWithPath("startTime").type(JsonFieldType.STRING).description("The line start time"),
                                    fieldWithPath("endTime").type(JsonFieldType.STRING).description("The line end time"),
                                    fieldWithPath("stationInterval").type(JsonFieldType.STRING).description("The interval between lines")
                            ),
                            responseFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("The line id"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("The line name"),
                                    fieldWithPath("stations").type(JsonFieldType.ARRAY).description("stations of this line").optional(),
                                    fieldWithPath("startTime").type(JsonFieldType.STRING).description("The line start time"),
                                    fieldWithPath("endTime").type(JsonFieldType.STRING).description("The line end time"),
                                    fieldWithPath("stationInterval").type(JsonFieldType.NUMBER).description("The interval between lines")
                            )
                        )
                )
                .andDo(print());
    }

}