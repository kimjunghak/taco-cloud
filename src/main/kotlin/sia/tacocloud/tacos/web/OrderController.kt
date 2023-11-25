package sia.tacocloud.tacos.web

import jakarta.validation.Valid
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import sia.tacocloud.tacos.Order
import sia.tacocloud.tacos.Users
import sia.tacocloud.tacos.data.OrderRepository

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
class OrderController(
    private val orderRepository: OrderRepository,
    private val orderProps: OrderProps,
) {

    @GetMapping("/current")
    fun orderForm(@AuthenticationPrincipal users: Users,
                  @ModelAttribute order: Order): String {
        if(order.deliveryName == null) order.deliveryName = users.fullName
        if(order.deliveryStreet == null) order.deliveryStreet = users.street
        if(order.deliveryCity == null) order.deliveryCity = users.city
        if(order.deliveryState == null) order.deliveryState = users.state
        if(order.deliveryZip == null) order.deliveryZip = users.zip

        return "orderForm"
    }

    @PostMapping
    fun processOrder(@Valid order: Order, errors: Errors, sessionStatus: SessionStatus,
                     @AuthenticationPrincipal users: Users): String {
        if(errors.hasErrors()) return "orderForm"

        order.users = users

        orderRepository.save(order)
        sessionStatus.setComplete()
        return "redirect:/"
    }

    @GetMapping
    fun ordersForUser(@AuthenticationPrincipal users: Users, model: Model): String {
        val pageable = PageRequest.of(0, orderProps.pageSize)
        model.addAttribute("orders", orderRepository.findByUsersOrderByPlacedAtDesc(users, pageable))
        return "orderList"
    }
}