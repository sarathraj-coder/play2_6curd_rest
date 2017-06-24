package models


import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._
/**
  * Created by sarathraj on 21/06/17.
  */

case class User(id: Option[Long],
                name: String,
                address: String,
                dob: Option[Int],
                joiningDate: Option[Int],
                designation: Option[String])

case class PageData(user:Seq[User],page:Int, offset:Int, rows:Int)
case class Role(id:Option[Long],name:String,idUser:Long)



class UsersTable(tag:Tag) extends Table[(User)](tag,"USER"){



  def id=column[Long]("ID",O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def address = column[String]("ADDRESS")
  def dob = column[Int]("DATE_OF_BIRTH")
  def joiningDate = column[Int]("JOINING_DATE")
  def designation = column[String]("DESIGNATION")

  def * = (id.?, name, address, dob.?, joiningDate.?, designation.?) <> (User.tupled, User.unapply _)

}

class Roles(tag:Tag) extends  Table[Role](tag,"ROLE"){

    val users = TableQuery[UsersTable]

    def id=column[Long]("ID",O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def idUser=column[Long]("ID_USER",O.PrimaryKey, O.AutoInc)
    def * = (id.?, name, idUser) <> (Role.tupled, Role.unapply _)

    // A reified foreign key relation that can be navigated to create a join
    def user = foreignKey("USER_FK", idUser, users)(_.id)

  }
