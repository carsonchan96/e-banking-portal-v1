// package com.demo.ebankingportal;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;

// import com.demo.ebankingportal.models.User;
// import com.demo.ebankingportal.repositories.UserRepository;
// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import net.minidev.json.JSONObject;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import static org.hamcrest.CoreMatchers.containsString;


// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// public class HttpRequestTests {

//     private final ObjectMapper objectMapper = new ObjectMapper();
    
//     @LocalServerPort
//     private int port;

//     @Autowired
//     private TestRestTemplate testRestTemplate;

//     @Autowired
//     UserRepository userRepository;

//     @Test
//     public void signupFunctionSuccess() throws Exception{
//         if(!userRepository.existsByUsername("carsonchan")){
//             userRepository.save(new User(
//                 "carsonchan",
//                 "A-9999999999",
//                 "carsonchan@demo.com",
//                 "carsonchanpwd"
//             ));
//         }
//         JSONObject requestObject = new JSONObject();
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         requestObject.put("username", "carsonchan");
//         // requestObject.put("email", "carsonchan@demo.com");
//         requestObject.put("password", "carsonchanpwd");
//         // requestObject.put("roles", new ArrayList<>());
//         HttpEntity<String> request = new HttpEntity<String>(requestObject.toString(),headers);

//         String result = testRestTemplate.postForObject("http://127.0.0.1:"+port+"/api/auth/signin", request, String.class);

//         JsonNode root = objectMapper.readTree(result);

//         assertNotNull(result);
//         assertNotNull(root);
//         System.out.println(root.toString());
//         org.hamcrest.MatcherAssert.assertThat(root.toString(),containsString("username\":\"carsonchan\""));
//     }
// }
