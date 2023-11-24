package sia.tacocloud.tacos.security

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import sia.tacocloud.tacos.data.UserRepository

@Controller
@RequestMapping("/register")
class RegistrationController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping
    fun registerForm() = "registration"

    @PostMapping
    fun processRegistration(registrationForm: RegistrationForm): String {
        userRepository.save(registrationForm.toUser(passwordEncoder))
        return "redirect:/login"
    }
}