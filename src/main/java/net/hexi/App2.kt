package net.hexi

import org.docopt.Docopt

object App2 {
  @JvmStatic
  fun main(args: Array<String>) {
    try {
      val docopt = Docopt(
        """
        App 2 Test
        
        Usage:
         app2 [--test TEST]
         app2 inner [--test TEST]
        
        Options:
          --test TEST            DO YOU WORK [default: NO]
        """.trimIndent()
      ).withHelp(true).parse(*args)
      val test = docopt["--test"]
      val inner = (docopt["inner"] as Boolean?)!!
      if (inner) {
        println("inner")
      }
      println("Test:  $test")
    } catch (e: Exception) {
      e.printStackTrace()
      System.exit(1)
    }
  }
}
