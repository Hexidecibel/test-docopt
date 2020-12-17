package net.hexi

import org.docopt.Docopt
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.HashMap

@SpringBootApplication
open class App {
  companion object {
    private val DOC =
      """
Siteline

Usage:
    app app2 [<args>...] [options]
""".trim()

    @JvmStatic
    fun main(args: Array<String>) {
      val opts = Docopt(DOC).withHelp(true).parse(*args)
      val subComponents = HashMap<String, (args: Array<String>) -> Unit>()
      subComponents["app2"] = { App2.main(it) }

      if (args.isNotEmpty()) {
        val handler = subComponents[args[0]]
        if (handler != null) {
          handler.invoke(args.drop(1).toList().toTypedArray())
          return
        }
      }

      if (opts["help"] as Boolean) {
        val component = opts["<command>"] as String
        val componentHandler = subComponents[component]
        if (componentHandler != null) {
          componentHandler.invoke(arrayOf("-h"))
        } else {
          System.err.println("Error: Unknown sub-command: $component")
          System.err.println(
            "Known sub-commands: " + subComponents.entries.joinToString(", ") { it.key }
          )
          System.exit(1)
        }
        return
      }
      for ((subComponent, value) in subComponents) {
        val o = opts[subComponent]
        if (o != null && (o as Boolean?)!!) {
          value.invoke(args)
          return
        }
      }

      SpringApplication.run(App::class.java)
    }
  }
}
