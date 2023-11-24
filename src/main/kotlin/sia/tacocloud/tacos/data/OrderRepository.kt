package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Order
import java.util.*

interface OrderRepository: CrudRepository<Order, Long> {

    fun readOrdersByDeliveryZipAndPlacedAtBetween(deliveryZip: String, startDate: Date, endDate: Date): List<Order>
}