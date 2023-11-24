package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Users
import java.util.Optional

interface UserRepository: CrudRepository<Users, Long> {
    fun findByUsername(username: String): Optional<Users>
}