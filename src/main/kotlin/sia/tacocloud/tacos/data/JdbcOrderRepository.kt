package sia.tacocloud.tacos.data

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import sia.tacocloud.tacos.Order
import sia.tacocloud.tacos.Taco
import java.util.Date

@Repository
class JdbcOrderRepository(
    private val orderInserter: SimpleJdbcInsert,
    private val orderTacoInserter: SimpleJdbcInsert,
    private val objectMapper: ObjectMapper,
): OrderRepository {

    @Autowired
    constructor(jdbc: JdbcTemplate): this(
        SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id"),
        SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order_Tacos"),
        ObjectMapper()
    )

    override fun save(order: Order): Order {
        order.placedAt = Date()
        val orderId = saveOrderDetails(order)
        order.id = orderId

        order.tacos.forEach{
            saveTacoToOrder(it, orderId)
        }
        return order
    }

    private fun saveOrderDetails(order: Order): Long {
        //todo object: 찾아보기
        val values = objectMapper.convertValue(order, object : TypeReference<MutableMap<String, Any>>() {})
        values["placedAt"] = order.placedAt!!

        return orderInserter.executeAndReturnKey(values)
            .toLong()
    }

    private fun saveTacoToOrder(taco: Taco, orderId: Long) {
        orderTacoInserter.execute(
            mapOf(
                "tacoOrder" to orderId,
                "taco" to taco.id
            )
        )
    }

}