package sia.tacocloud.tacos

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.Date

@Entity
@Table(name="Taco_Order")
class Taco(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @field:NotNull
    @field:Size(min = 5, message = "Name must be at least 5 characters long")
    var name: String? = null,

    @field:ManyToMany(targetEntity = Ingredient::class)
    @field:Size(min = 1, message = "You must choose at least 1 ingredient")
    var ingredients: MutableList<Ingredient>? = mutableListOf(),

    var createdAt: Date? = Date(),
) {
    @PrePersist
    fun createdAt() {
        this.createdAt = Date()
    }
}