package com.services.micro.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataApplicationTests {

    //    private MockMvc mockMvc;
//
//
//    @Mock
//    private DataService myService;
//
//    @InjectMocks
//    private ServiceResource serviceResource;
////    private HelloResource serviceResource;
//
//
//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(serviceResource)
//                .build();
//    }
//
////    @Mock
////    private HelloService helloService;
////
////    @Test
////    public void testHelloWorld() throws Exception {
////
////        when(helloService.hello()).thenReturn("hello");
////
////        mockMvc.perform(get("/hello")
//////        .servletPath("/demo")
////        )
////
////                .andExpect(status().isOk())
////                .andExpect(content().string("hello"));
////
////        verify(helloService).hello();
////    }
//
//    @Test
//    public void testString() throws Exception {
//
//        ServiceResponse serviceResponse = new ServiceResponse();
//        serviceResponse.setMessage("hello test");
//        when(myService.getResponse("test")).thenReturn(serviceResponse);
//
//        mockMvc.perform(get("/test/plain"))
////                .andExpect(status().isOk())
//                .andExpect(content().string("hello test"));
//
//        verify(myService).getResponse("test");
//    }
    @Test
    public void contextLoads() {
    }
//
//
//    @Test
//    public void testJson() throws Exception {
//
//        ServiceResponse serviceResponse = new ServiceResponse();
//        serviceResponse.setMessage("hello test");
//        serviceResponse.setType("MyType");
//        when(myService.getResponse("testkey")).thenReturn(serviceResponse);
//
//
//        mockMvc.perform(get("/test")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message", Matchers.is("hello test")))
//                .andExpect(jsonPath("$.type", Matchers.is("MyType")))
//                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
//    }
//
//
//    @Test
//    public void testPost() throws Exception {
//        String json = "{\n" +
//                "  \"input\": \"MyInput\"\n" +
//                "}";
//        mockMvc.perform(post("/test/post")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message", Matchers.is("Hello MyInput")))
//                .andExpect(jsonPath("$.type", Matchers.is("post")))
//                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
}

