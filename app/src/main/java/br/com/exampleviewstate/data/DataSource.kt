package br.com.exampleviewstate.data

import kotlinx.coroutines.delay
import kotlin.random.Random

class DataSource : IDataSource {

    override suspend fun fetchData(): Result<String> {
        delay(1000)

        val randomInt = (Random.nextInt(1, 10))

        return try {
            if (randomInt < 6) {
                Result.success("MyData")
            } else {
                throw SomeException()
            }
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun fetchOtherData(): Result<Int> {
        return Result.success(1)
    }

    class SomeException : Exception()

}