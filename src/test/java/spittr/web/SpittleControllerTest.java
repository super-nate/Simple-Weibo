package spittr.web;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.service.SpittleManager;
import spittr.entity.Spittle;
import spittr.data.SpittleRepository;

public class SpittleControllerTest {

//  @Test
//  public void houldShowRecentSpittles() throws Exception {
//    List<Spittle> expectedSpittles = createSpittleList(20);
//    SpittleManager mockManager = mock(SpittleManager.class);
//    when(mockManager.findSpittles(Long.MAX_VALUE, 20))
//        .thenReturn(expectedSpittles);
//
//    SpittleController controller = new SpittleController(mockManager);
//    MockMvc mockMvc = standaloneSetup(controller)
//        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
//        .build();
//
//    mockMvc.perform(get("/spittles"))
//       .andExpect(view().name("spittles"))
//       .andExpect(model().attributeExists("spittleList"))
//       .andExpect(model().attribute("spittleList",
//                  hasItems(expectedSpittles.toArray())));
//  }

//  @Test
//  public void shouldShowPagedSpittles() throws Exception {
//    List<Spittle> expectedSpittles = createSpittleList(50);
//    SpittleRepository mockRepository = mock(SpittleRepository.class);
//    when(mockRepository.findSpittles(238900, 50))
//        .thenReturn(expectedSpittles);
//
//    SpittleController controller = new SpittleController(mockRepository);
//    MockMvc mockMvc = standaloneSetup(controller)
//        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
//        .build();
//
//    mockMvc.perform(get("/spittles?max=238900&count=50"))
//      .andExpect(view().name("spittles"))
//      .andExpect(model().attributeExists("spittleList"))
//      .andExpect(model().attribute("spittleList",
//                 hasItems(expectedSpittles.toArray())));
//  }
  
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle("Hello", new Date());
    SpittleManager mockManager = mock(SpittleManager.class);
    when(mockManager.findOne(12345)).thenReturn(expectedSpittle);
    
    SpittleController controller = new SpittleController(mockManager);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(get("/spittles/12345"))
      .andExpect(view().name("spittle"))
      .andExpect(model().attributeExists("spittle"))
      .andExpect(model().attribute("spittle", expectedSpittle));
  }
   //TODO fix the web test
  // will not save the spittle to database so return null
/*  @Test
  public void saveSpittle() throws Exception {
    SpittleManager mockManager = mock(SpittleManager.class);
    SpittleController controller = new SpittleController(mockManager);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/spittles")
           .param("message", "Hello World") // this works, but isn't really testing what really happens
           .param("longitude", "-81.5811668")
           .param("latitude", "28.4159649")
           )
           .andExpect(redirectedUrl("/spittles"));
    
    verify(mockManager, atLeastOnce()).save(new Spittle(null, "Hello World", new Date(), -81.5811668, 28.4159649));
  }*/
  
  private List<Spittle> createSpittleList(int count) {
    List<Spittle> spittles = new ArrayList<Spittle>();
    for (int i=0; i < count; i++) {
      spittles.add(new Spittle("Spittle " + i, new Date()));
    }
    return spittles;
  }
}
