package sia.tacocloud.tacos.data

import sia.tacocloud.tacos.Taco

interface TacoRepository {
    fun save(design: Taco): Taco
}