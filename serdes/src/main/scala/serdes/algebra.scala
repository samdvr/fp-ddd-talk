package serdes

trait Serializer[F[_], A] {
  def serialize(a: A): F[Array[Byte]]
}

trait Deserializer[F[_], A] {
  def deserialize(bytes: Array[Byte]): F[A]
}

trait Serdes[F[_], A] extends Serializer[F, A] with Deserializer[F, A]

