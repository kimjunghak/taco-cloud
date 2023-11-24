package sia.tacocloud.tacos.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import sia.tacocloud.tacos.data.UserRepository

@Service
class UserRepositoryUSerDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("User '$username' not found")
        }
    }
}