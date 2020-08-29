package messaging


import java.util.Properties

import cats.effect.Sync
import cats.implicits._
import org.apache.kafka.clients.producer.{Producer, ProducerConfig, ProducerRecord}
object KafkaMessageProducer {
  def apply[F[_] : Sync ,K,V](topic: String,
                              producer: F[Producer[K,V]]) = new Messaging[F,K,V] {

    override def publish(key: K, value: V): F[Unit] = for {
      _ <- producer.map(_.send(new ProducerRecord[K, V](topic,value)))
    } yield ()
  }


}

object KafkaConfig {
  def apply = {
    val props = new Properties()
    props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer")
    props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer")
    props
  }
}