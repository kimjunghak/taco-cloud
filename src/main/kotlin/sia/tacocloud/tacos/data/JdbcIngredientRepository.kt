package sia.tacocloud.tacos.data

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import sia.tacocloud.tacos.Ingredient
import java.sql.ResultSet

@Repository
class JdbcIngredientRepository (
    private val jdbcTemplate: JdbcTemplate
): IngredientRepository {

    override fun findAll(): Iterable<Ingredient> {
        return jdbcTemplate.query(
            "select id, name, type from Ingredient",
            this::mapRowToIngredient
        )
    }

    override fun findById(id: String): Ingredient? {
        return jdbcTemplate.queryForObject(
            "select id, name, type from Ingredient where id = ?",
            this::mapRowToIngredient,
            id
        )
    }

    override fun save(ingredient: Ingredient): Ingredient {
        return jdbcTemplate.update(
            "insert into Ingredient (id, name, type) values (?, ?, ?)",
            ingredient.id,
            ingredient.name,
            ingredient.type.toString()
        ).let { ingredient }
    }

    private fun mapRowToIngredient(rs: ResultSet, rowNum: Int): Ingredient {
        return Ingredient(
            rs.getString("id"),
            rs.getString("name"),
            Ingredient.Type.valueOf(rs.getString("type"))
        )
    }

}