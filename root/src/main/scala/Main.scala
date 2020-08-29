import java.time.Instant
import java.util.UUID

import cats.effect.{ExitCode, IO, IOApp}
import com.redis.RedisClient
import io.circe.generic.auto._
import messaging.{KafkaConfig, KafkaMessageProducer}
import org.apache.kafka.clients.producer.KafkaProducer
import persistence.RedisStore
import sales.SalesCreator
import sales.model.{CommissionBased, Sales}
import serdes.DefaultJsonSerializer

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = IO {
      val sales = CommissionBased(UUID.randomUUID,
                                  UUID.randomUUID,
                                  UUID.randomUUID,
                                  UUID.randomUUID,
                                  220,
                                  0.2,
                                  Instant.now.toEpochMilli)

      SalesCreator[IO](
        buildRedisStore,
        buildKafkaClientProducer,
        DefaultJsonSerializer[IO, Sales]).create(sales)

    }.as(ExitCode.Success)

  private def buildKafkaClientProducer = KafkaMessageProducer[IO, String, Array[Byte]](
    "topic",
    IO(new KafkaProducer[String, Array[Byte]](KafkaConfig.apply)))

  private def buildRedisStore = RedisStore[IO, String](IO(new RedisClient("localhost", 6379)))
}


