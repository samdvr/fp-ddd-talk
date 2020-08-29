package sales

import cats.Monad
import cats.implicits._
import messaging.Messaging
import persistence.KVStore
import sales.model.Sales
import serdes.Serializer
trait SalesCreator[F[_]] {
  def create(sales: Sales): F[Unit]
}
object SalesCreator {
  def apply[F[_]: Monad](store: KVStore[F, String, Array[Byte]],
                        messaging: Messaging[F, String, Array[Byte]],
                        serializer: Serializer[F, Sales],
  ): SalesCreator[F] = new SalesCreator[F] {
    override def create(sales: Sales): F[Unit] =
      for {
        arrayOfBytes <- serializer.serialize(sales)
        _ <- store.put(sales.ID.toString, arrayOfBytes)
        _ <- messaging.publish(sales.ID.toString, arrayOfBytes)
      } yield ()
  }
}
