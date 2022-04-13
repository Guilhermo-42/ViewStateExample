package br.com.exampleviewstate.data

interface IDataSource {
    suspend fun fetchData(): Result<String>
    suspend fun fetchOtherData(): Result<Int>
}