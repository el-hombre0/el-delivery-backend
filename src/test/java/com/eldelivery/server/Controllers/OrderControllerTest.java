package com.eldelivery.server.Controllers;

import com.eldelivery.server.Models.Order.Order;
import com.eldelivery.server.Models.Order.Status;
import com.eldelivery.server.Models.User.Role;
import com.eldelivery.server.Models.User.User;
import com.eldelivery.server.Repositories.OrderRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderRepo orderRepo;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    OrderController controller;

//    private String getJwtForUser(String email, String password, String loginUrl){
//        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//        form.set("username", username);
//        form.set("password", password);
//        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity(
//                loginUrl,
//                new HttpEntity<>(form, new HttpHeaders()),
//                String.class);
//        String cookie = loginResponse.getHeaders().get("Set-Cookie").get(0);
//        return cookie;
//    }
    @Test
    void getAllOrders_ReturnsValidListOrder() {
        // given
        Role userRole = Role.USER;
        Role adminRole = Role.ADMIN;

        Set<Order> orders1 = new HashSet<>();
        Set<Order> orders2 = new HashSet<>();

        Status processingStatus = Status.PROCESSING;
        Status acceptedStatus = Status.ACCEPTED;

        User user1 = new User(1, "Иван", "Бласкович", "blasco@yandex.ru", "1q2w3e4r", userRole, orders1);
        User user2 = new User(2, "Дмитрий", "Степанов", "stepanov@yandex.ru", "1q2w3e4r", adminRole, orders2);

        Order order1 = new Order(1L, user1, "Иван", "Бласкович", "89991112233", "blasco@yandex.ru", "Tesla Modex S", 30.00, 13.50, "Moscow, Lenina st., h. 12", 6200.0, "debit card", processingStatus, user2);
        Order order2 = new Order(2L, user2, "Дмитрий", "Степанов", "89991112233", "stepanov@yandex.ru", "Tesla Modex X", 13.45, 19.99, "Moscow, Lenina st., h. 12", 5990.00, "cash", acceptedStatus, user2);

//        ArrayList<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));
        var orders = List.of(order1, order2);

        Mockito.doReturn(orders).when(this.orderRepo).findAll();
        // when
        var responseEntity = this.controller.getAllOrders();
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(orders, responseEntity.getBody());
    }
}