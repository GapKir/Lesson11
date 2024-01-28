package com.example.lesson11.model

class SingersService{
    private val singers: MutableList<Singer> = mutableListOf()

    init {
        singers.add(Singers.taylorSwift)
        singers.add(Singers.sza)
        singers.add(Singers.beatles)
        singers.add(Singers.tateMcRae)
        singers.add(Singers.jungKook)
        singers.add(Singers.dojaCat)
        singers.add(Singers.morganWallen)
    }

    fun getSingers(): List<Singer> {
        return singers
    }
}
