package sia.tacocloud.tacos

import jakarta.persistence.*
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.CreditCardNumber
import java.io.Serializable
import java.util.Date

@Entity
@Table(name="`Order`")
class Order (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @field:NotBlank(message = "Name is required")
    var deliveryName: String? = String(),

    @field:NotBlank(message = "Street is required")
    var deliveryStreet: String? = String(),

    @field:NotBlank(message = "City is required")
    var deliveryCity: String? = String(),

    @field:NotBlank(message = "State is required")
    var deliveryState: String? = String(),

    @field:NotBlank(message = "Zip code is required")
    var deliveryZip: String? = String(),

    @field:CreditCardNumber(message = "Not a valid credit card number")
    var ccNumber: String? = String(),

    @field:Pattern(regexp = "^(0[1-9]|1[0-2])(/)([1-9][0-9])$", message = "Must be formatted MM/YY")
    var ccExpiration: String? = String(),

    @field:Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    var ccCVV: String? = String(),

    var placedAt: Date? = null,

    @field:ManyToMany(targetEntity = Taco::class)
    var tacos: MutableList<Taco> = mutableListOf(),

    @field:ManyToOne
    var users: Users? = null
): Serializable {

    private val serialVersionUID: Long = 1L

    fun addDesign(design: Taco) {
        this.tacos.add(design)
    }

    @PrePersist
    fun placedAt() {
        this.placedAt = Date()
    }
}