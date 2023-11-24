package sia.tacocloud.tacos

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Ingredient(
    @field:Id
    var id: String,
    var name: String,
    var type: Type,
) {
    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}