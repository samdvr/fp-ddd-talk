package serdes

import cats.effect.Sync
import io.circe.Encoder
import io.circe.syntax._

object DefaultJsonSerializer {

  def apply[F[_] : Sync ,A](implicit encoder: Encoder[A]) = new Serializer[F,A] {
    override def serialize(a: A): F[Array[Byte]] = Sync[F].delay{
      a.asJson.noSpaces.getBytes()
    }
  }
}