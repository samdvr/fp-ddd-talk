package messaging

trait Messaging[F[_],K,V] {
  def publish(key: K, value: V): F[Unit]
}

