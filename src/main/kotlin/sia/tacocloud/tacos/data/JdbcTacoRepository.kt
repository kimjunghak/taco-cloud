package sia.tacocloud.tacos.data

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreatorFactory
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import sia.tacocloud.tacos.Ingredient
import sia.tacocloud.tacos.Taco
import java.sql.Timestamp
import java.sql.Types
import java.util.*

@Repository
class JdbcTacoRepository(
    private val jdbcTemplate: JdbcTemplate
): TacoRepository {

    override fun save(design: Taco): Taco {
        val tacoId = saveTacoInfo(design)
        design.id = tacoId
        design.ingredients!!.forEach {
            saveIngredientToTaco(it, tacoId)
        }
        return design
    }

    private fun saveTacoInfo(taco: Taco): Long {
        taco.createdAt = Date()
        val preparedStatementCreatorFactory = PreparedStatementCreatorFactory(
            "insert into Taco (name, createdAt) values (?, ?)",
            Types.VARCHAR, Types.TIMESTAMP
        )
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true) // 자동 생성된 키를 받기 위해

        val psc = preparedStatementCreatorFactory.newPreparedStatementCreator(
            listOf(taco.name, Timestamp(taco.createdAt!!.time))
        )

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            psc, keyHolder
        )

        return keyHolder.key!!.toLong()
    }

    private fun saveIngredientToTaco(ingredient: Ingredient, tacoId: Long) {
        jdbcTemplate.update(
            "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
            tacoId, ingredient.id
        )
    }
}