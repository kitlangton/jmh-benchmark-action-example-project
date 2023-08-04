package benchmarks

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
class MyBenchmark {

  @Benchmark
  def measure(): Unit = {
    val x    = 1 + 1
    val slow = List.fill(7_000)(x).reverse
  }

  @Benchmark
  def measure2(): Unit = {
    val x    = 1 + 2
    val slow = List.fill(53_000)(x).reverse
  }

}
