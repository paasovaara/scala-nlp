package models

import opendata.citizeninitiative.DetailedInitiativeInfo

case class Title(
  title: String
)

case class FullData(
           id: String,
           supportCount: Int,
           name: String,
           proposal: String,
           rational: Option[String]
)

object FullData {
  def apply(info:DetailedInitiativeInfo): FullData = {
    FullData(
      info.id,
      info.supportCount,
      info.name.fi.getOrElse(""),
      info.proposal.fi.getOrElse(""),
      info.rational.flatMap(_.fi)
    )
  }
}

case class FullDataListing(
  count: Int,
  data: Seq[FullData]
)