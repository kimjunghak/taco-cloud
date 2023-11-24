package sia.tacocloud.tacos.data

import org.springframework.data.repository.CrudRepository
import sia.tacocloud.tacos.Taco

interface TacoRepository: CrudRepository<Taco, Long>