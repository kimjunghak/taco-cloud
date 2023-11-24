## Taco Cloud Kotlin + Spring 구현

[[Spring In Action 5]]

### 오류 리스트
```kotlin
private fun saveTacoInfo(taco: Taco): Long {
    taco.createdAt = Date()
    val preparedStatementCreatorFactory = PreparedStatementCreatorFactory(
        "insert into Taco (name, createdAt) values (?, ?)",
        Types.VARCHAR, Types.TIMESTAMP
    )
    preparedStatementCreatorFactory.setReturnGeneratedKeys(true) // 추가된 설정

    val psc = preparedStatementCreatorFactory.newPreparedStatementCreator(
        listOf(taco.name, Timestamp(taco.createdAt!!.time))
    )

    val keyHolder = GeneratedKeyHolder()

    jdbcTemplate.update(
        psc, keyHolder
    )

    return keyHolder.key!!.toLong()
}
```
preparedStatementCreatorFactory.setReturnGeneratedKeys(true) // 이 부분을 추가하여야 keyHolder에서 key를 받을 수 있다.   
해당 설정이 없으면 key 가 null 인 오류가 발생한다.
```kotlin
@Bean
fun securityFilterChain(http: HttpSecurity, inspector: HandlerMappingIntrospector): SecurityFilterChain {
    val matcherBuilder = MvcRequestMatcher.Builder(inspector) // Mvc Request Matcher
    // Spring Security 6.x 버전에서 h2-console을 사용할 때 필요한 설정
    return http
        .authorizeHttpRequests {
            it.requestMatchers(
                matcherBuilder.pattern("/design"),
                matcherBuilder.pattern("/orders")
            ).hasRole("USER")
                .requestMatchers(
                    matcherBuilder.pattern("/"),
                    matcherBuilder.pattern("/**")
                ).permitAll()
        }
        .httpBasic {  }
        .build()
}
```
MvcRequestMatcher을 이용하여 Servlet을 정확하게 지정해준다.   
지정하지 않을 경우 /h2-console/* 경로의 Servlet 매핑 오류가 난다.   
```text
This is because there is more than one mappable servlet in your servlet context: {org.h2.server.web.JakartaWebServlet=[/h2-console/*], org.springframework.web.servlet.DispatcherServlet=[/]}.
```
