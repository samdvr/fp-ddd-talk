package sales

import java.util.UUID

object model {

  sealed trait Sales {
    def ID: UUID
  }

  case class CommissionBased(ID: UUID,
                             salesPersonID: UUID,
                             productID: UUID,
                             userID: UUID,
                             totalAmount: BigDecimal,
                             commissionRate: Double,
                             createdAt: Long) extends Sales

  case class NonCommissionBased(ID: UUID,
                                salesPersonID: UUID,
                                productID: UUID,
                                userID: UUID,
                                totalAmount: BigDecimal,
                                createdAt: Long) extends Sales

}
