package sia.tacocloud.tacos.web

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.Ingredient
import sia.tacocloud.tacos.Ingredient.Type
import sia.tacocloud.tacos.Order
import sia.tacocloud.tacos.Taco
import sia.tacocloud.tacos.data.IngredientRepository
import sia.tacocloud.tacos.data.TacoRepository

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
class DesignTacoController(
    private val ingredientRepository: IngredientRepository,
    private val tacoRepository: TacoRepository,
){
    @GetMapping
    fun showDesignForm(model: Model): String {
        val ingredients = ingredientRepository.findAll().toList()

        Type.entries.forEach {
            model.addAttribute(it.toString().lowercase(), filterByType(ingredients, it))
        }

        model.addAttribute("taco", Taco())

        return "design"
    }

    @PostMapping
    fun processDesign(@Valid design: Taco, errors: Errors,
                      @ModelAttribute order: Order): String {
        if(errors.hasErrors()) return "design"

        val saved = tacoRepository.save(design)
        order.addDesign(saved)

        return "redirect:/orders/current"
    }

    private fun filterByType(ingredients: List<Ingredient>, type: Type): List<Ingredient> {
        return ingredients.filter { it.type == type }
            .toList()
    }

    @ModelAttribute(name = "order")
    fun order() = Order()

    @ModelAttribute(name = "taco")
    fun taco() = Taco()
}
