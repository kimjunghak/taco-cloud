package sia.tacocloud.tacos.web

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.bind.support.SessionStatus
import sia.tacocloud.tacos.Order
import sia.tacocloud.tacos.data.OrderRepository

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
class OrderController(
    private val orderRepository: OrderRepository
) {

    @GetMapping("/current")
    fun orderForm(): String {
        return "orderForm"
    }

    @PostMapping
    fun processOrder(@Valid order: Order, errors: Errors, sessionStatus: SessionStatus): String {
        if(errors.hasErrors()) return "orderForm"

        orderRepository.save(order)
        sessionStatus.setComplete()
        return "redirect:/"
    }
}