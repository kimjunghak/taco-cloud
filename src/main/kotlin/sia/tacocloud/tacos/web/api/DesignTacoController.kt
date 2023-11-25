package sia.tacocloud.tacos.web.api

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sia.tacocloud.tacos.Taco
import sia.tacocloud.tacos.data.TacoRepository

@RestController
@RequestMapping(path = ["/design"], produces = ["application/json"])
@CrossOrigin(origins = ["*"])
class DesignTacoController(
    private val tacoRepository: TacoRepository,
) {

    @GetMapping("/recent")
    fun recentTacos(): MutableList<Taco> {
        val page = PageRequest.of(0, 12, Sort.by("createdAt").descending())
        return tacoRepository.findAll(page).content
    }

    @GetMapping("/{id}")
    fun tacoById(@PathVariable id: Long): ResponseEntity<Taco>? {
        val tacoOptional = tacoRepository.findById(id)
        return if (tacoOptional.isEmpty) ResponseEntity(HttpStatus.NOT_FOUND)
        else ResponseEntity.ok(tacoOptional.get())
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postTaco(@RequestBody taco: Taco): Taco {
        return tacoRepository.save(taco)
    }
}