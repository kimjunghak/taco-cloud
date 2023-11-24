package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Ingredient

interface IngredientRepository: CrudRepository<Ingredient, String>