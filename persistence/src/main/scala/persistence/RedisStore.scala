package persistence

import cats.Monad
import cats.implicits._
import com.redis.RedisClient
object RedisStore {
  def apply[F[_] : Monad ,K](redisConnection: F[RedisClient]) =  new KVStore[F,K,Array[Byte]] {
    override def get(key: K): F[Option[Array[Byte]]] =
      redisConnection.map(_.get(key).map(_.getBytes))

    override def put(key: K, value: Array[Byte]): F[Unit] =
      redisConnection.map(_.set(key,value))
  }
}
