package messaging


import java.util.concurrent.ConcurrentHashMap

import cats.Id

object InMemoryMessaging extends Messaging[Id,String, String] {
  val hashmap = new ConcurrentHashMap[String, String]()
  override def publish(key: String, value: String): Id[Unit] = {
    hashmap.put(key,value)
  }
}
