package serdes

import cats.Monad
import io.circe.Encoder
import io.circe.syntax._

object DefaultJsonSerializer {

  def apply[F[_] : Monad ,A](implicit encoder: Encoder[A]) = new Serializer[F,A] {
    override def serialize(a: A): F[Array[Byte]] = Monad[F].pure{
      a.asJson.noSpaces.getBytes()
    }
  }
}