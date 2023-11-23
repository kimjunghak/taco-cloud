package sia.tacocloud.tacos.data

import sia.tacocloud.tacos.Ingredient

interface IngredientRepository {

    fun findAll(): Iterable<Ingredient>
    fun findById(id: String): Ingredient?
    fun save(ingredient: Ingredient): Ingredient
}