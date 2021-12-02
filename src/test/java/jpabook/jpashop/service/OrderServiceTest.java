package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {
    //상품 주문시 ORDER로 변경되는지 테스트, 주문 종류가 정확해야함
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    @Test
    public void 상품주문() throws Exception {
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order findorder = orderRepository.findOne(orderId);
        assertThat(findorder.getStatus()).as("상품 주문시 상태는 ORDER").isEqualTo(OrderStatus.ORDER);
        assertThat(findorder.getOrderItems().size()).as("주문한 상품 종류 사가 정확해야 한다.").isEqualTo(1);
        Item findItem = itemService.findOne(book.getId());
        assertThat(findItem.getStockQuantity()).as("주문 수량만큼 재고가 줄어야 한다.").isEqualTo(10 - orderCount);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("kim");
        memberService.join(member);
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 11;
        NotEnoughStockException exception = assertThrows(
                NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
        String message = exception.getMessage();
        System.out.println("message : " + message);
    }

    @Test
    public void 주문취소() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getStatus())
                .as("주문 취소시 상태는 canceled이다.").isEqualTo(OrderStatus.CANCELED);
        assertThat(item.getStockQuantity())
                .as("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.").isEqualTo(10);
    }
}