package io.wyki.aggro.storage.benchmark

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class Main {

    companion object {
        fun main(vararg args: String) {
            println("Running main method")
            Quarkus.run(*args)
        }
    }
}
