package com.example.fakeapichallenge.data.local

import kotlinx.coroutines.flow.Flow

interface LocalRepository<model> {

    fun readEntities() : Flow<List<model>>

    suspend fun insertEntity(entity: model)

}