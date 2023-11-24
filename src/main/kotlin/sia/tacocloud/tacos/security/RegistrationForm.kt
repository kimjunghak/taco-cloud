package sia.tacocloud.tacos.security

import org.springframework.security.crypto.password.PasswordEncoder
import sia.tacocloud.tacos.Users

data class RegistrationForm(
    val username: String,
    val password: String,
    val fullName: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val phone: String
) {
    fun toUser(passwordEncoder: PasswordEncoder) = Users(
        null,
        username,
        passwordEncoder.encode(password),
        fullName,
        street,
        city,
        state,
        zip,
        phone
    )
}
