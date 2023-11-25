package sia.tacocloud.tacos.data

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Order
import sia.tacocloud.tacos.Users
import java.util.*

interface OrderRepository: CrudRepository<Order, Long> {

    fun readOrdersByDeliveryZipAndPlacedAtBetween(deliveryZip: String, startDate: Date, endDate: Date): List<Order>

    fun findByUsersOrderByPlacedAtDesc(users: Users, pageable: Pageable): List<Order>
}