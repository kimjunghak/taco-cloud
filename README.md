## Taco Cloud Kotlin + Spring 구현

[[Spring In Action 5]]

달라진 점
```kotlin
private fun saveTacoInfo(taco: Taco): Long {
    taco.createdAt = Date()
    val preparedStatementCreatorFactory = PreparedStatementCreatorFactory(
        "insert into Taco (name, createdAt) values (?, ?)",
        Types.VARCHAR, Types.TIMESTAMP
    )
    preparedStatementCreatorFactory.setReturnGeneratedKeys(true) // 이 부분을 추가하여야 keyHolder에서 key를 받을 수 있다.

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