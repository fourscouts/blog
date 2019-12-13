package nl.fourscouts.blog.replays.api;

import nl.fourscouts.blog.replays.domain.Replayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplayController.class)
class ReplayControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private Replayer replayer;

	@Test
	void shouldStartReplay() throws Exception {
		mvc
			.perform(post("/replay"))
			.andExpect(status().isOk());

		verify(replayer).replay(any());
	}

	@Test
	void shouldGetProgression() throws Exception {
		when(replayer.getProgress(any())).thenReturn(Optional.of(new Replayer.Progress(100, 100)));

		mvc
			.perform(get("/replay"))
			.andExpect(status().isOk())
			.andExpect(content().string("Replay at 100/100 (100% complete)"));
	}

	@Test
	void shouldIndicateNoReplay() throws Exception {
		when(replayer.getProgress(any())).thenReturn(Optional.empty());

		mvc
			.perform(get("/replay"))
			.andExpect(status().isOk())
			.andExpect(content().string("No replay active"));
	}
}
