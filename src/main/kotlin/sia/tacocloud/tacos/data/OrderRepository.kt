package sia.tacocloud.tacos.data

import sia.tacocloud.tacos.Order

interface OrderRepository {

    fun save(order: Order): Order
}